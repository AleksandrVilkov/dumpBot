package com.dumpBot.storage;


import com.dumpBot.model.City;
import com.dumpBot.storage.entity.RegionEntity;
import com.dumpBot.storage.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CityStorage {
@Autowired
    RegionRepository regionRepository;

    public List<City> getCities() {
        List<RegionEntity> result = regionRepository.findAll();
        List<City> cities = new ArrayList<>();
        for (RegionEntity c : result) {
            City city = new City();
            city.setRegionId(c.getId());
            city.setName(c.getName());
            cities.add(city);
        }
        return cities;
    }

}
