package com.tech_challenge.medical.domain.events;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class EmotionEvents {
    private final List<EmotionEvent> events;

    private EmotionEvents(List<EmotionEvent> events) {
        this.events = new ArrayList<>(events);
    }

    public static EmotionEvents empty() {
        return new EmotionEvents(List.of());
    }

    public static EmotionEvents of(List<EmotionEvent> events) {
        if (events == null) {
            throw new IllegalArgumentException("Events list cannot be null");
        }
        return new EmotionEvents(events);
    }

    public EmotionEvents add(EmotionEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        List<EmotionEvent> newEvents = new ArrayList<>(this.events);
        newEvents.add(event);
        return new EmotionEvents(newEvents);
    }

    public EmotionLabel dominantEmotion() {
        if (events.isEmpty()) {
            return EmotionLabel.NEUTRAL;
        }

        return events.stream()
                .collect(Collectors.groupingBy(EmotionEvent::emotion, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(EmotionLabel.NEUTRAL);
    }

    public Map<EmotionLabel, Double> distributionPercentages() {
        if (events.isEmpty()) {
            return Map.of();
        }

        long total = events.size();
        return events.stream()
                .collect(Collectors.groupingBy(
                        EmotionEvent::emotion,
                        Collectors.collectingAndThen(Collectors.counting(), count -> (count * 100.0) / total)
                ));
    }

    public List<EmotionEvent> highConfidencePeaks() {
        return events.stream()
                .filter(event -> event.confidence().isHigh())
                .sorted(Comparator.comparing(EmotionEvent::timestamp))
                .toList();
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }

    public int size() {
        return events.size();
    }

    public List<EmotionEvent> toList() {
        return List.copyOf(events);
    }
}
