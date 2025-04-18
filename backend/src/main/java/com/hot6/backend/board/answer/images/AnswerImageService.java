package com.hot6.backend.board.answer.images;

import com.hot6.backend.board.answer.model.Answer;
import com.hot6.backend.pet.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerImageService {

    private final S3Service s3Service;
    private final AnswerImageRepository answerImageRepository;

    public void saveImages(List<MultipartFile> images, Answer answer) throws IOException {
        for (MultipartFile image : images) {
            System.out.println("파일 이름: " + image.getOriginalFilename());
            String key = "answers/" + System.currentTimeMillis() + "_" + image.getOriginalFilename();
            String url = s3Service.upload(image, key);

            AnswerImage answerImage = AnswerImage.builder()
                    .answer(answer)
                    .url(url)
                    .build();

            answerImageRepository.save(answerImage);
        }
    }
}
