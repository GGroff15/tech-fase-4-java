package com.tech_challenge.medical.infraestructure.dto;

import org.springframework.core.io.FileSystemResource;

public record ProcessVideoRequest(
        FileSystemResource video,
        int framesPerSecond
) {
}
