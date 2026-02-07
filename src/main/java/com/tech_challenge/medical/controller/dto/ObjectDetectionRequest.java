package com.tech_challenge.medical.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Object detection event produced by video analysis")
public record ObjectDetectionRequest(
        @NotBlank @Schema(description = "Detected object label", example = "person") String label,
        @NotNull @Schema(description = "Confidence for the detection", example = "0.76") Double confidence,
        @NotNull @Schema(description = "Frame index where the object was detected", example = "1234") Long frameIndex
) {
}
