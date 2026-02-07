package com.tech_challenge.medical.domain.events;

import com.tech_challenge.medical.domain.triage.Confidence;

import java.util.Objects;

public record TranscriptChunk(
        String text,
        Confidence confidence,
        TimeWindow timeWindow
) {
    public TranscriptChunk {
        Objects.requireNonNull(text, "Text cannot be null");
        Objects.requireNonNull(confidence, "Confidence cannot be null");
        Objects.requireNonNull(timeWindow, "Time window cannot be null");
        
        if (text.isBlank()) {
            throw new IllegalArgumentException("Text cannot be blank");
        }
    }

    public static TranscriptChunk of(String text, Confidence confidence, TimeWindow timeWindow) {
        return new TranscriptChunk(text, confidence, timeWindow);
    }
}
