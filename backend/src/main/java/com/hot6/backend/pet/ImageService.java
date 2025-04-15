package com.hot6.backend.pet;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<String> upload(MultipartFile[] files);
}