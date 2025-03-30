package com.memento.tech.service;

import com.memento.tech.data.Symbol;
import com.memento.tech.data.WinCombination;

import java.util.List;
import java.util.Map;

public interface WinCombinationsService {

    Map<Symbol, List<WinCombination>> getWinCombinations(List<List<Symbol>> symbols, List<WinCombination> availableWinCombinations);
}
