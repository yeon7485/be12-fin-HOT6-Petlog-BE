package com.hot6.backend.board.answer;

import com.hot6.backend.board.answer.images.AnswerImageService;
import com.hot6.backend.board.answer.model.Answer;
import com.hot6.backend.board.answer.model.AnswerDto;
import com.hot6.backend.board.question.QuestionRepository;
import com.hot6.backend.board.question.model.Question;
import com.hot6.backend.user.UserRepository;
import com.hot6.backend.user.model.User;
import com.hot6.backend.user.model.UserType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerImageService answerImageService;

    public void create(AnswerDto.AnswerRequest request, List<MultipartFile> images) throws IOException {
        Question question = questionRepository.findById(request.getQuestionIdx())
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));

        User user = userRepository.findById(1L) // TODO: 인증 사용자로 대체 예정
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음"));

        Answer answer = Answer.builder()
                .question(question)
                .user(user)
                .content(request.getContent())
                .selected(false)
                .build();

        Answer saved = answerRepository.save(answer);

        // ✅ 이미지 저장
        if (images != null && !images.isEmpty()) {
            answerImageService.saveImages(images, saved);
        }
    }

    public List<AnswerDto.AnswerResponse> listByQuestion(Long questionIdx) {
        return answerRepository.findByQuestion_Idx(questionIdx).stream()
                .map(AnswerDto.AnswerResponse::from)
                .toList();
    }

    public void select(Long idx) {
        Answer answer = answerRepository.findById(idx)
                .orElseThrow(() -> new RuntimeException("답변 없음"));

        if (answer.getUser().getUserType() == UserType.AI) {
            throw new IllegalStateException("AI가 작성한 답변은 채택할 수 없습니다.");
        }

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
    public void update(Long idx, AnswerDto.AnswerRequest dto, List<MultipartFile> images) {
        Answer answer = answerRepository.findById(idx)
                .orElseThrow(() -> new RuntimeException("답변이 존재하지 않습니다"));

        if (answer.isSelected()) {
            throw new IllegalStateException("채택된 답변은 수정할 수 없습니다.");
        }

        if (answer.getUser().getUserType() == UserType.AI) {
            throw new IllegalStateException("AI가 작성한 답변은 수정할 수 없습니다.");
        }

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

        if (answer.isSelected()) {
            throw new IllegalStateException("채택된 답변은 삭제할 수 없습니다.");
        }

        if (answer.getUser().getUserType() == UserType.AI) {
            throw new IllegalStateException("AI가 작성한 답변은 삭제할 수 없습니다.");
        }

        answerImageService.deleteImagesByAnswer(idx);
        answerRepository.delete(answer);
    }

    public int countByQuestionIdx(Long questionId) {
        return answerRepository.countByQuestionIdx(questionId);
    }

    public List<AnswerDto.AnswerResponse> readByAnswer(Long userId) {
        return answerRepository.findByUser_IdxOrderByCreatedAtDesc(userId)
                .stream()
                .map(AnswerDto.AnswerResponse::from)
                .toList();
    }

    @Transactional
    public void createAiAnswerForQuestion(Question question, String aiContent) {
        if (aiContent == null || aiContent.trim().isEmpty()) {
            System.out.println(">> AI 답변이 비어있어 저장하지 않음");
            return;
        }

        User aiUser = userRepository.findByUserType(UserType.AI)
                .orElseThrow(() -> new RuntimeException("AI 유저 없음"));

        Answer aiAnswer = Answer.builder()
                .question(question)
                .user(aiUser)
                .content(aiContent)
                .isAi(true)
                .build();

        answerRepository.save(aiAnswer);
        System.out.println(">> AI 답변 저장 완료");
    }


}
