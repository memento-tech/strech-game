package com.memento.tech;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import com.memento.tech.data.ConfigData;
import com.memento.tech.data.ResultData;
import com.memento.tech.data.Symbol;
import com.memento.tech.data.WinCombination;
import com.memento.tech.enums.SymbolType;
import com.memento.tech.facade.WinCombinationsFacade;
import com.memento.tech.facade.impl.DefaultWinCombinationsFacade;
import com.memento.tech.service.MatrixGenerator;
import com.memento.tech.service.ProbabilitySymbolSelector;
import com.memento.tech.service.RewardCalculationService;
import com.memento.tech.service.WinCombinationsService;
import com.memento.tech.service.impl.CumulativeSumSymbolSelector;
import com.memento.tech.service.impl.DefaultMatrixGenerator;
import com.memento.tech.service.impl.DefaultRewardCalculationService;
import com.memento.tech.service.impl.LinearSymbolsWinCombinationsService;
import com.memento.tech.service.impl.SameSymbolWinCombinationsService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameEngine {

    private final ProbabilitySymbolSelector probabilitySymbolSelector = new CumulativeSumSymbolSelector();

    private final MatrixGenerator matrixGenerator = new DefaultMatrixGenerator(probabilitySymbolSelector);

    private final WinCombinationsService sameSymbolWinCombinationsService = new SameSymbolWinCombinationsService();

    private final WinCombinationsService linearSymbolsWinCombinationsService = new LinearSymbolsWinCombinationsService();

    private final WinCombinationsFacade winCombinationsFacade = new DefaultWinCombinationsFacade(List.of(sameSymbolWinCombinationsService, linearSymbolsWinCombinationsService));

    private final RewardCalculationService rewardCalculationService = new DefaultRewardCalculationService();

    public ResultData play(ConfigData configData, double bettingAmount) {
        Objects.requireNonNull(configData);

        var result = new ResultData();

        var matrix = matrixGenerator.generateMatrix(configData);

        result.setMatrix(matrix);

        var symbolsMatrix = matrix.stream()
                .map(innerList -> innerList.stream()
                        .map(symbolName -> configData.getSymbols().stream().filter(symbol -> symbol.getName().equals(symbolName)).findAny().orElseThrow())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        var winCombinations = winCombinationsFacade.collectWinCombinations(symbolsMatrix, configData.getWinCombinations());

        var parsedWinCombinations = MapUtils.emptyIfNull(winCombinations)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getName(),
                        entry -> ListUtils.emptyIfNull(entry.getValue())
                                .stream()
                                .map(WinCombination::getName)
                                .collect(Collectors.toList())
                ));

        result.setAppliedWinningCombinations(parsedWinCombinations);

        List<Symbol> appliedBonuses = List.of();

        if (MapUtils.isNotEmpty(winCombinations)) {
            appliedBonuses = symbolsMatrix.stream()
                    .flatMap(Collection::stream)
                    .filter(symbol -> symbol.getType().equals(SymbolType.BONUS))
                    .toList();
        }

        var parsedBonuses = appliedBonuses.stream()
                .map(Symbol::getName)
                .toList();

        result.setAppliedBonusSymbol(parsedBonuses);

        result.setReward(rewardCalculationService.calculateFinalReward(bettingAmount, winCombinations, appliedBonuses));

        return result;
    }
}
