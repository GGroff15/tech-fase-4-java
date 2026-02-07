package com.tech_challenge.medical.domain.events;

import com.tech_challenge.medical.domain.triage.Confidence;

import java.util.Objects;

public record ObjectDetectionEvent(
        ObjectLabel label,
        Confidence confidence,
        FrameIndex frameIndex
) {
    public ObjectDetectionEvent {
        Objects.requireNonNull(label, "Label cannot be null");
        Objects.requireNonNull(confidence, "Confidence cannot be null");
        Objects.requireNonNull(frameIndex, "Frame index cannot be null");
    }

    public static ObjectDetectionEvent of(ObjectLabel label, Confidence confidence, FrameIndex frameIndex) {
        return new ObjectDetectionEvent(label, confidence, frameIndex);
    }
}
