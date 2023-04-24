package com.dumpBot.service;

import com.dumpBot.http.ICarService;
import com.dumpBot.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService implements ICarService {
    @Autowired
    IServiceStorage serviceStorage;

    @Override
    public List<Concern> getConcernsByPattern(String pattern) {
        return serviceStorage.getConcerns(pattern);
    }

    @Override
    public List<Brand> getBrandsByPattern(String concern, String pattern) {
        return serviceStorage.getBrands(concern, pattern);
    }

    @Override
    public List<Model> getModelsByPattern(String concern, String brand, String pattern) {
        return serviceStorage.getModels(concern, brand, pattern);
    }

    @Override
    public List<Engine> getEnginesByPattern(String concern, String brand, String model, String pattern) {
        return serviceStorage.getEngines(concern, brand, model, pattern);
    }

    @Override
    public List<BoltPattern> getBotPatternsByPattern(String concern, String brand, String model, String pattern) {
        return serviceStorage.getBoltPattern(concern, brand, model, pattern);
    }
}
