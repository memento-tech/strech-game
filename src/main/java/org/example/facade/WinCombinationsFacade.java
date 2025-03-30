package org.example.facade;

import org.example.data.Symbol;
import org.example.data.WinCombination;

import java.util.List;
import java.util.Map;

public interface WinCombinationsFacade {

    Map<Symbol, List<WinCombination>> collectWinCombinations(List<List<Symbol>> symbolsMatrix, List<WinCombination> winCombinations);
}
