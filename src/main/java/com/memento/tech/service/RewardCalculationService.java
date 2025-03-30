package com.memento.tech.service;

import com.memento.tech.data.Symbol;
import com.memento.tech.data.WinCombination;

import java.util.List;
import java.util.Map;

public interface RewardCalculationService {

    double calculateFinalReward(double bettingAmount, Map<Symbol, List<WinCombination>> winCombinations, List<Symbol> appliedBonuses);
}
