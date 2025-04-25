package com.hot6.backend.pet;

import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.pet.model.PetDto;
import com.hot6.backend.pet.model.PetStatus;
import com.hot6.backend.schedule.model.ScheduleDto;
import com.hot6.backend.user.UserRepository;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.hot6.backend.pet.model.QPet.pet;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Value("${pet-image}")
    private String petImageUrl;

    @Transactional
    public void createPetCard(PetDto.PetCardCreateRequest request, String imagePath) {
        // ✅ imagePath가 없으면 환경변수로 대체
        String profileImageUrl = (imagePath != null && !imagePath.isBlank())
                ? imagePath
                : petImageUrl;

        // 사용자 조회
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));

        // Entity 생성 + 이미지 URL 적용
        Pet pet = request.toEntity(user, profileImageUrl); // <- 바로 넘겨도 되고
        pet.setProfileImageUrl(profileImageUrl);           // <- 여기서도 확실히 설정
        petRepository.save(pet);
    }


    //유저별 카드 목록
    public List<PetDto.PetCard> getPetCardsByUserId(Long userId) {
        List<Pet> pets = petRepository.findByUserIdx(userId);

        return pets.stream()
                .map(PetDto.PetCard::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    // 카드 수정
    public void updatePetCard(PetDto.PetCardUpdateRequest petCardUpdateRequest, MultipartFile profileImage, Long petId) {
        // 1. 기존 반려동물 조회
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.PET_NOT_FOUND_WITH_ID, petId));

        // 2. DTO를 통해 엔티티 업데이트
        petCardUpdateRequest.updateEntity(pet);

        // 3. 프로필 이미지가 새로 들어왔으면, S3 업로드 후 URL 저장
        if (profileImage != null && !profileImage.isEmpty()) {
            String key = "pet/" + UUID.randomUUID();  // 고유 경로 생성
            try {
                String imageUrl = s3Service.upload(profileImage, key);
                pet.setProfileImageUrl(imageUrl);  // 이미지 URL 반영
            } catch (IOException e) {
                throw new BaseException(BaseResponseStatus.IMAGE_UPLOAD_FAILED);
            }
        }
        // 4. 저장
        petRepository.save(pet);
    }
    // 카드 상세 조회
    public PetDto.PetCardDetailResponse getPetDetailById(Long petId) {
        // Pet ID로 해당 반려동물을 DB에서 조회
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.PET_NOT_FOUND_WITH_ID, petId));

        // 조회된 Pet 객체를 PetCardDetailResponse로 변환하여 반환
        return PetDto.PetCardDetailResponse.from(pet);
    }

    @Transactional
    // 반려동물 삭제
    public void deletePet(Long petId) {
        // 반려동물 ID로 조회
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.PET_NOT_FOUND_WITH_ID, petId));

        // 삭제 처리
        petRepository.delete(pet);
    }


    public List<Pet> findByUser(User user) {
        return petRepository.findByUserIdx(user.getIdx());
    }

    public Pet findById(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseStatus.PET_NOT_FOUND));
    }
}

