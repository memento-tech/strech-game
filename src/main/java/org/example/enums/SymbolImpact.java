package org.example.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SymbolImpact {
    MULTIPLY_REWARD,
    EXTRA_BONUS,
    MISS;

    @JsonCreator
    public static SymbolImpact fromString(String value) {
        return switch (value.toLowerCase()) {
            case "multiply_reward" -> MULTIPLY_REWARD;
            case "extra_bonus" -> EXTRA_BONUS;
            case "miss" -> MISS;
            default -> throw new IllegalArgumentException("Unknown value: " + value);
        };
    }
}
