package com.tech_challenge.medical.domain.exception;

import com.tech_challenge.medical.domain.session.CorrelationId;

public class SessionNotFoundException extends RuntimeException {
    private final CorrelationId correlationId;

    public SessionNotFoundException(CorrelationId correlationId) {
        super("Session not found: " + correlationId.asString());
        this.correlationId = correlationId;
    }

    public CorrelationId correlationId() {
        return correlationId;
    }
}
