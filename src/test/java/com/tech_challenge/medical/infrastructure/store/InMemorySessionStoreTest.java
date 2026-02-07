package com.tech_challenge.medical.infrastructure.store;

import com.tech_challenge.medical.domain.session.CorrelationId;
import com.tech_challenge.medical.domain.session.SessionBuffer;
import com.tech_challenge.medical.infrastructure.config.TriageProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("InMemorySessionStore tests")
class InMemorySessionStoreTest {

    private InMemorySessionStore store;
    private TriageProperties properties;

    @BeforeEach
    void setUp() {
        properties = mock(TriageProperties.class);
        TriageProperties.Session sessionConfig = mock(TriageProperties.Session.class);
        when(properties.session()).thenReturn(sessionConfig);
        when(sessionConfig.ttlMinutes()).thenReturn(10L);
        
        store = new InMemorySessionStore(properties);
    }

    @Nested
    @DisplayName("when saving sessions")
    class SavingTests {

        @Test
        @DisplayName("stores session successfully")
        void storesSession() {
            // Given
            CorrelationId id = CorrelationId.generate();
            SessionBuffer buffer = SessionBuffer.create(id);

            // When
            store.save(buffer);

            // Then
            Optional<SessionBuffer> found = store.findByCorrelationId(id);
            assertTrue(found.isPresent());
            assertEquals(id, found.get().correlationId());
        }

        @Test
        @DisplayName("throws exception when buffer is null")
        void throwsOnNullBuffer() {
            // When & Then
            assertThrows(IllegalArgumentException.class, () -> 
                    store.save(null)
            );
        }

        @Test
        @DisplayName("overwrites existing session with same ID")
        void overwritesExistingSession() {
            // Given
            CorrelationId id = CorrelationId.generate();
            SessionBuffer buffer1 = SessionBuffer.create(id);
            SessionBuffer buffer2 = SessionBuffer.create(id);

            // When
            store.save(buffer1);
            store.save(buffer2);

            // Then
            assertEquals(1, store.size());
        }
    }

    @Nested
    @DisplayName("when finding sessions")
    class FindingTests {

        @Test
        @DisplayName("returns empty when session not found")
        void returnsEmptyWhenNotFound() {
            // Given
            CorrelationId id = CorrelationId.generate();

            // When
            Optional<SessionBuffer> result = store.findByCorrelationId(id);

            // Then
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("throws exception when correlation ID is null")
        void throwsOnNullId() {
            // When & Then
            assertThrows(IllegalArgumentException.class, () -> 
                    store.findByCorrelationId(null)
            );
        }
    }

    @Nested
    @DisplayName("when removing sessions")
    class RemovalTests {

        @Test
        @DisplayName("removes session successfully")
        void removesSession() {
            // Given
            CorrelationId id = CorrelationId.generate();
            SessionBuffer buffer = SessionBuffer.create(id);
            store.save(buffer);

            // When
            store.remove(id);

            // Then
            Optional<SessionBuffer> found = store.findByCorrelationId(id);
            assertTrue(found.isEmpty());
        }

        @Test
        @DisplayName("throws exception when correlation ID is null")
        void throwsOnNullId() {
            // When & Then
            assertThrows(IllegalArgumentException.class, () -> 
                    store.remove(null)
            );
        }

        @Test
        @DisplayName("handles removal of non-existent session")
        void handlesNonExistentRemoval() {
            // Given
            CorrelationId id = CorrelationId.generate();

            // When & Then
            assertDoesNotThrow(() -> store.remove(id));
        }
    }

    @Nested
    @DisplayName("when finding expired sessions")
    class ExpirationTests {

        @Test
        @DisplayName("returns empty list when no sessions")
        void returnsEmptyListWhenNoSessions() {
            // When
            List<CorrelationId> expired = store.findExpiredSessions();

            // Then
            assertTrue(expired.isEmpty());
        }

        @Test
        @DisplayName("does not return recently created sessions")
        void doesNotReturnRecentSessions() {
            // Given
            CorrelationId id = CorrelationId.generate();
            SessionBuffer buffer = SessionBuffer.create(id);
            store.save(buffer);

            // When
            List<CorrelationId> expired = store.findExpiredSessions();

            // Then
            assertTrue(expired.isEmpty());
        }
    }

    @Nested
    @DisplayName("when managing store state")
    class StateManagementTests {

        @Test
        @DisplayName("returns correct size")
        void returnsCorrectSize() {
            // Given
            store.save(SessionBuffer.create(CorrelationId.generate()));
            store.save(SessionBuffer.create(CorrelationId.generate()));

            // When
            int size = store.size();

            // Then
            assertEquals(2, size);
        }

        @Test
        @DisplayName("clears all sessions")
        void clearsAllSessions() {
            // Given
            store.save(SessionBuffer.create(CorrelationId.generate()));
            store.save(SessionBuffer.create(CorrelationId.generate()));

            // When
            store.clear();

            // Then
            assertEquals(0, store.size());
        }
    }
}
