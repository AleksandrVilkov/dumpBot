package com.dumpBot.http;

import com.dumpBot.model.*;

import java.util.List;

public interface ICarService {
    List<Concern> getConcernsByPattern(String pattern);

    List<Brand> getBrandsByPattern(String concern, String pattern);

    List<Model> getModelsByPattern(String concern, String brand, String pattern);

    List<Engine> getEnginesByPattern(String concern, String brand, String model, String pattern);

    List<BoltPattern> getBotPatternsByPattern(String concern, String brand, String model, String pattern);
}
