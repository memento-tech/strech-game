package com.memento.tech.service.impl;

import com.memento.tech.data.BoardSlot;
import com.memento.tech.data.Symbol;
import com.memento.tech.data.WinCombination;
import com.memento.tech.enums.SymbolType;
import com.memento.tech.enums.WinConditionType;
import org.apache.commons.collections4.CollectionUtils;
import com.memento.tech.service.WinCombinationsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LinearSymbolsWinCombinationsService implements WinCombinationsService {

    @Override
    public Map<Symbol, List<WinCombination>> getWinCombinations(List<List<Symbol>> matrixSymbols, List<WinCombination> availableWinCombinations) {
        var result = new HashMap<Symbol, List<WinCombination>>();

        CollectionUtils.emptyIfNull(availableWinCombinations)
                .stream()
                .filter(winCombination -> WinConditionType.LINEAR_SYMBOLS.equals(winCombination.getConditionType()))
                .forEach(winCombination -> {
                    CollectionUtils.emptyIfNull(winCombination.getCoveredAreas())
                            .stream()
                            .map(slots -> getValidSymbolForCoveredArea(slots, matrixSymbols))
                            .filter(Objects::nonNull)
                            .filter(symbol -> symbol.getType().equals(SymbolType.STANDARD))
                            .forEach(symbol -> {
                                if (result.containsKey(symbol)) {
                                    boolean groupAlreadyAdded = result.get(symbol)
                                            .stream()
                                            .map(WinCombination::getGroup)
                                            .anyMatch(group -> winCombination.getGroup().equals(group));

                                    if (!groupAlreadyAdded) {
                                        result.get(symbol).add(winCombination);
                                    }
                                } else {
                                    var temp = new ArrayList<WinCombination>();
                                    temp.add(winCombination);

                                    result.put(symbol, temp);
                                }
                            });
                });

        return result;
    }

    private Symbol getValidSymbolForCoveredArea(List<BoardSlot> slots, List<List<Symbol>> matrixSymbols) {
        Symbol firstSymbol = null;

        for (BoardSlot slot : slots) {
            if (matrixSymbols.size() <= slot.getX() || matrixSymbols.get(slot.getX()).size() <= slot.getY()) {
                // Board slot is out of range for matrix symbols
                firstSymbol = null;
                break;
            }

            if (Objects.isNull(firstSymbol)) {
                firstSymbol = matrixSymbols.get(slot.getX()).get(slot.getY());
            } else if (!firstSymbol.equals(matrixSymbols.get(slot.getX()).get(slot.getY()))) {
                firstSymbol = null;
                break;
            }
        }

        return firstSymbol;
    }
}
