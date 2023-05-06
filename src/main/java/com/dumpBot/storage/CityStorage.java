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
    public List<City> getAllCities() {
       List<Object[]> result = regionRepository.getAllCities();
       List<City> cities = new ArrayList<>();
       for (Object[] o: result) {
           City city = new City();
           city.setId((Integer) o[0]);
           city.setName((String) o[1]);
           city.setCountryCode((String) o[2]);
           city.setRegionId((String) o[3]);
           cities.add(city);
       }

        return cities;
    }
}
