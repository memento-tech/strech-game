package org.example.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.data.Symbol;
import org.example.enums.SymbolType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SymbolsDeserializerTest {

    @Test
    @SuppressWarnings("unchecked")
    void testDeserializeEmpty() throws IOException {
        var json = "{}";

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule()
                .addDeserializer(List.class, new SymbolsDeserializer()));

        List<Symbol> symbols = objectMapper.readValue(json, List.class);

        assertNotNull(symbols);
        assertEquals(0, symbols.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testDeserialize() throws IOException {
        var json = """
                    {
                        "A": {
                                "reward_multiplier": 5,
                                "type": "standard"
                        },
                       "B": {
                                "reward_multiplier": 3,
                                "type": "bonus"
                        }
                    }
                """;

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule()
                .addDeserializer(List.class, new SymbolsDeserializer()));

        List<Symbol> symbols = objectMapper.readValue(json, List.class);

        assertNotNull(symbols);
        assertEquals(2, symbols.size());

        var first = symbols.get(0);
        assertEquals("A", first.getName());
        assertEquals(SymbolType.STANDARD, first.getType());

        var second = symbols.get(1);
        assertEquals("B", second.getName());
        assertEquals(SymbolType.BONUS, second.getType());
    }
}