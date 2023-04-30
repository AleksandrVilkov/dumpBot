package com.dumpBot.http;

import com.dumpBot.model.City;

import java.util.List;

public interface ICityService {
    List<String> getCountries();
    List<City> getCityByPattern(String pattern);
}
