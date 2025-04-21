package com.hot6.backend.board.question;

import com.hot6.backend.board.answer.AnswerService;
import com.hot6.backend.board.answer.aiAnswer.AiAnswerService;
import com.hot6.backend.board.hashtagQuestion.Hashtag_QuestionService;
import com.hot6.backend.board.question.images.QuestionImageService;
import com.hot6.backend.board.question.model.Question;
import com.hot6.backend.board.question.model.QuestionDto;
import com.hot6.backend.pet.PetRepository;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final AnswerService answerService;
    private final QuestionRepository questionRepository;
    private final Hashtag_QuestionService hashtagService;
    private final QuestionImageService questionImageService;
    private final AiAnswerService aiAnswerService;
    private final PetRepository petRepository;

    public void create(QuestionDto.QuestionRequest dto, List<MultipartFile> images) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        Question question = dto.toEntity();
        question.setUser(currentUser);
        questionRepository.save(question);

        // ✅ 선택된 반려동물과 연관
        if (dto.getPetIdxList() != null && !dto.getPetIdxList().isEmpty()) {
            List<Pet> pets = petRepository.findAllById(dto.getPetIdxList());
            pets.forEach(pet -> pet.setQuestion(question));
            petRepository.saveAll(pets);
        }

        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            hashtagService.saveTags(dto.getTags(), question.getIdx());
        }

        if (images != null && !images.isEmpty()) {
            questionImageService.saveImages(images, question);
        }

        try {
            System.out.println("🔥 AI 답변 생성 시도 시작");
            String aiContent = aiAnswerService.generateAnswer(question.getQTitle(), question.getContent());
            System.out.println("🔥 생성된 AI 답변 내용: " + aiContent);
            answerService.createAiAnswerForQuestion(question, aiContent);
            System.out.println("✅ AI 답변 저장 완료");
        } catch (Exception e) {
            System.out.println("❌ AI 답변 생성 실패: " + e.getMessage());
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

        // ✅ 기존 질문과 연결된 펫 해제 후 다시 연결
        List<Pet> existingPets = petRepository.findAllByQuestion(question);
        existingPets.forEach(p -> p.setQuestion(null));
        petRepository.saveAll(existingPets);

        if (dto.getPetIdxList() != null && !dto.getPetIdxList().isEmpty()) {
            List<Pet> selectedPets = petRepository.findAllById(dto.getPetIdxList());
            selectedPets.forEach(pet -> pet.setQuestion(question));
            petRepository.saveAll(selectedPets);
        }
    }

    @Transactional
    public void delete(Long idx) {
        Question question = questionRepository.findById(idx)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "질문 없음"));

        questionImageService.deleteImagesByQuestion(idx);
        answerService.deleteByQuestionIdx(idx);
        hashtagService.deleteByQuestionIdx(idx);

        // ✅ 연결된 펫들 question 해제
        List<Pet> relatedPets = petRepository.findAllByQuestion(question);
        relatedPets.forEach(pet -> pet.setQuestion(null));
        petRepository.saveAll(relatedPets);

        questionRepository.delete(question);
    }

    public List<QuestionDto.UserQuestionResponse> findUserQuestions(Long userId) {
        return questionRepository.findByUser_IdxOrderByCreatedAtDesc(userId)
                .stream()
                .map(QuestionDto.UserQuestionResponse::from)
                .toList();
    }
}
