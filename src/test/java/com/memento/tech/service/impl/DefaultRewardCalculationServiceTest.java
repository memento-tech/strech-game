package com.memento.tech.service.impl;

import com.memento.tech.data.Symbol;
import com.memento.tech.data.WinCombination;
import com.memento.tech.enums.SymbolImpact;
import com.memento.tech.enums.SymbolType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DefaultRewardCalculationServiceTest {

    @InjectMocks
    private DefaultRewardCalculationService defaultRewardCalculationService;

    @Test
    void testNullParams() {
        var result = defaultRewardCalculationService.calculateFinalReward(100, null, null);

        assertEquals(0, result);
    }

    @Test
    void testWithWinCombinations_withoutBonuses() {
        var winCombination = new WinCombination();
        winCombination.setRewardMultiplier(10);

        var winCombinations = new HashMap<Symbol, List<WinCombination>>();
        winCombinations.put(new Symbol(), List.of(winCombination));

        var result = defaultRewardCalculationService.calculateFinalReward(100, winCombinations, null);

        assertEquals(1000, result);
    }

    @Test
    void testWithWinCombinations_withoutBonuses_multipleWinsSameSymbol() {
        var winCombination = new WinCombination();
        winCombination.setRewardMultiplier(10);

        var otherWinCombination = new WinCombination();
        otherWinCombination.setRewardMultiplier(5);

        var winCombinations = new HashMap<Symbol, List<WinCombination>>();
        winCombinations.put(new Symbol(), List.of(winCombination, otherWinCombination));

        var result = defaultRewardCalculationService.calculateFinalReward(100, winCombinations, null);

        assertEquals(100 * 10 * 5, result);
    }

    @Test
    void testWithWinCombinations_withoutBonuses_multipleWinsDifferentSymbol() {
        var winCombination = new WinCombination();
        winCombination.setRewardMultiplier(10);

        var otherWinCombination = new WinCombination();
        otherWinCombination.setRewardMultiplier(5);

        var winCombinations = new HashMap<Symbol, List<WinCombination>>();
        winCombinations.put(new Symbol(), List.of(winCombination));
        winCombinations.put(new Symbol(), List.of(otherWinCombination));

        var result = defaultRewardCalculationService.calculateFinalReward(100, winCombinations, null);

        assertEquals(100 * 10 + 100 * 5, result);
    }

    @Test
    void testWithWinCombinations_withBonuses() {
        var winCombination = new WinCombination();
        winCombination.setRewardMultiplier(10);

        var winCombinations = new HashMap<Symbol, List<WinCombination>>();
        winCombinations.put(new Symbol(), List.of(winCombination));

        var multiplyBonus = new Symbol();
        multiplyBonus.setType(SymbolType.BONUS);
        multiplyBonus.setImpact(SymbolImpact.MULTIPLY_REWARD);
        multiplyBonus.setRewardMultiplier(2);

        var extraBonus = new Symbol();
        extraBonus.setType(SymbolType.BONUS);
        extraBonus.setImpact(SymbolImpact.EXTRA_BONUS);
        extraBonus.setExtra(1);

        var missBonus = new Symbol();
        missBonus.setType(SymbolType.BONUS);
        missBonus.setImpact(SymbolImpact.MISS);

        var bonuses = List.of(multiplyBonus, extraBonus, missBonus);

        var result = defaultRewardCalculationService.calculateFinalReward(100, winCombinations, bonuses);

        assertEquals(100 * 10 * 2 + 1, result);
    }

    @Test
    void testWithoutWinCombinations_withBonuses() {
        var multiplyBonus = new Symbol();
        multiplyBonus.setType(SymbolType.BONUS);
        multiplyBonus.setImpact(SymbolImpact.MULTIPLY_REWARD);
        multiplyBonus.setRewardMultiplier(2);

        var extraBonus = new Symbol();
        extraBonus.setType(SymbolType.BONUS);
        extraBonus.setImpact(SymbolImpact.EXTRA_BONUS);
        extraBonus.setExtra(1);

        var missBonus = new Symbol();
        missBonus.setType(SymbolType.BONUS);
        missBonus.setImpact(SymbolImpact.MISS);

        var bonuses = List.of(multiplyBonus, extraBonus, missBonus);

        var result = defaultRewardCalculationService.calculateFinalReward(100, Map.of(), bonuses);

        assertEquals(0, result);
    }
}