package com.memento.tech.service.impl;

import com.memento.tech.data.Probability;
import org.apache.commons.collections4.MapUtils;
import com.memento.tech.service.ProbabilitySymbolSelector;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class CumulativeSumSymbolSelector implements ProbabilitySymbolSelector {

    @Override
    public String getSymbolBasedOnProbability(Probability probability) {
        Objects.requireNonNull(probability);

        var percentageProbabilities = calculatePercentages(MapUtils.emptyIfNull(probability.getSymbolsProbability()));

        return getRandomSymbol(percentageProbabilities);
    }

    private Map<String, Double> calculatePercentages(Map<String, Integer> symbolProbabilities) {
        final var totalProbability = symbolProbabilities
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (totalProbability ==  0) {
            throw new IllegalStateException("Total probability can not be 0.");
        }

        return symbolProbabilities.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (entry.getValue() / (double) totalProbability) * 100,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    private String getRandomSymbol(Map<String, Double> percentageProbabilities) {
        var randomValue = new Random().nextDouble() * 100;

        double cumulativeSum = 0;

        for (Map.Entry<String, Double> entry : MapUtils.emptyIfNull(percentageProbabilities).entrySet()) {
            cumulativeSum += entry.getValue();
            if (randomValue <= cumulativeSum) {
                return entry.getKey();
            }
        }

        throw new IllegalStateException("Random symbol not generated, something is wrong with cumulative sum calculation.");
    }
}
