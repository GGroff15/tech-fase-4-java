package com.tech_challenge.medical.infrastructure.store;

import com.tech_challenge.medical.domain.session.CorrelationId;
import com.tech_challenge.medical.domain.session.SessionBuffer;
import com.tech_challenge.medical.infrastructure.config.TriageProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemorySessionStore {
    private final ConcurrentHashMap<CorrelationId, SessionBuffer> sessions;
    private final TriageProperties properties;

    public InMemorySessionStore(TriageProperties properties) {
        this.sessions = new ConcurrentHashMap<>();
        this.properties = properties;
    }

    public void save(SessionBuffer buffer) {
        if (buffer == null) {
            throw new IllegalArgumentException("Buffer cannot be null");
        }
        sessions.put(buffer.correlationId(), buffer);
    }

    public Optional<SessionBuffer> findByCorrelationId(CorrelationId correlationId) {
        if (correlationId == null) {
            throw new IllegalArgumentException("Correlation ID cannot be null");
        }
        return Optional.ofNullable(sessions.get(correlationId));
    }

    public void remove(CorrelationId correlationId) {
        if (correlationId == null) {
            throw new IllegalArgumentException("Correlation ID cannot be null");
        }
        sessions.remove(correlationId);
    }

    public List<CorrelationId> findExpiredSessions() {
        long ttlMinutes = properties.session().ttlMinutes();
        List<CorrelationId> expiredIds = new ArrayList<>();
        
        sessions.forEach((id, buffer) -> {
            if (buffer.isExpired(ttlMinutes)) {
                expiredIds.add(id);
            }
        });
        
        return expiredIds;
    }

    public int size() {
        return sessions.size();
    }

    public void clear() {
        sessions.clear();
    }
}
