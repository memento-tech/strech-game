package org.example.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.example.data.Symbol;
import org.example.data.WinCombination;
import org.example.enums.SymbolType;
import org.example.service.WinCombinationsService;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.example.enums.WinConditionType.SAME_SYMBOLS;

public class SameSymbolWinCombinationsService implements WinCombinationsService {

    @Override
    public Map<Symbol, List<WinCombination>> getWinCombinations(List<List<Symbol>> matrixSymbols, List<WinCombination> availableWinCombinations) {
        var result = new HashMap<Symbol, List<WinCombination>>();

        var sameSymbolWinCombinations = getSameSymbolWinCombinations(availableWinCombinations);

        CollectionUtils.emptyIfNull(matrixSymbols)
                .stream()
                .flatMap(Collection::stream)
                .filter(symbol -> symbol.getType().equals(SymbolType.STANDARD))
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .forEach((symbol, count) ->
                                findValidWinCombination(count, sameSymbolWinCombinations)
                                        .ifPresent(winCombination -> result.put(symbol, List.of(winCombination))));

        return result;
    }

    private List<WinCombination> getSameSymbolWinCombinations(List<WinCombination> availableWinCombinations) {
        return ListUtils.emptyIfNull(availableWinCombinations)
                .stream()
                .filter(winCombination -> SAME_SYMBOLS.equals(winCombination.getConditionType()))
                .collect(Collectors.toList());
    }

    private Optional<WinCombination> findValidWinCombination(long count, List<WinCombination> sameSymbolWinCombinations) {
        return sameSymbolWinCombinations.stream()
                .filter(combination -> combination.getSymbolsCount() == count)
                .findFirst();
    }
}
