package com.dumpBot.service;

import com.dumpBot.http.ICarService;
import com.dumpBot.model.Brand;
import com.dumpBot.model.Car;
import com.dumpBot.model.Concern;
import com.dumpBot.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService implements ICarService {
    @Autowired
    ICarStorage carStorage;


    @Override
    public List<Concern> getConcernsByPattern(String pattern) {
        return carStorage.getConcernsByPattern(pattern);
    }

    @Override
    public List<Brand> getBrandsByPattern(Concern concern, String pattern) {
        return carStorage.getBrandsByPattern(concern, pattern);
    }

    @Override
    public List<Model> getModelsByPattern(Concern concern, Brand brand, String pattern) {
        return carStorage.getModelsByPattern(concern, brand, pattern);
    }

    @Override
    public List<Car> getCars(Concern concern, Brand brand, Model model) {
        return carStorage.getCars(concern, brand, model);
    }
}
