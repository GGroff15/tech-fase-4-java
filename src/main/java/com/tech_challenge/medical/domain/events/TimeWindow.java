package com.tech_challenge.medical.domain.events;

import java.time.Instant;
import java.util.Objects;

public record TimeWindow(
        Instant start,
        Instant end
) {
    public TimeWindow {
        Objects.requireNonNull(start, "Start time cannot be null");
        Objects.requireNonNull(end, "End time cannot be null");
        
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
    }

    public static TimeWindow of(Instant start, Instant end) {
        return new TimeWindow(start, end);
    }
}
