package com.tech_challenge.medical.domain.exception;

import com.tech_challenge.medical.domain.session.CorrelationId;

public class SessionAlreadyClosedException extends RuntimeException {
    private final CorrelationId correlationId;

    public SessionAlreadyClosedException(CorrelationId correlationId) {
        super("Session already closed: " + correlationId.asString());
        this.correlationId = correlationId;
    }

    public CorrelationId correlationId() {
        return correlationId;
    }
}
