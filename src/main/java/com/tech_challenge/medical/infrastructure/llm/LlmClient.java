package com.tech_challenge.medical.infrastructure.llm;

import com.tech_challenge.medical.domain.summary.ClinicalSummary;
import com.tech_challenge.medical.domain.triage.TriageResult;

public interface LlmClient {

    TriageResult analyzeClinicalSummary(ClinicalSummary summary);

}