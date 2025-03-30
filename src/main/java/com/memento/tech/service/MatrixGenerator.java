package com.memento.tech.service;

import com.memento.tech.data.ConfigData;

import java.util.List;

public interface MatrixGenerator {

    List<List<String>> generateMatrix(ConfigData configData);

}
