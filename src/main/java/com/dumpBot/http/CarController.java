package com.dumpBot.http;

import com.dumpBot.model.Brand;
import com.dumpBot.model.Car;
import com.dumpBot.model.Concern;
import com.dumpBot.model.Model;
import com.dumpBot.model.http.HttpRequest;
import com.dumpBot.model.http.HttpResponse;
import com.dumpBot.model.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
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
    public HttpResponse getBrands(@RequestBody String concern,
                                  @RequestBody String pattern) {
        List<Brand> brands = carService.getBrandsByPattern(new Concern(concern), pattern);
        List<String> resp = new ArrayList<>();
        for (Brand b : brands) {
            resp.add(b.getName());
        }
        return new HttpResponse(HttpStatus.SC_OK, resp);
    }

    @PostMapping("/models")
    public HttpResponse getModels(@RequestBody String concern,
                                  @RequestBody String brand,
                                  @RequestBody String pattern) {
        List<Model> models = carService.getModelsByPattern(new Concern(concern), new Brand(brand), pattern);
        List<String> resp = new ArrayList<>();
        for (Model m : models) {
            resp.add(m.getName());
        }
        return new HttpResponse(HttpStatus.SC_OK, resp);
    }

    @PostMapping("/cars")
    public HttpResponse getCars(@RequestBody String concern,
                                @RequestBody String brand,
                                @RequestBody String model) {
        List<Car> cars = carService.getCars(new Concern(concern), new Brand(brand), new Model(model));
        return new HttpResponse(HttpStatus.SC_OK, cars);
    }
}


