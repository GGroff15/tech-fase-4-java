package com.tech_challenge.medical.application;

import com.tech_challenge.medical.domain.events.*;
import com.tech_challenge.medical.domain.exception.SessionNotFoundException;
import com.tech_challenge.medical.domain.session.CorrelationId;
import com.tech_challenge.medical.domain.session.SessionBuffer;
import com.tech_challenge.medical.infrastructure.store.InMemorySessionStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BufferService {
    private static final Logger log = LoggerFactory.getLogger(BufferService.class);

    private final InMemorySessionStore store;

    public BufferService(InMemorySessionStore store) {
        this.store = store;
    }

    public void appendEvent(CorrelationId correlationId, EmotionEvent event) {
        SessionBuffer buffer = findBuffer(correlationId);
        BufferedEvents updatedEvents = buffer.events().addEmotion(event);
        store.save(buffer.updateEvents(updatedEvents));
        
        log.debug("Appended emotion event to session: {}", correlationId);
        log.trace("Emotion event details: {}", event);
    }

    public void appendEvent(CorrelationId correlationId, TranscriptChunk chunk) {
        SessionBuffer buffer = findBuffer(correlationId);
        BufferedEvents updatedEvents = buffer.events().addTranscript(chunk);
        store.save(buffer.updateEvents(updatedEvents));
        
        log.debug("Appended transcript chunk to session: {}", correlationId);
        log.trace("Transcript chunk details: {}", chunk);
    }

    public void appendEvent(CorrelationId correlationId, ObjectDetectionEvent detection) {
        SessionBuffer buffer = findBuffer(correlationId);
        BufferedEvents updatedEvents = buffer.events().addObject(detection);
        store.save(buffer.updateEvents(updatedEvents));
        
        log.debug("Appended object detection to session: {}", correlationId);
        log.trace("Object detection details: {}", detection);
    }

    public SessionBuffer findBuffer(CorrelationId correlationId) {
        if (correlationId == null) {
            throw new IllegalArgumentException("Correlation ID cannot be null");
        }
        
        return store.findByCorrelationId(correlationId)
                .orElseThrow(() -> new SessionNotFoundException(correlationId));
    }
}
