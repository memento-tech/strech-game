package org.example.service;

import org.example.data.Symbol;
import org.example.data.WinCombination;

import java.util.List;
import java.util.Map;

public interface RewardCalculationService {

    double calculateFinalReward(double bettingAmount, Map<Symbol, List<WinCombination>> winCombinations, List<Symbol> appliedBonuses);
}
