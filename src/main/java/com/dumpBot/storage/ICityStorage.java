package com.dumpBot.storage;

import com.dumpBot.model.City;

import java.util.List;

public interface ICityStorage {
    List<City> getAllCities();
    City getCityById(int id);
}
