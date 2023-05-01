package com.dumpBot.storage;

import com.dumpBot.model.*;
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
    public List<Car> getAllCars() {
        List<Object[]> response = carRepository.getAllCars();
        List<Car> cars = new ArrayList<>();
        for (Object[] object : response) {
            Car car = new Car();
            car.setId((Integer) object[0]);
            car.setConcern(new Concern((String) object[1]));
            car.setBrand(new Brand((String) object[2]));
            car.setModel(new Model((String) object[3]));
            car.setEngine(new Engine((String) object[4]));
            car.setBoltPattern(new BoltPattern((String) object[5]));
            cars.add(car);
        }
        return cars;
    }

}