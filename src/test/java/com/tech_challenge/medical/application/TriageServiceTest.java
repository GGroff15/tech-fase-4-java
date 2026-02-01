package com.tech_challenge.medical.application;

import com.tech_challenge.medical.domain.exception.SessionNotFoundException;
import com.tech_challenge.medical.domain.form.*;
import com.tech_challenge.medical.domain.session.CorrelationId;
import com.tech_challenge.medical.domain.session.SessionBuffer;
import com.tech_challenge.medical.domain.summary.ClinicalSummary;
import com.tech_challenge.medical.domain.triage.*;
import com.tech_challenge.medical.infrastructure.llm.OpenAiLlmClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TriageService tests")
class TriageServiceTest {

    @Mock
    BufferService bufferService;

    @Mock
    SummarizationService summarizationService;

    @Mock
    OpenAiLlmClient llmClient;

    @Mock
    SessionService sessionService;

    @InjectMocks
    TriageService triageService;

    @Nested
    @DisplayName("when processing valid triage request")
    class ValidTriageProcessing {

        @Test
        @DisplayName("completes full triage workflow")
        void completesFullWorkflow() {
            // Given
            CorrelationId correlationId = CorrelationId.generate();
            ClinicalForm form = createForm();
            SessionBuffer buffer = SessionBuffer.create(correlationId);
            ClinicalSummary summary = mock(ClinicalSummary.class);
            TriageResult expectedResult = createTriageResult();

            when(bufferService.findBuffer(correlationId)).thenReturn(buffer);
            when(summarizationService.summarize(buffer, form)).thenReturn(summary);
            when(llmClient.analyzeClinicalSummary(summary)).thenReturn(expectedResult);

            // When
            TriageResult result = triageService.processTriage(correlationId, form);

            // Then
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(TriageLevel.MEDIUM, result.triageLevel()),
                    () -> verify(bufferService).findBuffer(correlationId),
                    () -> verify(summarizationService).summarize(buffer, form),
                    () -> verify(llmClient).analyzeClinicalSummary(summary),
                    () -> verify(sessionService).closeSession(correlationId)
            );
        }

        @Test
        @DisplayName("closes session after successful processing")
        void closesSessionAfterProcessing() {
            // Given
            CorrelationId correlationId = CorrelationId.generate();
            ClinicalForm form = createForm();
            SessionBuffer buffer = SessionBuffer.create(correlationId);

            when(bufferService.findBuffer(correlationId)).thenReturn(buffer);
            when(summarizationService.summarize(any(), any())).thenReturn(mock(ClinicalSummary.class));
            when(llmClient.analyzeClinicalSummary(any())).thenReturn(createTriageResult());

            // When
            triageService.processTriage(correlationId, form);

            // Then
            verify(sessionService, times(1)).closeSession(correlationId);
        }
    }

    @Nested
    @DisplayName("when handling invalid inputs")
    class InvalidInputHandling {

        @Test
        @DisplayName("throws exception when correlation ID is null")
        void throwsOnNullCorrelationId() {
            // Given
            ClinicalForm form = createForm();

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> 
                    triageService.processTriage(null, form)
            );
        }

        @Test
        @DisplayName("throws exception when form is null")
        void throwsOnNullForm() {
            // Given
            CorrelationId correlationId = CorrelationId.generate();

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> 
                    triageService.processTriage(correlationId, null)
            );
        }

        @Test
        @DisplayName("throws exception when session not found")
        void throwsOnSessionNotFound() {
            // Given
            CorrelationId correlationId = CorrelationId.generate();
            ClinicalForm form = createForm();

            when(bufferService.findBuffer(correlationId))
                    .thenThrow(new SessionNotFoundException(correlationId));

            // When & Then
            assertThrows(SessionNotFoundException.class, () -> 
                    triageService.processTriage(correlationId, form)
            );
        }
    }

    @Nested
    @DisplayName("when orchestrating services")
    class ServiceOrchestration {

        @Test
        @DisplayName("calls services in correct order")
        void callsServicesInOrder() {
            // Given
            CorrelationId correlationId = CorrelationId.generate();
            ClinicalForm form = createForm();
            SessionBuffer buffer = SessionBuffer.create(correlationId);
            ClinicalSummary summary = mock(ClinicalSummary.class);

            when(bufferService.findBuffer(correlationId)).thenReturn(buffer);
            when(summarizationService.summarize(buffer, form)).thenReturn(summary);
            when(llmClient.analyzeClinicalSummary(summary)).thenReturn(createTriageResult());

            // When
            triageService.processTriage(correlationId, form);

            // Then
            var inOrder = inOrder(bufferService, summarizationService, llmClient, sessionService);
            inOrder.verify(bufferService).findBuffer(correlationId);
            inOrder.verify(summarizationService).summarize(buffer, form);
            inOrder.verify(llmClient).analyzeClinicalSummary(summary);
            inOrder.verify(sessionService).closeSession(correlationId);
        }
    }

    private ClinicalForm createForm() {
        return ClinicalForm.of(
                PatientComplaint.of("Headache", "1 day", "Moderate"),
                VitalSigns.of(
                        HeartRate.of(80),
                        BloodPressure.of(120, 80),
                        Temperature.ofCelsius(36.8),
                        OxygenSaturation.of(98),
                        RespiratoryRate.of(16)
                ),
                MedicalHistory.of(List.of(), List.of(), List.of())
        );
    }

    private TriageResult createTriageResult() {
        return TriageResult.of(
                TriageLevel.MEDIUM,
                RiskFactors.of(List.of(RiskFactor.of("Moderate pain"))),
                Inconsistencies.empty(),
                PhysicianNotes.of("Patient stable"),
                Confidence.of(0.85)
        );
    }
}
