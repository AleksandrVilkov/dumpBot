package com.dumpBot.storage;

import com.dumpBot.model.*;

import java.util.List;

public interface ICarStorage {

    List<Car> getAllCars();
    Car findCarById(int id);
}
