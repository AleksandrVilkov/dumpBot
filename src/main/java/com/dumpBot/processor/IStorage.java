package com.dumpBot.processor;

import com.dumpBot.model.*;
import com.dumpBot.model.callback.Callback;

import java.util.List;

public interface IStorage {

    List<Concern> getConcerns();

    List<City> getCities();

    List<Brand> getBrands(String concern);

    List<Model> getModels(String brand);

    List<Engine> getEngines(String brand, String model);

    List<BoltPattern> getBoltPattern(String brand, String model);

    boolean saveTempData(String token, Callback callback);

    Callback getTempData(String token);

    boolean checkUser(String id);

    User getUser(String id);

    boolean saveUser(User user);

    boolean saveSearchTerms(UserSearchRequest userSearchRequest);
}
