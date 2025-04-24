package com.hot6.backend.pet;

import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {
    @Value("${S3_BUCKET_NAME}")
    private String bucketName;

    private final S3Operations s3Operations;

    public S3Service(S3Operations s3Operations) {
        this.s3Operations = s3Operations;
    }

    // 파일을 S3에 업로드하고, 파일 URL 반환
    public String upload(MultipartFile file, String key) throws IOException {
        // S3에 파일 업로드
        try (InputStream inputStream = file.getInputStream()) {
            s3Operations.upload(bucketName, key, inputStream);
        }

        // 업로드한 파일의 URL을 수동으로 생성
        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }

    // S3에서 파일 다운로드
    public ResponseEntity<?> download(String key) {
        try {
            // S3에서 파일 가져오기
            S3Resource s3Resource = s3Operations.download(bucketName, key);
            String contentType = s3Resource.contentType();

            if (MediaType.IMAGE_PNG.toString().equals(contentType)) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(s3Resource);
            }

            if (MediaType.IMAGE_JPEG.toString().equals(contentType)) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(s3Resource);
            }

            // ✅ 잘못된 형식 예외
            throw new BaseException(BaseResponseStatus.INVALID_FILE_TYPE);

        } catch (Exception e) {
            // ✅ 다운로드 실패 예외
            throw new BaseException(BaseResponseStatus.FILE_DOWNLOAD_FAILED, e.getMessage());
        }
    }

    public void delete(String key) {
        s3Operations.deleteObject(bucketName, key);
    }
}
