package com.tech_challenge.medical.infrastructure.llm;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.google.genai.types.Schema;
import com.tech_challenge.medical.domain.exception.LlmProcessingException;
import com.tech_challenge.medical.domain.summary.ClinicalSummary;
import com.tech_challenge.medical.domain.triage.Confidence;
import com.tech_challenge.medical.domain.triage.Inconsistencies;
import com.tech_challenge.medical.domain.triage.Inconsistency;
import com.tech_challenge.medical.domain.triage.PhysicianNotes;
import com.tech_challenge.medical.domain.triage.RiskFactor;
import com.tech_challenge.medical.domain.triage.RiskFactors;
import com.tech_challenge.medical.domain.triage.TriageLevel;
import com.tech_challenge.medical.domain.triage.TriageResult;
import com.tech_challenge.medical.infrastructure.config.GeminiProperties;
import com.tech_challenge.medical.infrastructure.config.TriageProperties;

import jakarta.annotation.PostConstruct;

@Component
@Profile("!mock-llm")
public class GeminiLlmClient implements LlmClient {
    private static final Logger log = LoggerFactory.getLogger(GeminiLlmClient.class);

    private final Client chatClient;
    private final TriageProperties properties;
    private final ObjectMapper objectMapper;
    private final GenerateContentConfig config;
    private final GeminiProperties geminiProperties;

    @PostConstruct
    public void init() {
        log.info("GeminiLlmClient initialized - using Gemini LLM client for analysis.");
    }

    public GeminiLlmClient(Client client, TriageProperties properties, GeminiProperties geminiProperties) {
        this.geminiProperties = geminiProperties;
        Schema outputSchema = Schema.fromJson(geminiProperties.outputSchema());
        Content systemInstruction = Content.fromParts(Part.fromText(geminiProperties.systemPrompt()));

        config = GenerateContentConfig.builder()
                .responseMimeType(MediaType.APPLICATION_JSON_VALUE)
                .systemInstruction(systemInstruction)
                .responseSchema(outputSchema)
                .build();

        this.chatClient = client;
        this.properties = properties;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public TriageResult analyzeClinicalSummary(ClinicalSummary summary) {
        try {
            String summaryJson = serializeSummary(summary);
            String prompt = buildPrompt(summaryJson);
            String response = callLlm(prompt);
            return parseResponse(response);
        } catch (Exception ex) {
            log.error("LLM processing failed", ex);
            throw new LlmProcessingException("Failed to analyze clinical summary", ex);
        }
    }

    private String serializeSummary(ClinicalSummary summary) {
        try {
            return objectMapper.writeValueAsString(summary.asMap());
        } catch (Exception ex) {
            throw new LlmProcessingException("Failed to serialize clinical summary", ex);
        }
    }

    private String buildPrompt(String summaryJson) {
        String template = properties.llm().promptTemplate();
        if (template == null || template.isBlank()) {
            throw new LlmProcessingException("Prompt template is not configured");
        }
        return template.replace("{summaryJson}", summaryJson);
    }

    private String callLlm(String prompt) {
        try {
            GenerateContentResponse response = chatClient.models.generateContent(geminiProperties.model(), prompt, config);
            return response.text();
        } catch (Exception ex) {
            throw new LlmProcessingException("LLM API call failed", ex);
        }
    }

    @SuppressWarnings("unchecked")
    private TriageResult parseResponse(String response) {
        try {
            String jsonContent = extractJsonFromResponse(response);
            Map<String, Object> parsed = objectMapper.readValue(jsonContent, Map.class);

            return TriageResult.of(
                    parseTriageLevel(parsed),
                    parseRiskFactors(parsed),
                    parseInconsistencies(parsed),
                    parsePhysicianNotes(parsed),
                    parseConfidence(parsed));
        } catch (Exception ex) {
            throw new LlmProcessingException("Failed to parse LLM response: " + response, ex);
        }
    }

    private String extractJsonFromResponse(String response) {
        int start = response.indexOf('{');
        int end = response.lastIndexOf('}');

        if (start == -1 || end == -1 || start >= end) {
            throw new LlmProcessingException("No valid JSON found in response");
        }

        return response.substring(start, end + 1);
    }

    private TriageLevel parseTriageLevel(Map<String, Object> parsed) {
        String level = (String) parsed.get("triageLevel");
        return TriageLevel.valueOf(level);
    }

    @SuppressWarnings("unchecked")
    private RiskFactors parseRiskFactors(Map<String, Object> parsed) {
        List<String> factors = (List<String>) parsed.get("riskFactors");
        if (factors == null || factors.isEmpty()) {
            return RiskFactors.empty();
        }
        return RiskFactors.of(factors.stream().map(RiskFactor::of).toList());
    }

    @SuppressWarnings("unchecked")
    private Inconsistencies parseInconsistencies(Map<String, Object> parsed) {
        List<String> items = (List<String>) parsed.get("inconsistencies");
        if (items == null || items.isEmpty()) {
            return Inconsistencies.empty();
        }
        return Inconsistencies.of(items.stream().map(Inconsistency::of).toList());
    }

    private PhysicianNotes parsePhysicianNotes(Map<String, Object> parsed) {
        String notes = (String) parsed.get("notesForPhysician");
        return notes != null ? PhysicianNotes.of(notes) : PhysicianNotes.empty();
    }

    private Confidence parseConfidence(Map<String, Object> parsed) {
        Object confValue = parsed.get("confidence");
        double value = confValue instanceof Number number ? number.doubleValue() : 0.5;
        return Confidence.of(value);
    }
}
