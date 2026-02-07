package com.tech_challenge.medical.domain.summary;

import com.tech_challenge.medical.domain.events.EmotionLabel;

import java.util.Map;
import java.util.Objects;

public final class EmotionSummary {
    private final EmotionLabel dominantEmotion;
    private final Map<EmotionLabel, Double> distribution;
    private final boolean hadHighConfidencePeaks;

    private EmotionSummary(EmotionLabel dominantEmotion, Map<EmotionLabel, Double> distribution, boolean hadHighConfidencePeaks) {
        this.dominantEmotion = Objects.requireNonNull(dominantEmotion, "Dominant emotion cannot be null");
        this.distribution = Map.copyOf(Objects.requireNonNull(distribution, "Distribution cannot be null"));
        this.hadHighConfidencePeaks = hadHighConfidencePeaks;
    }

    public static EmotionSummary of(EmotionLabel dominantEmotion, Map<EmotionLabel, Double> distribution, boolean hadHighConfidencePeaks) {
        return new EmotionSummary(dominantEmotion, distribution, hadHighConfidencePeaks);
    }

    public EmotionLabel dominantEmotion() {
        return dominantEmotion;
    }

    public Map<EmotionLabel, Double> distribution() {
        return distribution;
    }

    public boolean hadHighConfidencePeaks() {
        return hadHighConfidencePeaks;
    }

    public Map<String, Object> asMap() {
        return Map.of(
                "dominantEmotion", dominantEmotion.name(),
                "distribution", distribution,
                "hadHighConfidencePeaks", hadHighConfidencePeaks
        );
    }
}
