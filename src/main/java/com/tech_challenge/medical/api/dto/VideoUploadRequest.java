package com.tech_challenge.medical.api.dto;

import com.tech_challenge.medical.api.validation.FileType;
import org.springframework.web.multipart.MultipartFile;

public record VideoUploadRequest(
        @FileType(types = {"video/mp4", "video/wav"})
        MultipartFile file,
        Long patientId,
        String source
) {
}
