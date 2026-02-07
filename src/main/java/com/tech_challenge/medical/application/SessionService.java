package com.tech_challenge.medical.application;

import com.tech_challenge.medical.domain.session.CorrelationId;
import com.tech_challenge.medical.domain.session.SessionBuffer;
import com.tech_challenge.medical.infrastructure.store.InMemorySessionStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private static final Logger log = LoggerFactory.getLogger(SessionService.class);

    private final InMemorySessionStore store;

    public SessionService(InMemorySessionStore store) {
        this.store = store;
    }

    public CorrelationId createSession() {
        CorrelationId correlationId = CorrelationId.generate();
        SessionBuffer buffer = SessionBuffer.create(correlationId);
        store.save(buffer);
        
        log.info("Created new session: {}", correlationId.asString());
        return correlationId;
    }

    public void closeSession(CorrelationId correlationId) {
        if (correlationId == null) {
            throw new IllegalArgumentException("Correlation ID cannot be null");
        }
        
        store.remove(correlationId);
        log.info("Closed session: {}", correlationId.asString());
    }
}
