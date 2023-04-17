package com.dumpBot.processor;

import com.dumpBot.model.*;
import com.dumpBot.model.callback.Callback;

import java.util.List;

public interface IStorage {

    List<Concern> getConcerns();

    List<City> getCities();

    List<Brand> getBrands();

    List<Model> getModels();

    List<Engine> getEngines();

    List<BoltPattern> getBoltPattern();


    boolean saveTempData(String token, Callback callback);

    Callback getTempData(String token);

    boolean CheckUser(String id);

    boolean SaveUser(User user);
}
