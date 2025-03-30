package org.example.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotEmpty;
import org.example.deserializer.SymbolsDeserializer;
import org.example.deserializer.WinCombinationsDeserializer;

import java.util.List;

public class ConfigData {

    private long columns;

    private long rows;

    @JsonDeserialize(using = SymbolsDeserializer.class)
    @NotEmpty
    private List<Symbol> symbols;

    @JsonDeserialize(using = WinCombinationsDeserializer.class)
    @JsonProperty("win_combinations")
    private List<WinCombination> winCombinations;

    private ProbabilitiesData probabilities;

    public long getColumns() {
        return columns;
    }

    public void setColumns(long columns) {
        this.columns = columns;
    }

    public long getRows() {
        return rows;
    }

    public void setRows(long rows) {
        this.rows = rows;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public List<WinCombination> getWinCombinations() {
        return winCombinations;
    }

    public void setWinCombinations(List<WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }

    public ProbabilitiesData getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(ProbabilitiesData probabilities) {
        this.probabilities = probabilities;
    }
}
