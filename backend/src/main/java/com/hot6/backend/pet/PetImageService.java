package com.hot6.backend.pet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PetImageService implements ImageService {

    @Value("${project.upload.path}")
    private String defaultUploadPath;

    private String makeDir() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String uploadPath = defaultUploadPath + "/" + date;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        return "/" + date;
    }

    @Override
    public List<String> upload(MultipartFile[] files) {
        List<String> uploadFilePaths = new ArrayList<>();
        String uploadPath = makeDir();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String uploadFilePath = uploadPath + "/" + UUID.randomUUID() + "_" + originalFilename;
            uploadFilePaths.add(uploadFilePath);

            File uploadFile = new File(defaultUploadPath + "/" + uploadFilePath);
            try {
                file.transferTo(uploadFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return uploadFilePaths;
    }
}