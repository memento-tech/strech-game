package org.example.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.data.Symbol;
import org.example.data.WinCombination;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WinCombinationsDeserializer extends JsonDeserializer<List<WinCombination>> {

    @Override
    public List<WinCombination> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        List<WinCombination> symbols = new ArrayList<>();
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();

        while (fields.hasNext()) {
            var field = fields.next();

            WinCombination winCombination = p.getCodec().treeToValue(field.getValue(), WinCombination.class);
            winCombination.setName(field.getKey());

            symbols.add(winCombination);
        }

        return symbols;
    }
}
