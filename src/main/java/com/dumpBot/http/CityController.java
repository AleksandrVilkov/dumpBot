package com.dumpBot.http;

import com.dumpBot.model.http.HttpResponse;
import com.dumpBot.model.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin
@RequestMapping("/api/place")
public class CityController {
    @Autowired
    ICityService cityService;

    @GetMapping("/cities")
    public HttpResponse getCities() {
        return new HttpResponse(HttpStatus.SC_OK, cityService.getAllCities());
    }

}
