package com.tech_challenge.medical.domain;

import org.springframework.web.multipart.MultipartFile;

public record VideoUpload(
        MultipartFile file,
        Long patientId,
        String source
) {
}
