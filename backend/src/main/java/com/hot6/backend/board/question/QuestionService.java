package com.hot6.backend.board.question;

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
}
