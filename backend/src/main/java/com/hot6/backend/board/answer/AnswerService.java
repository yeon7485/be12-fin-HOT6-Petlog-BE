package com.hot6.backend.board.answer;

import com.hot6.backend.board.answer.model.Answer;
import com.hot6.backend.board.answer.model.AnswerDto;
import com.hot6.backend.board.question.QuestionRepository;
import com.hot6.backend.board.question.model.Question;
import com.hot6.backend.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public void create(AnswerDto.AnswerRequest dto, User currentUser) {
        Question question = questionRepository.findById(dto.getQuestionIdx())
                .orElseThrow(() -> new RuntimeException("질문이 존재하지 않습니다"));

        Answer answer = Answer.builder()
                .content(dto.getContent())
                .selected(false)
                .question(question)
                .user(currentUser)
                .build();

        answerRepository.save(answer);
    }

    public List<AnswerDto.AnswerResponse> listByQuestion(Long questionIdx) {
        return answerRepository.findByQuestion_Idx(questionIdx).stream()
                .map(AnswerDto.AnswerResponse::from)
                .toList();
    }

    public void select(Long idx) {
        Answer answer = answerRepository.findById(idx)
                .orElseThrow(() -> new RuntimeException("답변 없음"));
        answer.setSelected(true);
        answerRepository.save(answer);

        Question question = answer.getQuestion();
        question.setSelected(true);
        questionRepository.save(question);
    }

    @Transactional
    public void deleteByQuestionIdx(Long questionIdx) {
        answerRepository.deleteByQuestionIdx(questionIdx);
    }

    @Transactional
    public void update(Long idx, AnswerDto.AnswerRequest dto) {
        Answer answer = answerRepository.findById(idx)
                .orElseThrow(() -> new RuntimeException("답변이 존재하지 않습니다"));
        answer.setContent(dto.getContent());
        answerRepository.save(answer);
    }

    public AnswerDto.AnswerResponse read(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 답변을 찾을 수 없습니다."));
        return AnswerDto.AnswerResponse.from(answer);
    }

    @Transactional
    public void delete(Long idx) {
        Answer answer = answerRepository.findById(idx)
                .orElseThrow(() -> new RuntimeException("답변이 존재하지 않습니다."));
        answerRepository.delete(answer);
    }
}
