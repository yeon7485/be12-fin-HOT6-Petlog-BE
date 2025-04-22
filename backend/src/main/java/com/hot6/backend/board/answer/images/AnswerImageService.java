package com.hot6.backend.board.answer.images;

import com.hot6.backend.board.answer.model.Answer;
import com.hot6.backend.pet.S3Service;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void deleteImagesByAnswer(Long answerIdx) {
        List<AnswerImage> images = answerImageRepository.findByAnswerIdx(answerIdx);
        for (AnswerImage image : images) {
            String url = image.getUrl();
            String key = extractKeyFromUrl(url);
            s3Service.delete(key);
        }
        answerImageRepository.deleteByAnswerIdx(answerIdx);
    }

    private String extractKeyFromUrl(String url) {
        int index = url.indexOf(".amazonaws.com/");
        if (index == -1) {
            throw new IllegalArgumentException("S3 URL 형식이 올바르지 않습니다: " + url);
        }
        return url.substring(index + ".amazonaws.com/".length());
    }

    public void deleteImagesExcept(Answer answer, List<String> keepUrls) {
        List<AnswerImage> all = answerImageRepository.findByAnswer(answer);
        for (AnswerImage image : all) {
            if (!keepUrls.contains(image.getUrl().trim())) {
                s3Service.delete(image.getUrl());
                answerImageRepository.delete(image);
            }
        }
    }
}
