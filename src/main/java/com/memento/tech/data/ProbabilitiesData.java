package com.memento.tech.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ProbabilitiesData {

    @JsonProperty("standard_symbols")
    @NotEmpty
    private List<Probability> standardSymbols;

    @JsonProperty("bonus_symbols")
    private Probability bonusSymbols;

    public List<Probability> getStandardSymbols() {
        return standardSymbols;
    }

    public void setStandardSymbols(List<Probability> standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    public Probability getBonusSymbols() {
        return bonusSymbols;
    }

    public void setBonusSymbols(Probability bonusSymbols) {
        this.bonusSymbols = bonusSymbols;
    }
}
