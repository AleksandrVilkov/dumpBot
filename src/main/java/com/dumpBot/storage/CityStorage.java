package com.dumpBot.storage;


import com.dumpBot.model.City;
import com.dumpBot.service.ICityStorage;
import com.dumpBot.storage.entity.RegionEntity;
import com.dumpBot.storage.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CityStorage implements ICityStorage {
    @Autowired
    RegionRepository regionRepository;

    @Override
    public List<String> getAllCountries() {

        //TODO реализовать
        return null;
    }

    @Override
    public List<City> getCityByPattern(String pattern) {
        //TODO реализовать
        return null;
    }
}
