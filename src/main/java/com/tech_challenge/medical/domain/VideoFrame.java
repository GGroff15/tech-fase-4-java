package com.tech_challenge.medical.domain;

import java.util.UUID;

public record VideoFrame(
        UUID id,
        Long analysisCaseId,
        String framePath,
        double timestamp
) {
}
