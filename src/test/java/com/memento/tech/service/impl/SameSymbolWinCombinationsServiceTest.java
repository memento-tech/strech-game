package com.memento.tech.service.impl;

import com.memento.tech.data.Symbol;
import com.memento.tech.data.WinCombination;
import com.memento.tech.enums.SymbolType;
import com.memento.tech.enums.WinConditionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SameSymbolWinCombinationsServiceTest {

    @InjectMocks
    private SameSymbolWinCombinationsService sameSymbolWinCombinationsService;

    @Test
    void testWithNullParams() {
        var result = sameSymbolWinCombinationsService.getWinCombinations(null, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testNoAvailableWinCombinations() {
        var symbol = new Symbol();
        symbol.setType(SymbolType.STANDARD);

        var matrixSymbols = new ArrayList<List<Symbol>>();
        matrixSymbols.add(List.of(symbol));

        var result = sameSymbolWinCombinationsService.getWinCombinations(matrixSymbols, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testWithWinCombinations_wrongConditionType() {
        var winCombination = new WinCombination();
        winCombination.setConditionType(WinConditionType.LINEAR_SYMBOLS);
        winCombination.setSymbolsCount(1);

        var symbol = new Symbol();
        symbol.setType(SymbolType.STANDARD);

        var matrixSymbols = new ArrayList<List<Symbol>>();
        matrixSymbols.add(List.of(symbol));

        var result = sameSymbolWinCombinationsService.getWinCombinations(matrixSymbols, List.of(winCombination));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testWithWinCombinations_wrongSymbolType() {
        var winCombination = new WinCombination();
        winCombination.setConditionType(WinConditionType.SAME_SYMBOLS);
        winCombination.setSymbolsCount(1);

        var symbol = new Symbol();
        symbol.setType(SymbolType.BONUS);

        var matrixSymbols = new ArrayList<List<Symbol>>();
        matrixSymbols.add(List.of(symbol));

        var result = sameSymbolWinCombinationsService.getWinCombinations(matrixSymbols, List.of(winCombination));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testWithWinCombinations_totalValidCountOne() {
        var winCombination = new WinCombination();
        winCombination.setConditionType(WinConditionType.SAME_SYMBOLS);
        winCombination.setSymbolsCount(1);

        var standardSymbol = new Symbol();
        standardSymbol.setType(SymbolType.STANDARD);

        var bonusSymbol = new Symbol();
        bonusSymbol.setType(SymbolType.BONUS);

        var matrixSymbols = new ArrayList<List<Symbol>>();
        matrixSymbols.add(List.of(standardSymbol, bonusSymbol));

        var result = sameSymbolWinCombinationsService.getWinCombinations(matrixSymbols, List.of(winCombination));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey(standardSymbol));
        assertEquals(winCombination, result.get(standardSymbol).get(0));
    }
}