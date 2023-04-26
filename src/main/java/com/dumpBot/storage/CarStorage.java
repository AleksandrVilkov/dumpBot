package com.dumpBot.storage;

import com.dumpBot.http.ICarService;
import com.dumpBot.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class CarStorage implements ICarService {
    @Override
    public List<Concern> getConcernsByPattern(String pattern) {
        return null;
    }

    @Override
    public List<Brand> getBrandsByPattern(String concern, String pattern) {
        return null;
    }

    @Override
    public List<Model> getModelsByPattern(String concern, String brand, String pattern) {
        return null;
    }

    @Override
    public List<Engine> getEnginesByPattern(String concern, String brand, String model, String pattern) {
        return null;
    }

    @Override
    public List<BoltPattern> getBotPatternsByPattern(String concern, String brand, String model, String pattern) {
        return null;
    }
}
