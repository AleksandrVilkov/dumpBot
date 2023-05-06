package com.dumpBot.service;

import com.dumpBot.http.ICityService;
import com.dumpBot.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService implements ICityService {

    @Autowired
    ICityStorage cityStorage;

    @Override
    public List<City> getAllCities() {
        return cityStorage.getAllCities();
    }
}
