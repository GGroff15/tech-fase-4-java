package com.tech_challenge.medical.domain.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class DetectedObjects {
    private final List<ObjectDetectionEvent> detections;

    private DetectedObjects(List<ObjectDetectionEvent> detections) {
        this.detections = new ArrayList<>(detections);
    }

    public static DetectedObjects empty() {
        return new DetectedObjects(List.of());
    }

    public static DetectedObjects of(List<ObjectDetectionEvent> detections) {
        if (detections == null) {
            throw new IllegalArgumentException("Detections list cannot be null");
        }
        return new DetectedObjects(detections);
    }

    public DetectedObjects add(ObjectDetectionEvent detection) {
        if (detection == null) {
            throw new IllegalArgumentException("Detection cannot be null");
        }
        List<ObjectDetectionEvent> newDetections = new ArrayList<>(this.detections);
        newDetections.add(detection);
        return new DetectedObjects(newDetections);
    }

    public List<ObjectLabel> mostFrequentObjects(int topN) {
        if (detections.isEmpty() || topN <= 0) {
            return List.of();
        }

        return detections.stream()
                .collect(Collectors.groupingBy(ObjectDetectionEvent::label, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<ObjectLabel, Long>comparingByValue().reversed())
                .limit(topN)
                .map(Map.Entry::getKey)
                .toList();
    }

    public Map<ObjectLabel, Long> objectFrequencies() {
        return detections.stream()
                .collect(Collectors.groupingBy(ObjectDetectionEvent::label, Collectors.counting()));
    }

    public List<ObjectDetectionEvent> highConfidenceDetections() {
        return detections.stream()
                .filter(detection -> detection.confidence().isHigh())
                .toList();
    }

    public boolean isEmpty() {
        return detections.isEmpty();
    }

    public int size() {
        return detections.size();
    }

    public List<ObjectDetectionEvent> toList() {
        return List.copyOf(detections);
    }
}
