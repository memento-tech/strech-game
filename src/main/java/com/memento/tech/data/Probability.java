package com.memento.tech.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Map;

public class Probability {

    @JsonIgnore
    private int column;

    @JsonIgnore
    private int row;

    @JsonProperty("symbols")
    @NotEmpty
    private Map<String, Integer> symbolsProbability;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Map<String, Integer> getSymbolsProbability() {
        return symbolsProbability;
    }

    public void setSymbolsProbability(Map<String, Integer> symbolsProbability) {
        this.symbolsProbability = symbolsProbability;
    }
}
