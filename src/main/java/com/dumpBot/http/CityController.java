package com.dumpBot.http;

import com.dumpBot.model.http.HttpResponse;
import com.dumpBot.model.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/cities")
    public HttpResponse getCities() {
        return new HttpResponse(HttpStatus.SC_OK, cityService.getAllCities());
    }

}
