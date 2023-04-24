package com.dumpBot.storage;

import com.dumpBot.model.*;
import com.dumpBot.service.IServiceStorage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PSQLService implements IServiceStorage {
    @Override
    public List<Concern> getConcerns(String pattern) {
        return null;
    }

    @Override
    public List<Brand> getBrands(String concern, String pattern) {
        return null;
    }

    @Override
    public List<Model> getModels(String concern, String brand, String pattern) {
        return null;
    }

    @Override
    public List<Engine> getEngines(String concern, String brand, String model, String pattern) {
        return null;
    }

    @Override
    public List<BoltPattern> getBoltPattern(String concern, String brand, String model, String pattern) {
        return null;
    }

    @Override
    public List<City> getCities(String pattern) {
        return null;
    }
}
