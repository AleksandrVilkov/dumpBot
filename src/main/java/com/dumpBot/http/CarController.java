package com.dumpBot.http;

import com.dumpBot.model.*;
import com.dumpBot.model.http.HttpResponse;
import com.dumpBot.model.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public HttpResponse getConcerns(@RequestBody String pattern) {
        List<Concern> concerns = carService.getConcernsByPattern(pattern);
        List<String> resp = new ArrayList<>();
        for (Concern concern : concerns) {
            resp.add(concern.getName());
        }
        return new HttpResponse(HttpStatus.SC_OK, resp);
    }

    @PostMapping("/brands")
    public HttpResponse getBrands(@RequestBody String concern,
                                  @RequestBody String pattern) {
        List<Brand> brands = carService.getBrandsByPattern(concern, pattern);
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
        List<Model> models = carService.getModelsByPattern(concern, brand, pattern);
        List<String> resp = new ArrayList<>();
        for (Model m : models) {
            resp.add(m.getName());
        }
        return new HttpResponse(HttpStatus.SC_OK, resp);
    }

    @PostMapping("/engines")
    public HttpResponse getEngines(@RequestBody String concern,
                                   @RequestBody String brand,
                                   @RequestBody String model,
                                   @RequestBody String pattern) {
        List<Engine> engines = carService.getEnginesByPattern(concern, brand, model, pattern);
        List<String> resp = new ArrayList<>();
        for (Engine e : engines) {
            resp.add(e.getName());
        }
        return new HttpResponse(HttpStatus.SC_OK, resp);
    }

    @PostMapping("/botPatterns")
    public HttpResponse getBotPatterns(@RequestBody String concern,
                                       @RequestBody String brand,
                                       @RequestBody String model,
                                       @RequestBody String pattern) {
        List<BoltPattern> boltPatterns = carService.getBotPatternsByPattern(concern, brand, model, pattern);
        List<String> resp = new ArrayList<>();
        for (BoltPattern bp : boltPatterns) {
            resp.add(bp.getName());
        }
        return new HttpResponse(HttpStatus.SC_OK, resp);
    }
}


