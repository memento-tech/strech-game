package org.example.facade.impl;

import org.example.data.Symbol;
import org.example.data.WinCombination;
import org.example.service.WinCombinationsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultWinCombinationsFacadeTest {

    @Mock
    private WinCombinationsService winCombinationsServiceFirst;

    @Mock
    private WinCombinationsService winCombinationsServiceSecond;

    @Test
    void testWithNullConstructorParam() {
        var facade = new DefaultWinCombinationsFacade(null);

        var result = facade.collectWinCombinations(List.of(List.of(new Symbol())), List.of(new WinCombination()));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testValid_singleService() {
        var facade = new DefaultWinCombinationsFacade(List.of(winCombinationsServiceFirst));

        var symbol = new Symbol();
        var winCombination = new WinCombination();

        var map = new HashMap<Symbol, List<WinCombination>>();
        map.put(symbol, List.of(winCombination));

        when(winCombinationsServiceFirst.getWinCombinations(any(), any())).thenReturn(map);

        var result = facade.collectWinCombinations(null, null);

        assertNotNull(result);
        assertTrue(result.containsKey(symbol));

        var winCombinations = result.get(symbol);
        assertFalse(winCombinations.isEmpty());
        assertEquals(1, winCombinations.size());
        assertEquals(winCombination, winCombinations.get(0));
    }

    @Test
    void testValid_multipleService() {
        var facade = new DefaultWinCombinationsFacade(List.of(winCombinationsServiceFirst, winCombinationsServiceSecond));

        var symbol = new Symbol();
        var firstWin = new WinCombination();
        var secondWin = new WinCombination();

        var firstMap = new HashMap<Symbol, List<WinCombination>>();
        firstMap.put(symbol, List.of(firstWin));

        var secondMap = new HashMap<Symbol, List<WinCombination>>();
        secondMap.put(symbol, List.of(secondWin));

        when(winCombinationsServiceFirst.getWinCombinations(any(), any())).thenReturn(firstMap);
        when(winCombinationsServiceSecond.getWinCombinations(any(), any())).thenReturn(secondMap);

        var result = facade.collectWinCombinations(null, null);

        assertNotNull(result);
        assertTrue(result.containsKey(symbol));

        var winCombinations = result.get(symbol);
        assertFalse(winCombinations.isEmpty());
        assertEquals(2, winCombinations.size());
        assertTrue(winCombinations.contains(firstWin));
        assertTrue(winCombinations.contains(secondWin));
    }
}