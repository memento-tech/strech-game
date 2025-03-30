package com.memento.tech.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.memento.tech.data.Symbol;
import com.memento.tech.data.WinCombination;
import com.memento.tech.enums.WinConditionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WinCombinationsDeserializerTest {

    @Test
    @SuppressWarnings("unchecked")
    void testDeserializeEmpty() throws IOException {
        var json = "{}";

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule()
                .addDeserializer(List.class, new WinCombinationsDeserializer()));

        List<Symbol> symbols = objectMapper.readValue(json, List.class);

        assertNotNull(symbols);
        assertEquals(0, symbols.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testDeserialize() throws IOException {
        var json = """
                    {
                        "same_symbol_3_times": {
                               "reward_multiplier": 1,
                               "when": "same_symbols",
                               "count": 3,
                               "group": "same_symbols"
                        }
                    }
                """;

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule()
                .addDeserializer(List.class, new WinCombinationsDeserializer()));

        List<WinCombination> symbols = objectMapper.readValue(json, List.class);

        assertNotNull(symbols);
        assertEquals(1, symbols.size());

        var first = symbols.get(0);
        assertEquals("same_symbol_3_times", first.getName());
        assertEquals(WinConditionType.SAME_SYMBOLS, first.getConditionType());
        assertEquals(1, first.getRewardMultiplier());
        assertEquals(3, first.getSymbolsCount());
        assertEquals("same_symbols", first.getGroup());
    }
}