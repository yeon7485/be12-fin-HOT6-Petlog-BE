package com.hot6.backend.board.post.images;

import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.pet.S3Service;
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

    public void deleteImagesByPost(Long postIdx) {
        postImageRepository.deleteByPostIdx(postIdx);
    }
}
