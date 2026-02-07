package com.tech_challenge.medical.domain.events;

import com.tech_challenge.medical.domain.triage.Confidence;

import java.time.Instant;
import java.util.Objects;

public record EmotionEvent(
        EmotionLabel emotion,
        Confidence confidence,
        Instant timestamp
) {
    public EmotionEvent {
        Objects.requireNonNull(emotion, "Emotion cannot be null");
        Objects.requireNonNull(confidence, "Confidence cannot be null");
        Objects.requireNonNull(timestamp, "Timestamp cannot be null");
    }

    public static EmotionEvent of(EmotionLabel emotion, Confidence confidence, Instant timestamp) {
        return new EmotionEvent(emotion, confidence, timestamp);
    }
}
