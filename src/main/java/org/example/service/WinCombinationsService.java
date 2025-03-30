package org.example.service;

import org.example.data.Symbol;
import org.example.data.WinCombination;

import java.util.List;
import java.util.Map;

public interface WinCombinationsService {

    Map<Symbol, List<WinCombination>> getWinCombinations(List<List<Symbol>> symbols, List<WinCombination> availableWinCombinations);
}
