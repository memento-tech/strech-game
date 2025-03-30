package org.example.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum WinConditionType {
    SAME_SYMBOLS,
    LINEAR_SYMBOLS;

    @JsonCreator
    public static WinConditionType fromString(String value) {
        return switch (value.toLowerCase()) {
            case "same_symbols" -> SAME_SYMBOLS;
            case "linear_symbols" -> LINEAR_SYMBOLS;
            default -> throw new IllegalArgumentException("Unknown value: " + value);
        };
    }
}
