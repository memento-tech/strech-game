package com.memento.tech.facade;

import com.memento.tech.data.Symbol;
import com.memento.tech.data.WinCombination;

import java.util.List;
import java.util.Map;

public interface WinCombinationsFacade {

    Map<Symbol, List<WinCombination>> collectWinCombinations(List<List<Symbol>> symbolsMatrix, List<WinCombination> winCombinations);
}
