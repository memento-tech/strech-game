package com.memento.tech.service;

import com.memento.tech.data.Probability;

public interface ProbabilitySymbolSelector {

    String getSymbolBasedOnProbability(Probability probability);

}
