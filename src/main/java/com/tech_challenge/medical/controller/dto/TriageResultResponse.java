package com.tech_challenge.medical.controller.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Result produced by the assisted triage process")
public record TriageResultResponse(
        @Schema(description = "Triage level (LOW | MEDIUM | HIGH)", example = "MEDIUM") String triageLevel,
        @Schema(description = "List of detected risk factors") List<String> riskFactors,
        @Schema(description = "Detected inconsistencies in data") List<String> inconsistencies,
        @Schema(description = "Notes for the attending physician") String notesForPhysician,
        @Schema(description = "Confidence score between 0.0 and 1.0", example = "0.85") Double confidence
) {
}
