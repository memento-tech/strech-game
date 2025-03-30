package com.memento.tech.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.memento.tech.data.BoardSlot;

import java.io.IOException;

public class BoardSlotDeserializer extends JsonDeserializer<BoardSlot> {

    @Override
    public BoardSlot deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        String[] parts = value.split(":");

        if (parts.length != 2) {
            throw new IllegalStateException("Invalid BoardSlot format: " + value);
        }

        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);

        return new BoardSlot(x, y);
    }
}
