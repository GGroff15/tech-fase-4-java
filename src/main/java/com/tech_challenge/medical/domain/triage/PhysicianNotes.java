package com.tech_challenge.medical.domain.triage;

import java.util.Objects;

public final class PhysicianNotes {
    private final String notes;

    private PhysicianNotes(String notes) {
        this.notes = Objects.requireNonNull(notes, "Notes cannot be null");
    }

    public static PhysicianNotes of(String notes) {
        return new PhysicianNotes(notes);
    }

    public static PhysicianNotes empty() {
        return new PhysicianNotes("");
    }

    public String asString() {
        return notes;
    }

    public boolean isEmpty() {
        return notes.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PhysicianNotes that = (PhysicianNotes) obj;
        return Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notes);
    }

    @Override
    public String toString() {
        return notes;
    }
}
