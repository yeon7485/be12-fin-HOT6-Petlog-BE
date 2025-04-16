package com.hot6.backend.pet;

import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.pet.model.PetDto;
import com.hot6.backend.schedule.model.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final ImageService imageService;

    public void createPetCard(PetDto.PetCardCreateRequest request, String imagePath) {
        Pet pet = new Pet();
        pet.setName(request.getName());
        pet.setGender(request.getGender());
        pet.setBreed(request.getBreed());
        pet.setBirthDate(request.getBirthDate());
        pet.setNeutering(request.getIsNeutering());
        pet.setSpecificInformation(request.getSpecificInformation());
        pet.setProfileImageUrl(imagePath); // 이미지 경로 설정
        pet.setUserId(request.getUserId());

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
                .map(pet -> PetDto.PetCard.builder()
                        .idx(pet.getIdx())
                        .name(pet.getName())
                        .breed(pet.getBreed())
                        .gender(pet.getGender())
                        .birthDate(pet.getBirthDate())
                        .profileImageUrl(pet.getProfileImageUrl())
                        .isNeutering(pet.isNeutering())
                        .specificInformation(pet.getSpecificInformation())
                        .build())
                .collect(Collectors.toList());
    }

    public void updatePetCard(PetDto.PetCardUpdateRequest request) {
        // DTO를 엔티티로 변환
        Pet pet = request.toEntity(request);

        // userId가 null인 경우 기본값 설정
        if (pet.getUserId() == null) {
            pet.setUserId(1L);  // 예시로 기본값 1L을 설정
        }

        // 저장 또는 업데이트
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

