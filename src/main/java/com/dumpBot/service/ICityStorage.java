package com.dumpBot.service;

import com.dumpBot.model.City;

import java.util.List;

public interface ICityStorage {

    List<String> getAllCountries();

    List<City> getCityByPattern(String pattern);
    List<City> getAllCities();
}
