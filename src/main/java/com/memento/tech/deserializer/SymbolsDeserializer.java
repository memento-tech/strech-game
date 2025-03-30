package com.memento.tech.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.memento.tech.data.Symbol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SymbolsDeserializer extends JsonDeserializer<List<Symbol>> {

    @Override
    public List<Symbol> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        var symbols = new ArrayList<Symbol>();
        var fields = node.fields();

        while (fields.hasNext()) {
            var field = fields.next();

            var symbol = p.getCodec().treeToValue(field.getValue(), Symbol.class);
            symbol.setName(field.getKey());

            symbols.add(symbol);
        }

        return symbols;
    }
}
