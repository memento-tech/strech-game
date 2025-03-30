package org.example.service;

import org.example.data.Probability;

import java.util.Optional;

public interface ProbabilitySymbolSelector {

    String getSymbolBasedOnProbability(Probability probability);

}
