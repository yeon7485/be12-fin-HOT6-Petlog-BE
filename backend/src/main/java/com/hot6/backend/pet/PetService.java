package com.hot6.backend.pet;

import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.pet.model.PetDto;
import com.hot6.backend.pet.model.PetStatus;
import com.hot6.backend.schedule.model.ScheduleDto;
import com.hot6.backend.user.UserRepository;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.hot6.backend.pet.model.QPet.pet;

@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    public void createPetCard(PetDto.PetCardCreateRequest request, String imagePath) {
        // 이미지 URL이 있을 경우, 해당 URL을 사용
        String profileImageUrl = null;
        if (imagePath != null) {
            profileImageUrl = imagePath; // S3에서 반환된 URL
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다: " + request.getUserId()));


        Pet pet = request.toEntity(user,imagePath);
        pet.setProfileImageUrl(profileImageUrl);  // 프로필 이미지 URL 설정
        petRepository.save(pet);  // DB에 저장
    }

    public String saveProfileImage(MultipartFile image) {
        String imagePath = imageService.upload(new MultipartFile[]{image}).get(0);
        // 서버에서 이미지 경로 반환 시 절대 경로로 설정
        return imagePath;
    }

    public List<PetDto.PetCard> getPetCardsByUserId(Long userId) {
        List<Pet> pets = petRepository.findByUserIdx(userId);

        return pets.stream()
                .map(PetDto.PetCard::fromEntity)
                .collect(Collectors.toList());
    }

    public void updatePetCard(PetDto.PetCardUpdateRequest petCardUpdateRequest, MultipartFile profileImage, Long petId) {
        // 1. 기존 반려동물 조회
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물이 존재하지 않습니다. id=" + petId));

        // 2. 필드 업데이트
        pet.setName(petCardUpdateRequest.getName());
        pet.setBreed(petCardUpdateRequest.getBreed());
        pet.setGender(petCardUpdateRequest.getGender());
        pet.setBirthDate(petCardUpdateRequest.getBirthDate());
        pet.setNeutering(petCardUpdateRequest.isNeutering());
        pet.setSpecificInformation(petCardUpdateRequest.getSpecificInformation());
        pet.setStatus(PetStatus.valueOf(petCardUpdateRequest.getStatus()));

        // ✅ 3. 프로필 이미지가 새로 들어왔으면, S3 업로드 후 URL 저장
        if (profileImage != null && !profileImage.isEmpty()) {
            String key = "pet/profile/" + UUID.randomUUID();  // 예: 고유한 파일 경로 생성
            try {
                String imageUrl = s3Service.upload(profileImage, key);
                pet.setProfileImageUrl(imageUrl);  // 👉 이미지 경로 저장
            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드 실패", e);
            }
        }

        // 4. 저장
        petRepository.save(pet);
    }

    public PetDto.PetCardDetailResponse getPetDetailById(Long petId) {
        // Pet ID로 해당 반려동물을 DB에서 조회
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물이 존재하지 않습니다. id=" + petId));

        // 조회된 Pet 객체를 PetCardDetailResponse로 변환하여 반환
        return PetDto.PetCardDetailResponse.from(pet);
    }

    // 반려동물 삭제
    public void deletePet(Long petId) {
        // 반려동물 ID로 조회
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물이 존재하지 않습니다. id=" + petId));

        // 삭제 처리
        petRepository.delete(pet);
    }


    public List<Pet> findByUser(User user) {
        return petRepository.findByUserIdx(user.getIdx());
    }
}

