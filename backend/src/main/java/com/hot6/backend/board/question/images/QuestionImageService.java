package com.hot6.backend.board.question.images;


import com.hot6.backend.board.question.model.Question;
import com.hot6.backend.pet.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionImageService {

    private final S3Service s3Service;
    private final QuestionImageRepository questionImageRepository;

    public void saveImages(List<MultipartFile> files, Question question) throws IOException {
        for (MultipartFile file : files) {
            String key = "questions/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String imageUrl = s3Service.upload(file, key);

            QuestionImage image = QuestionImage.builder()
                    .question(question)
                    .url(imageUrl)
                    .build();

            questionImageRepository.save(image);
        }
    }

    public void deleteImagesByQuestion(Long questionIdx) {
        questionImageRepository.deleteByQuestionIdx(questionIdx);
    }
}

