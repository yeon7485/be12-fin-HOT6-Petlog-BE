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


    // 파일 업로드 처리 및 Pet 카드 생성
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
        // 기존 Pet 객체를 petId로 찾아옴
        Pet pet = petRepository.findById(request.getIdx()) // 여기서 petId를 사용해야 함
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물이 존재하지 않습니다. id=" + request.getIdx()));

        // 기존 Pet 엔티티의 필드 값을 요청으로 받은 값으로 업데이트
        pet.setName(request.getName());
        pet.setGender(request.getGender());
        pet.setBreed(request.getBreed());
        pet.setBirthDate(request.getBirthDate());

        // 변경된 Pet 객체를 DB에 저장
        petRepository.save(pet);
    }

    public PetDto.PetCardDetailResponse getPetDetailById(Long petId) {
        // Pet ID로 해당 반려동물을 DB에서 조회
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물이 존재하지 않습니다. id=" + petId));

        // 조회된 Pet 객체를 PetCardDetailResponse로 변환하여 반환
        return PetDto.PetCardDetailResponse.from(pet);
    }
}
