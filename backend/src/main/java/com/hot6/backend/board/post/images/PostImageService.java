package com.hot6.backend.board.post.images;

import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.pet.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final S3Service s3Service;
    private final PostImageRepository postImageRepository;

    public void saveImages(List<MultipartFile> files, Post post) throws IOException {
        for (MultipartFile file : files) {
            String key = "posts/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String imageUrl = s3Service.upload(file, key);

            PostImage image = PostImage.builder()
                    .post(post)
                    .url(imageUrl)
                    .build();

            postImageRepository.save(image);
        }
    }

    @Transactional
    public void deleteImagesByPost(Long postIdx) {
        List<PostImage> images = postImageRepository.findByPostIdx(postIdx);
        for (PostImage image : images) {
            String url = image.getUrl();
            String key = extractKeyFromUrl(url); // ex) posts/123_파일명.jpg
            s3Service.delete(key); // 실제 S3에서 삭제
        }
        postImageRepository.deleteByPostIdx(postIdx);
    }

    private String extractKeyFromUrl(String url) {
        int index = url.indexOf(".amazonaws.com/");
        if (index == -1) {
            throw new IllegalArgumentException("S3 URL 형식이 올바르지 않습니다: " + url);
        }
        return url.substring(index + ".amazonaws.com/".length());
    }
}
