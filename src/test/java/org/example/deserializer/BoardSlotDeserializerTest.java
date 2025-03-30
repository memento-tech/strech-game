package org.example.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.data.BoardSlot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BoardSlotDeserializerTest {

    @Test
    void testEmptyValidInput() throws IOException {
        var json = "\"\"";

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule()
                .addDeserializer(BoardSlot.class, new BoardSlotDeserializer()));

        assertThrows(IllegalStateException.class, () -> objectMapper.readValue(json, BoardSlot.class));
    }

    @Test
    void testNotValidInput() throws IOException {
        var json = "\"1-2\"";

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule()
                .addDeserializer(BoardSlot.class, new BoardSlotDeserializer()));

        assertThrows(IllegalStateException.class, () -> objectMapper.readValue(json, BoardSlot.class));
    }

    @Test
    void testValidInput() throws IOException {
        var json = "\"3:5\"";

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule()
                .addDeserializer(BoardSlot.class, new BoardSlotDeserializer()));

        var result = objectMapper.readValue(json, BoardSlot.class);

        assertNotNull(result);
        assertEquals(3, result.getX());
        assertEquals(5, result.getY());
    }
}