package com.dumpBot.http;

import com.dumpBot.model.Brand;
import com.dumpBot.model.Car;
import com.dumpBot.model.Concern;
import com.dumpBot.model.Model;
import com.dumpBot.model.http.HttpRequest;
import com.dumpBot.model.http.HttpResponse;
import com.dumpBot.model.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/car")
public class CarController {
    @Autowired
    ICarService carService;
    @GetMapping("/allCars")
    public HttpResponse getAllCars() {
        List<Car> cars = carService.getAllCars();
        return new HttpResponse(HttpStatus.SC_OK, cars);
    }
}


