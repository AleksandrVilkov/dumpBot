package com.dumpBot.service;

import com.dumpBot.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    IServiceStorage serviceStorage;

    public List<City> getCities(String pattern) {
        return serviceStorage.getCities(pattern);
    }

}
