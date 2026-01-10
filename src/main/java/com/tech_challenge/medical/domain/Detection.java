package com.tech_challenge.medical.domain;

import java.time.Instant;

public record Detection(
        String label,
        double confidence,
        Instant timestamp
) {
}
