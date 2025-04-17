package com.hot6.backend.board.question;

import com.hot6.backend.board.answer.AnswerService;
import com.hot6.backend.board.hashtagQuestion.Hashtag_QuestionService;
import com.hot6.backend.board.question.model.Question;
import com.hot6.backend.board.question.model.QuestionDto;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final AnswerService answerService;
    private final QuestionRepository questionRepository;
    private final Hashtag_QuestionService hashtagService;

    public void create(QuestionDto.QuestionRequest dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        Question question = dto.toEntity();
        question.setUser(currentUser); // 로그인한 유저 설정

        questionRepository.save(question);

        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            hashtagService.saveTags(dto.getTags(), question.getIdx());
        }
    }

    public List<QuestionDto.QuestionResponse> list() {
        return questionRepository.findAll().stream()
                .map(q -> {
                    int answerCount = answerService.countByQuestionIdx(q.getIdx());
                    return QuestionDto.QuestionResponse.from(q, answerCount);
                })
                .toList();
    }

    public List<QuestionDto.QuestionResponse> search(String keyword) {
        List<Question> result = questionRepository
                .findByqTitleContainingIgnoreCaseOrUserNicknameContainingIgnoreCaseOrContentContainingIgnoreCaseOrHashtagsListTagContainingIgnoreCase(
                        keyword, keyword, keyword, keyword);
        return result.stream()
                .map(q -> {
                    int answerCount = answerService.countByQuestionIdx(q.getIdx());
                    return QuestionDto.QuestionResponse.from(q, answerCount);
                })
                .toList();
    }

    public QuestionDto.QuestionResponse read(Long idx) {
        Optional<Question> result = questionRepository.findById(idx);
        return result.map(q -> {
            int answerCount = answerService.countByQuestionIdx(q.getIdx());
            return QuestionDto.QuestionResponse.from(q, answerCount);
        }).orElse(null);
    }

    public void update(Long idx, QuestionDto.QuestionRequest dto) {
        Question question = questionRepository.findById(idx)
                .orElseThrow(() -> new RuntimeException("질문이 존재하지 않습니다"));

        question.setQTitle(dto.getQTitle());
        question.setContent(dto.getContent());
        question.setImage(dto.getImage());
        question.setSelected(dto.isSelected());

        questionRepository.save(question);

        hashtagService.deleteByQuestionIdx(idx);
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            hashtagService.saveTags(dto.getTags(), idx);
        }
    }

    public void delete(Long idx) {
        hashtagService.deleteByQuestionIdx(idx);
        answerService.deleteByQuestionIdx(idx);
        questionRepository.deleteById(idx);
    }
}
