package com.dumpBot.http;


import com.dumpBot.model.City;
import com.dumpBot.model.http.HttpRequest;
import com.dumpBot.model.http.HttpResponse;
import com.dumpBot.model.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/place")
public class CityController {
    @Autowired
    ICityService cityService;

    @GetMapping("/countries")
    public HttpResponse getCountries() {
        List<String> countries = cityService.getCountries();
        return new HttpResponse(HttpStatus.SC_OK, countries);
    }


    @PostMapping("/city")
    public HttpResponse getCity(@RequestBody HttpRequest request) {
        if (request.getPattern() == null || request.getPattern().equalsIgnoreCase("")) {
            return new HttpResponse(HttpStatus.SC_BAD_REQUEST, "pattern is empty");
        }
        List<City> cities = cityService.getCityByPattern(request.getPattern());
        return new HttpResponse(HttpStatus.SC_OK, cities);
    }

}
