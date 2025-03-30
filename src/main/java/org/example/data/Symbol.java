package org.example.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.enums.SymbolImpact;
import org.example.enums.SymbolType;

public class Symbol {

    @NotBlank
    private String name;

    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;

    private int extra;

    @NotNull
    private SymbolType type;

    private SymbolImpact impact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }

    public SymbolType getType() {
        return type;
    }

    public void setType(SymbolType type) {
        this.type = type;
    }

    public SymbolImpact getImpact() {
        return impact;
    }

    public void setImpact(SymbolImpact impact) {
        this.impact = impact;
    }
}
