package org.example.service;

import org.example.data.ConfigData;

import java.util.List;

public interface MatrixGenerator {

    List<List<String>> generateMatrix(ConfigData configData);

}
