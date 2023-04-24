package com.dumpBot.service;

import com.dumpBot.model.*;
import com.dumpBot.model.callback.Callback;

import java.util.List;

public interface IServiceStorage {

    List<Concern> getConcerns(String pattern);
    List<Brand> getBrands(String concern, String pattern);
    List<Model> getModels(String concern, String brand, String pattern);
    List<Engine> getEngines(String concern, String brand, String model, String pattern);
    List<BoltPattern> getBoltPattern(String concern, String brand, String model, String pattern);
    List<City> getCities(String pattern);
}
