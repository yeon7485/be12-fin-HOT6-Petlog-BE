package com.hot6.backend.board.question;

import com.hot6.backend.board.answer.AnswerService;
import com.hot6.backend.board.hashtagQuestion.Hashtag_QuestionService;
import com.hot6.backend.board.post.model.PostDto;
import com.hot6.backend.board.question.images.QuestionImageService;
import com.hot6.backend.board.question.model.Question;
import com.hot6.backend.board.question.model.QuestionDto;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final AnswerService answerService;
    private final QuestionRepository questionRepository;
    private final Hashtag_QuestionService hashtagService;
    private final QuestionImageService questionImageService;

    public void create(QuestionDto.QuestionRequest dto, List<MultipartFile> images) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        Question question = dto.toEntity();
        question.setUser(currentUser);

        questionRepository.save(question);

        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            hashtagService.saveTags(dto.getTags(), question.getIdx());
        }

        if (images != null && !images.isEmpty()) {
            questionImageService.saveImages(images, question);
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

    public void update(Long idx, QuestionDto.QuestionRequest dto, List<MultipartFile> images) throws IOException {
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

        if (images != null && !images.isEmpty()) {
            questionImageService.deleteImagesByQuestion(idx);
            questionImageService.saveImages(images, question);
        }
    }

    public void delete(Long idx) {
        hashtagService.deleteByQuestionIdx(idx);
        answerService.deleteByQuestionIdx(idx);
        questionImageService.deleteImagesByQuestion(idx);
        questionRepository.deleteById(idx);
    }
  
    public List<QuestionDto.UserQuestionResponse> findUserQuestions(Long userId) {
        return questionRepository.findByUser_IdxOrderByCreatedAtDesc(userId)
                .stream()
                .map(QuestionDto.UserQuestionResponse::from)
                .toList();
    }
}
