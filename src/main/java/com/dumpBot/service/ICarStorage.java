package com.dumpBot.service;

import com.dumpBot.model.*;

import java.util.List;

public interface ICarStorage {
    List<Concern> getConcernsByPattern(String patter);

    List<Brand> getBrandsByPattern(Concern concern, String pattern);

    List<Model> getModelsByPattern(Concern concern, Brand brand, String pattern);

    List<Car> getCars(Concern concern, Brand brand, Model model);

    List<Car> getAllCars();
}
