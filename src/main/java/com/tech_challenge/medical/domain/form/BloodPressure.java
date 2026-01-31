package com.tech_challenge.medical.domain.form;

public final class BloodPressure {
    private final int systolic;
    private final int diastolic;

    private BloodPressure(int systolic, int diastolic) {
        validate(systolic, diastolic);
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    public static BloodPressure of(int systolic, int diastolic) {
        return new BloodPressure(systolic, diastolic);
    }

    private void validate(int systolic, int diastolic) {
        if (systolic < 0 || systolic > 300) {
            throw new IllegalArgumentException("Systolic pressure must be between 0 and 300: " + systolic);
        }
        if (diastolic < 0 || diastolic > 200) {
            throw new IllegalArgumentException("Diastolic pressure must be between 0 and 200: " + diastolic);
        }
        if (diastolic >= systolic) {
            throw new IllegalArgumentException("Systolic must be greater than diastolic");
        }
    }

    public int systolic() {
        return systolic;
    }

    public int diastolic() {
        return diastolic;
    }

    public String asString() {
        return systolic + "/" + diastolic;
    }

    public boolean isHigh(int highSystolic, int highDiastolic) {
        return systolic >= highSystolic || diastolic >= highDiastolic;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BloodPressure that = (BloodPressure) obj;
        return systolic == that.systolic && diastolic == that.diastolic;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(systolic);
        result = 31 * result + Integer.hashCode(diastolic);
        return result;
    }

    @Override
    public String toString() {
        return asString() + " mmHg";
    }
}
