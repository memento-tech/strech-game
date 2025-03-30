package org.example;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.example.data.ConfigData;
import org.example.data.ResultData;
import org.example.data.Symbol;
import org.example.data.WinCombination;
import org.example.enums.SymbolType;
import org.example.facade.WinCombinationsFacade;
import org.example.facade.impl.DefaultWinCombinationsFacade;
import org.example.service.MatrixGenerator;
import org.example.service.ProbabilitySymbolSelector;
import org.example.service.RewardCalculationService;
import org.example.service.WinCombinationsService;
import org.example.service.impl.CumulativeSumSymbolSelector;
import org.example.service.impl.DefaultMatrixGenerator;
import org.example.service.impl.DefaultRewardCalculationService;
import org.example.service.impl.LinearSymbolsWinCombinationsService;
import org.example.service.impl.SameSymbolWinCombinationsService;

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
