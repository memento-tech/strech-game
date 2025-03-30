package org.example.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.enums.WinConditionType;

import java.util.List;

public class WinCombination {

    @NotBlank
    private String name;

    @JsonProperty("when")
    @NotNull
    private WinConditionType conditionType;

    @NotBlank
    private String group;

    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;

    @JsonProperty("count")
    private int symbolsCount;

    @JsonProperty("covered_areas")
    private List<List<BoardSlot>> coveredAreas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WinConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(WinConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public int getSymbolsCount() {
        return symbolsCount;
    }

    public void setSymbolsCount(int symbolsCount) {
        this.symbolsCount = symbolsCount;
    }

    public List<List<BoardSlot>> getCoveredAreas() {
        return coveredAreas;
    }

    public void setCoveredAreas(List<List<BoardSlot>> coveredAreas) {
        this.coveredAreas = coveredAreas;
    }
}
