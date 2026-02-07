package com.tech_challenge.medical.domain.exception;

import com.tech_challenge.medical.domain.session.CorrelationId;

public class SessionExpiredException extends RuntimeException {
    private final CorrelationId correlationId;

    public SessionExpiredException(CorrelationId correlationId) {
        super("Session expired: " + correlationId.asString());
        this.correlationId = correlationId;
    }

    public CorrelationId correlationId() {
        return correlationId;
    }
}
