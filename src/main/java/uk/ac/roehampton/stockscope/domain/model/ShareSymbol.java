package uk.ac.roehampton.stockscope.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public final class ShareSymbol {
    private final String value;

    public ShareSymbol(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Symbol cannot be blank");
        }
        this.value = value.trim().toUpperCase();
    }

    @JsonValue
    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShareSymbol that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}