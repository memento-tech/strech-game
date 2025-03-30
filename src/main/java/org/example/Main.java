package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.example.data.ConfigData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.example.Constants.BETTING_AMOUNT_INPUT_PARAM;
import static org.example.Constants.CONFIG_INPUT_PARAM;

public class Main {

    private static final ObjectMapper objectMapper;
    private static final GameEngine gameEngine;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        gameEngine = new GameEngine();
    }

    public static void main(String[] args) throws JsonProcessingException {
        String configFile = null;
        double bettingAmount = 0;

        for (int i = 0; i < args.length; i++) {
            if (CONFIG_INPUT_PARAM.equals(args[i]) && i + 1 < args.length) {
                configFile = args[++i];
            } else if (BETTING_AMOUNT_INPUT_PARAM.equals(args[i]) && i + 1 < args.length) {
                var inputValue = args[++i];
                try {
                    bettingAmount = Double.parseDouble(inputValue);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid betting amount value: " + inputValue);
                    return;
                }
            }
        }

        if (StringUtils.isBlank(configFile)) {
            System.out.println("Please provide valid configuration file name.");
            return;
        }

        if (bettingAmount == 0) {
            System.out.println("No money no play ;)");
            return;
        }

        var configData = getConfigData(configFile);

        var resultData = gameEngine.play(configData, bettingAmount);

        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultData));
    }

    private static ConfigData getConfigData(String configFile) {
        ConfigData configData = null;

        try (var reader = Files.newInputStream(Paths.get(configFile))) {
            String content = new String(reader.readAllBytes());

            configData = objectMapper.readValue(content, ConfigData.class);
        } catch (IOException exception) {
            System.out.println("Error reading config file: " + exception.getMessage());
        }

        return configData;
    }
}