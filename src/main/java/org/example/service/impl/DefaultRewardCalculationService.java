package org.example.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.example.data.Symbol;
import org.example.data.WinCombination;
import org.example.enums.SymbolType;
import org.example.service.RewardCalculationService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class DefaultRewardCalculationService implements RewardCalculationService {

    @Override
    public double calculateFinalReward(double bettingAmount, Map<Symbol, List<WinCombination>> winCombinations, List<Symbol> appliedBonuses) {
        AtomicReference<Double> reward = new AtomicReference<>(0.0);

        MapUtils.emptyIfNull(winCombinations)
                .forEach((key, value) -> {
                    var winMultiplier = CollectionUtils.emptyIfNull(value)
                            .stream()
                            .map(WinCombination::getRewardMultiplier)
                            .reduce((first, second) -> first * second)
                            .orElse(0.0);

                    reward.set(reward.get() + bettingAmount * winMultiplier);
                });

        if (MapUtils.isNotEmpty(winCombinations)) {
            CollectionUtils.emptyIfNull(appliedBonuses)
                    .stream()
                    .filter(symbol -> SymbolType.BONUS.equals(symbol.getType()))
                    .forEach(bonusSymbol -> {
                        reward.set(
                                switch (bonusSymbol.getImpact()) {
                                    case MULTIPLY_REWARD -> reward.get() * bonusSymbol.getRewardMultiplier();
                                    case EXTRA_BONUS -> reward.get() + bonusSymbol.getExtra();
                                    case MISS -> reward.get();
                                });
                    });
        }

        return reward.get();
    }
}
