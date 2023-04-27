package com.dumpBot.storage;

import com.dumpBot.model.Brand;
import com.dumpBot.model.Car;
import com.dumpBot.model.Concern;
import com.dumpBot.model.Model;
import com.dumpBot.service.ICarStorage;
import com.dumpBot.storage.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarStorage implements ICarStorage {
    @Autowired
    CarRepository carRepository;

    @Override
    public List<Concern> getConcernsByPattern(String pattern) {
        List<Object[]> response = carRepository.getConcernByPattern(pattern);
        List<Concern> concerns = new ArrayList<>();
        for (Object[] object : response) {
            for (Object o : object) {
                Concern concern = new Concern();
                concern.setName((String) o);
                concerns.add(concern);
            }
        }
        return concerns;
    }

    @Override
    public List<Brand> getBrandsByPattern(Concern concern, String pattern) {
        List<Object[]> response = carRepository.getBrandByPattern(concern.getName(), pattern);
        List<Brand> brands = new ArrayList<>();
        for (Object[] object : response) {
            for (Object o : object) {
                Brand brand = new Brand();
                brand.setName((String) o);
                brands.add(brand);
            }
        }
        return brands;
    }

    @Override
    public List<Model> getModelsByPattern(Concern concern, Brand brand, String pattern) {
        List<Object[]> response = carRepository.getModelByPattern(concern.getName(), brand.getName(), pattern);
        List<Model> models = new ArrayList<>();
        for (Object[] object : response) {
            for (Object o : object) {
                Model model = new Model();
                model.setName((String) o);
                models.add(model);
            }
        }
        return models;
    }

    @Override
    public List<Car> getCars(Concern concern, Brand brand, Model model) {
        List<Object[]> response = carRepository.getCars(concern.getName(), brand.getName(), model.getName());
        List<Car> cars = new ArrayList<>();
        for (Object[] object : response) {
            for (Object o : object) {
                //TODO заполнять
                Car car = new Car();
                car.setBrand(new Brand((String) o));
                cars.add(car);
            }
        }
        return cars;
    }

}