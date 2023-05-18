package com.dumpBot.service;

import com.dumpBot.http.ICarService;
import com.dumpBot.model.Car;
import com.dumpBot.storage.ICarStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService implements ICarService {
    @Autowired
    ICarStorage carStorage;

    @Override
    public List<Car> getAllCars() {
        return carStorage.getAllCars();
    }
}
