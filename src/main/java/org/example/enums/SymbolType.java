package org.example.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SymbolType {
    STANDARD,
    BONUS;

    @JsonCreator
    public static SymbolType fromString(String value) {
        return switch (value.toLowerCase()) {
            case "standard" -> STANDARD;
            case "bonus" -> BONUS;
            default -> throw new IllegalArgumentException("Unknown value: " + value);
        };
    }
}
