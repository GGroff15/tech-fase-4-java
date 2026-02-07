package com.tech_challenge.medical.domain.summary;

import com.tech_challenge.medical.domain.events.ObjectLabel;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class VideoSummary {
    private final List<ObjectLabel> mostFrequentObjects;
    private final int totalDetections;

    private VideoSummary(List<ObjectLabel> mostFrequentObjects, int totalDetections) {
        this.mostFrequentObjects = List.copyOf(Objects.requireNonNull(mostFrequentObjects, "Most frequent objects cannot be null"));
        this.totalDetections = validateTotalDetections(totalDetections);
    }

    public static VideoSummary of(List<ObjectLabel> mostFrequentObjects, int totalDetections) {
        return new VideoSummary(mostFrequentObjects, totalDetections);
    }

    private int validateTotalDetections(int totalDetections) {
        if (totalDetections < 0) {
            throw new IllegalArgumentException("Total detections cannot be negative: " + totalDetections);
        }
        return totalDetections;
    }

    public List<ObjectLabel> mostFrequentObjects() {
        return mostFrequentObjects;
    }

    public int totalDetections() {
        return totalDetections;
    }

    public Map<String, Object> asMap() {
        return Map.of(
                "mostFrequentObjects", mostFrequentObjects.stream().map(ObjectLabel::asString).toList(),
                "totalDetections", totalDetections
        );
    }
}
