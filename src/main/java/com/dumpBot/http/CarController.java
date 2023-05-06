package com.dumpBot.http;

import com.dumpBot.model.Car;
import com.dumpBot.model.http.HttpResponse;
import com.dumpBot.model.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("/api/car")
public class CarController {
    @Autowired
    ICarService carService;
    @GetMapping("/allCars")
    public HttpResponse getAllCars() {
        List<Car> cars = carService.getAllCars();
        return new HttpResponse(HttpStatus.SC_OK, cars);
    }
}


