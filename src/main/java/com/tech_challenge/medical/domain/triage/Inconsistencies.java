package com.tech_challenge.medical.domain.triage;

import java.util.ArrayList;
import java.util.List;

public final class Inconsistencies {
    private final List<Inconsistency> items;

    private Inconsistencies(List<Inconsistency> items) {
        this.items = new ArrayList<>(items);
    }

    public static Inconsistencies empty() {
        return new Inconsistencies(List.of());
    }

    public static Inconsistencies of(List<Inconsistency> items) {
        if (items == null) {
            throw new IllegalArgumentException("Items list cannot be null");
        }
        return new Inconsistencies(items);
    }

    public Inconsistencies add(Inconsistency item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        List<Inconsistency> newItems = new ArrayList<>(this.items);
        newItems.add(item);
        return new Inconsistencies(newItems);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public List<Inconsistency> toList() {
        return List.copyOf(items);
    }

    public List<String> asStringList() {
        return items.stream()
                .map(Inconsistency::asString)
                .toList();
    }
}
