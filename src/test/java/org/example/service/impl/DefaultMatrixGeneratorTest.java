package org.example.service.impl;

import org.example.data.ConfigData;
import org.example.data.ProbabilitiesData;
import org.example.data.Probability;
import org.example.service.ProbabilitySymbolSelector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultMatrixGeneratorTest {

    @InjectMocks
    private DefaultMatrixGenerator defaultMatrixGenerator;

    @Mock
    private ProbabilitySymbolSelector probabilitySymbolSelector;

    @Test
    void testNullParams() {
        assertThrows(NullPointerException.class, () -> defaultMatrixGenerator.generateMatrix(null));
    }

    @Test
    void testBadConfigData() {
        assertThrows(IllegalStateException.class, () -> defaultMatrixGenerator.generateMatrix(new ConfigData()));
    }

    @Test
    void testNullProbabilities() {
        var configData = new ConfigData();
        configData.setColumns(1);
        configData.setRows(1);

        assertThrows(NoSuchElementException.class, () -> defaultMatrixGenerator.generateMatrix(configData));
    }

    @Test
    void testNullStandardSymbolProbabilities() {
        var configData = new ConfigData();
        configData.setColumns(1);
        configData.setRows(1);
        configData.setProbabilities(new ProbabilitiesData());

        assertThrows(NoSuchElementException.class, () -> defaultMatrixGenerator.generateMatrix(configData));
    }

    @Test
    void testEmptyStandardSymbolProbabilities() {
        var configData = new ConfigData();
        configData.setRows(1);
        configData.setColumns(1);
        var probabilities = new ProbabilitiesData();
        probabilities.setStandardSymbols(List.of());

        configData.setProbabilities(probabilities);

        assertThrows(IllegalStateException.class, () -> defaultMatrixGenerator.generateMatrix(configData));
    }

    @Test
    void testNonExistingFirstProbability() {
        var configData = new ConfigData();
        configData.setRows(1);
        configData.setColumns(1);
        var probabilities = new ProbabilitiesData();

        var probability = new Probability();
        probability.setColumn(1);
        probability.setRow(1);

        probabilities.setStandardSymbols(List.of(probability));
        configData.setProbabilities(probabilities);

        assertThrows(IllegalStateException.class, () -> defaultMatrixGenerator.generateMatrix(configData));
    }

    @Test
    void testValidOneMatrix() {
        var configData = new ConfigData();
        configData.setRows(1);
        configData.setColumns(1);
        var probabilities = new ProbabilitiesData();

        var probability = new Probability();
        probability.setColumn(0);
        probability.setRow(0);

        probabilities.setStandardSymbols(List.of(probability));
        configData.setProbabilities(probabilities);

        when(probabilitySymbolSelector.getSymbolBasedOnProbability(any()))
                .thenReturn("S");

        var result = defaultMatrixGenerator.generateMatrix(configData);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertFalse(result.get(0).isEmpty());
        assertEquals(1, result.get(0).size());
        assertEquals("S", result.get(0).get(0));
    }
}