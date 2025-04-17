package com.hot6.backend.pet;

import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.pet.model.PetDto;
import com.hot6.backend.pet.model.PetStatus;
import com.hot6.backend.schedule.model.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.hot6.backend.pet.model.QPet.pet;

@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final ImageService imageService;

    public void createPetCard(PetDto.PetCardCreateRequest request, String imagePath) {
        // 이미지 URL이 있을 경우, 해당 URL을 사용
        String profileImageUrl = null;
        if (imagePath != null) {
            profileImageUrl = imagePath; // S3에서 반환된 URL
        }

        Pet pet = request.toEntity(imagePath);
        pet.setProfileImageUrl(profileImageUrl);  // 프로필 이미지 URL 설정
        petRepository.save(pet);  // DB에 저장
    }

    public String saveProfileImage(MultipartFile image) {
        String imagePath = imageService.upload(new MultipartFile[]{image}).get(0);
        // 서버에서 이미지 경로 반환 시 절대 경로로 설정
        return imagePath;
    }

    public List<PetDto.PetCard> getPetCardsByUserId(Long userId) {
        List<Pet> pets = petRepository.findByUserId(userId);

        return pets.stream()
                .map(PetDto.PetCard::fromEntity)
                .collect(Collectors.toList());
    }

    public void updatePetCard(PetDto.PetCardUpdateRequest petCardUpdateRequest, Long petId) {
        // Pet ID로 해당 반려동물을 DB에서 조회
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물이 존재하지 않습니다. id=" + petId));

        // DTO를 엔티티로 변환하여 해당 반려동물 정보를 갱신
        pet.setName(petCardUpdateRequest.getName());
        pet.setBreed(petCardUpdateRequest.getBreed());
        pet.setGender(petCardUpdateRequest.getGender());
        pet.setBirthDate(petCardUpdateRequest.getBirthDate());
        pet.setNeutering(petCardUpdateRequest.isNeutering());
        pet.setSpecificInformation(petCardUpdateRequest.getSpecificInformation());
        pet.setStatus(PetStatus.valueOf(petCardUpdateRequest.getStatus())); // String -> Enum 변환

        // DB에 저장 (업데이트)
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
}

