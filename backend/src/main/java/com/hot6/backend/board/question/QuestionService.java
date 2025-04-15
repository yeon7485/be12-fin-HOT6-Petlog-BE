package com.hot6.backend.board.question;

import com.hot6.backend.board.answer.AnswerService;
import com.hot6.backend.board.hashtagQuestion.Hashtag_QuestionService;
import com.hot6.backend.board.question.model.Question;
import com.hot6.backend.board.question.model.QuestionDto;
import lombok.RequiredArgsConstructor;
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
        Question question = questionRepository.save(dto.toEntity());
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            hashtagService.saveTags(dto.getTags(), question.getIdx());
        }
    }

    public List<QuestionDto.QuestionResponse> list() {
        return questionRepository.findAll().stream()
                .map(QuestionDto.QuestionResponse::from)
                .toList();
    }

    public List<QuestionDto.QuestionResponse> search(String keyword) {
        List<Question> result = questionRepository
                .findByqTitleContainingIgnoreCaseOrWriterContainingIgnoreCaseOrContentContainingIgnoreCaseOrHashtagsListTagContainingIgnoreCase(
                        keyword, keyword, keyword, keyword);
        return result.stream().map(QuestionDto.QuestionResponse::from).toList();
    }

    public QuestionDto.QuestionResponse read(Long idx) {
        Optional<Question> result = questionRepository.findById(idx);
        return result.map(QuestionDto.QuestionResponse::from).orElse(null);
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
        hashtagService.deleteByQuestionIdx(idx);     // 해시태그 삭제
        answerService.deleteByQuestionIdx(idx);      // 답변 삭제
        questionRepository.deleteById(idx);          // 질문 삭제
    }


}
