package com.memento.tech.service.impl;

import com.memento.tech.data.BoardSlot;
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
class LinearSymbolsWinCombinationsServiceTest {

    @InjectMocks
    private LinearSymbolsWinCombinationsService linearSymbolsWinCombinationsService;

    @Test
    void testNullParams() {
        var result = linearSymbolsWinCombinationsService.getWinCombinations(null, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testWithEmptyAvailableWinCombinations() {
        var symbol = new Symbol();
        symbol.setType(SymbolType.STANDARD);

        var matrixSymbols = new ArrayList<List<Symbol>>();
        matrixSymbols.add(List.of(symbol));

        var result = linearSymbolsWinCombinationsService.getWinCombinations(matrixSymbols, List.of());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testWithEmptySymbolsList_validCombinations() {
        var linearWinCombination = new WinCombination();
        linearWinCombination.setConditionType(WinConditionType.LINEAR_SYMBOLS);

        var sameSymbolWinCombination = new WinCombination();
        sameSymbolWinCombination.setConditionType(WinConditionType.SAME_SYMBOLS);

        var result = linearSymbolsWinCombinationsService.getWinCombinations(List.of(), List.of(linearWinCombination, sameSymbolWinCombination));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testWithSymbolsList_validCombinations_validArea() {
        var slot = new BoardSlot(0, 0);

        var linearWinCombination = new WinCombination();
        linearWinCombination.setConditionType(WinConditionType.LINEAR_SYMBOLS);
        linearWinCombination.setCoveredAreas(List.of(List.of(slot)));

        var symbol = new Symbol();
        symbol.setType(SymbolType.STANDARD);

        var matrixSymbols = new ArrayList<List<Symbol>>();
        matrixSymbols.add(List.of(symbol));

        var result = linearSymbolsWinCombinationsService.getWinCombinations(matrixSymbols, List.of(linearWinCombination));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey(symbol));
        assertEquals(linearWinCombination, result.get(symbol).get(0));
    }

    @Test
    void testWithSymbolsList_validCombinations_invalidArea() {
        var slot = new BoardSlot(0, 0);
        var otherSlot = new BoardSlot(0, 1);

        var linearWinCombination = new WinCombination();
        linearWinCombination.setConditionType(WinConditionType.LINEAR_SYMBOLS);
        linearWinCombination.setCoveredAreas(List.of(List.of(slot, otherSlot)));

        var symbol = new Symbol();
        symbol.setType(SymbolType.STANDARD);

        var otherSymbol = new Symbol();
        symbol.setType(SymbolType.STANDARD);

        var matrixSymbols = new ArrayList<List<Symbol>>();
        matrixSymbols.add(List.of(symbol, otherSymbol));

        var result = linearSymbolsWinCombinationsService.getWinCombinations(matrixSymbols, List.of(linearWinCombination));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testWithSymbolsList_validCombinations_invalidSlotSizeForY() {
        var slot = new BoardSlot(0, 0);
        var otherSlot = new BoardSlot(0, 1);
        var overflowSlot = new BoardSlot(0, 2);

        var linearWinCombination = new WinCombination();
        linearWinCombination.setConditionType(WinConditionType.LINEAR_SYMBOLS);
        linearWinCombination.setCoveredAreas(List.of(List.of(slot, otherSlot, overflowSlot)));

        var symbol = new Symbol();
        symbol.setType(SymbolType.STANDARD);

        var matrixSymbols = new ArrayList<List<Symbol>>();
        matrixSymbols.add(List.of(symbol, symbol));

        var result = linearSymbolsWinCombinationsService.getWinCombinations(matrixSymbols, List.of(linearWinCombination));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testWithSymbolsList_validCombinations_invalidSlotSizeForX() {
        var slot = new BoardSlot(0, 0);
        var otherSlot = new BoardSlot(0, 1);
        var overflowSlot = new BoardSlot(1, 0);

        var linearWinCombination = new WinCombination();
        linearWinCombination.setConditionType(WinConditionType.LINEAR_SYMBOLS);
        linearWinCombination.setCoveredAreas(List.of(List.of(slot, otherSlot, overflowSlot)));

        var symbol = new Symbol();
        symbol.setType(SymbolType.STANDARD);

        var matrixSymbols = new ArrayList<List<Symbol>>();
        matrixSymbols.add(List.of(symbol, symbol));

        var result = linearSymbolsWinCombinationsService.getWinCombinations(matrixSymbols, List.of(linearWinCombination));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}