package org.example.service.impl;

import org.example.data.ConfigData;
import org.example.data.ProbabilitiesData;
import org.example.service.MatrixGenerator;
import org.example.service.ProbabilitySymbolSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class DefaultMatrixGenerator implements MatrixGenerator {

    private final ProbabilitySymbolSelector probabilitySymbolSelector;

    public DefaultMatrixGenerator(ProbabilitySymbolSelector probabilitySymbolSelector) {
        this.probabilitySymbolSelector = probabilitySymbolSelector;
    }

    @Override
    public List<List<String>> generateMatrix(ConfigData configData) {
        Objects.requireNonNull(configData);

        if (configData.getColumns() <= 0 || configData.getRows() <= 0) {
            throw new IllegalStateException("Configuration data is badly expressed, columns and/or rows can not be of size 0 or less.");
        }

        var resultMatrix = new ArrayList<List<String>>();

        var standardSymbols = Optional.ofNullable(configData.getProbabilities())
                .map(ProbabilitiesData::getStandardSymbols)
                .orElseThrow();

        var defaultStandardProbability = standardSymbols.stream()
                .filter(probability -> probability.getColumn() == 0)
                .filter(probability -> probability.getRow() == 0)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("At least one probability is required!"));

        for (int i = 0; i < configData.getColumns(); i++) {
            var columnSlots = new ArrayList<String>();

            for (int j = 0; j < configData.getRows(); j++) {
                final var column = i;
                final var row = j;

                //Since no explanation is given in the test regarding when and how often to add bonus symbols,
                //in this example 90:10 proportion is taken into account in order to have less bonus symbols.
                var random = new Random().nextInt(100);

                var bonusSymbols = Optional.ofNullable(configData.getProbabilities())
                        .map(ProbabilitiesData::getBonusSymbols)
                        .orElse(null);

                if (random > 10 || Objects.isNull(bonusSymbols)) {
                    var probability = standardSymbols.stream()
                            .filter(p -> p.getColumn() == column && p.getRow() == row)
                            .findAny()
                            .orElse(defaultStandardProbability);

                    columnSlots.add(probabilitySymbolSelector.getSymbolBasedOnProbability(probability));
                } else {
                    columnSlots.add(probabilitySymbolSelector.getSymbolBasedOnProbability(bonusSymbols));
                }
            }

            resultMatrix.add(columnSlots);
        }

        return resultMatrix;
    }
}
