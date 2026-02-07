package com.tech_challenge.medical.infrastructure.store;

import com.tech_challenge.medical.domain.session.CorrelationId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class SessionCleanupScheduler {
    private static final Logger log = LoggerFactory.getLogger(SessionCleanupScheduler.class);

    private final InMemorySessionStore store;

    public SessionCleanupScheduler(InMemorySessionStore store) {
        this.store = store;
    }

    @Scheduled(fixedRateString = "${triage.session.cleanup-interval-ms:60000}")
    public void cleanupExpiredSessions() {
        List<CorrelationId> expiredIds = store.findExpiredSessions();
        
        if (expiredIds.isEmpty()) {
            return;
        }
        
        expiredIds.forEach(id -> {
            store.remove(id);
            log.info("Removed expired session: {}", id.asString());
        });
        
        log.info("Cleaned up {} expired sessions", expiredIds.size());
    }
}
