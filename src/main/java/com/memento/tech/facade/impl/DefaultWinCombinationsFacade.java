package com.memento.tech.facade.impl;

import org.apache.commons.collections4.ListUtils;
import com.memento.tech.data.Symbol;
import com.memento.tech.data.WinCombination;
import com.memento.tech.facade.WinCombinationsFacade;
import com.memento.tech.service.WinCombinationsService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultWinCombinationsFacade implements WinCombinationsFacade {

    private final List<WinCombinationsService> winCombinationsServices;

    public DefaultWinCombinationsFacade(List<WinCombinationsService> winCombinationsServices) {
        this.winCombinationsServices = ListUtils.emptyIfNull(winCombinationsServices);
    }

    @Override
    public Map<Symbol, List<WinCombination>> collectWinCombinations(List<List<Symbol>> symbolsMatrix, List<WinCombination> winCombinations) {
        return winCombinationsServices.stream()
                .map(winCombinationsService -> winCombinationsService.getWinCombinations(symbolsMatrix, winCombinations))
                .filter(Objects::nonNull)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        ListUtils::union
                ));
    }
}
