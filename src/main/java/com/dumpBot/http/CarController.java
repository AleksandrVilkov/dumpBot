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
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/car")
public class CarController {
    @Autowired
    ICarService carService;
    @PostMapping("/concerns")
    public HttpResponse getConcerns(@RequestBody HttpRequest request) {
        if (request.getPattern() == null || request.getPattern().equalsIgnoreCase("")) {
            return new HttpResponse(HttpStatus.SC_BAD_REQUEST, "pattern is empty");
        }
        List<Concern> concerns = carService.getConcernsByPattern(request.getPattern());
        List<String> resp = new ArrayList<>();
        for (Concern concern : concerns) {
            resp.add(concern.getName());
        }
        return new HttpResponse(HttpStatus.SC_OK, resp);
    }

    @PostMapping("/brands")
    public HttpResponse getBrands(@RequestBody HttpRequest request) {
        List<Brand> brands = carService.getBrandsByPattern(new Concern(request.getConcern()), request.getPattern());
        List<String> resp = new ArrayList<>();
        for (Brand b : brands) {
            resp.add(b.getName());
        }
        return new HttpResponse(HttpStatus.SC_OK, resp);
    }
    @PostMapping("/models")
    public HttpResponse getModels(@RequestBody HttpRequest request) {
        List<Model> models = carService.getModelsByPattern(new Concern(request.getConcern()),
                new Brand(request.getBrand()), request.getPattern());

        List<String> resp = new ArrayList<>();
        for (Model m : models) {
            resp.add(m.getName());
        }
        return new HttpResponse(HttpStatus.SC_OK, resp);
    }
    @PostMapping("/cars")
    public HttpResponse getCars(@RequestBody HttpRequest request) {
        List<Car> cars = carService.getCars(new Concern(request.getConcern()),
                new Brand(request.getBrand()), new Model(request.getModel()));
        return new HttpResponse(HttpStatus.SC_OK, cars);
    }
}


