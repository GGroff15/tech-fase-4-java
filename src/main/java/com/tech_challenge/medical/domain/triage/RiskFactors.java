package com.tech_challenge.medical.domain.triage;

import java.util.ArrayList;
import java.util.List;

public final class RiskFactors {
    private final List<RiskFactor> factors;

    private RiskFactors(List<RiskFactor> factors) {
        this.factors = new ArrayList<>(factors);
    }

    public static RiskFactors empty() {
        return new RiskFactors(List.of());
    }

    public static RiskFactors of(List<RiskFactor> factors) {
        if (factors == null) {
            throw new IllegalArgumentException("Factors list cannot be null");
        }
        return new RiskFactors(factors);
    }

    public RiskFactors add(RiskFactor factor) {
        if (factor == null) {
            throw new IllegalArgumentException("Factor cannot be null");
        }
        List<RiskFactor> newFactors = new ArrayList<>(this.factors);
        newFactors.add(factor);
        return new RiskFactors(newFactors);
    }

    public boolean isEmpty() {
        return factors.isEmpty();
    }

    public int size() {
        return factors.size();
    }

    public List<RiskFactor> toList() {
        return List.copyOf(factors);
    }

    public List<String> asStringList() {
        return factors.stream()
                .map(RiskFactor::asString)
                .toList();
    }
}
