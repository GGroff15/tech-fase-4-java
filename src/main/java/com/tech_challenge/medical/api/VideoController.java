package com.tech_challenge.medical.api;

import com.tech_challenge.medical.api.dto.VideoUploadRequest;
import com.tech_challenge.medical.application.UploadVideoService;
import com.tech_challenge.medical.domain.VideoUpload;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/videos")
class VideoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    private final UploadVideoService service;

    VideoController(UploadVideoService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upload(@Valid VideoUploadRequest request) throws IOException {
        LOGGER.info("Received video upload request for patient ID: {}", request.patientId());

        VideoUpload videoUpload = new VideoUpload(request.file(), request.patientId(), request.source());

        service.execute(videoUpload);

        return ResponseEntity.ok().build();
    }

}
