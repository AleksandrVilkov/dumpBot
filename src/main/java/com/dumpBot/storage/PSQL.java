package com.dumpBot.storage;

import com.dumpBot.common.Util;
import com.dumpBot.model.*;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.IStorage;
import com.dumpBot.storage.entity.Client;
import com.dumpBot.storage.entity.Region;
import com.dumpBot.storage.entity.TempData;
import com.dumpBot.storage.repository.CarRepository;
import com.dumpBot.storage.repository.ClientRepository;
import com.dumpBot.storage.repository.RegionRepository;
import com.dumpBot.storage.repository.TempDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class PSQL implements IStorage {
    @Autowired
    TempDataRepository tempDataRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    RegionRepository regionRepository;

    @Override
    public List<Concern> getConcerns() {
        List<Object[]> response = carRepository.getUniqueCarConcern();
        List<Concern> concerns = new ArrayList<>();
        for (Object[] object : response) {
            for (Object o : object) {
                Concern concern = new Concern();
                concern.setName((String) o);
                concerns.add(concern);
            }
        }
        return concerns;
    }

    @Override
    public List<City> getCities() {
        List<Region> result = regionRepository.findAll();
        List<City> cities = new ArrayList<>();
        for (Region c : result) {
            City city = new City();
            city.setRegionId(c.getId());
            city.setName(c.getName());
            cities.add(city);
        }
        return cities;
    }

    @Override
    public List<Brand> getBrands(String concern) {
        List<Object[]> response = carRepository.getUniqueCarBrandByConcern(concern);
        List<Brand> brands = new ArrayList<>();
        for (Object[] object : response) {
            for (Object o : object) {
                Brand brand = new Brand();
                brand.setName((String) o);
                brands.add(brand);
            }
        }
        return brands;
    }

    @Override
    public List<Model> getModels(String brand) {
        List<Object[]> response = carRepository.getUniqueCarModelsByBrand(brand);
        List<Model> models = new ArrayList<>();
        for (Object[] object : response) {
            for (Object o : object) {
                Model model = new Model();
                model.setName((String) o);
                models.add(model);
            }
        }
        return models;

    }

    @Override
    public List<Engine> getEngines(String brand, String model) {
        List<Object[]> response = carRepository.getUniqueCarEngine(brand, model);
        List<Engine> engines = new ArrayList<>();
        for (Object[] object : response) {
            for (Object o : object) {
                Engine engine = new Engine();
                engine.setName((String) o);
                engines.add(engine);
            }
        }
        return engines;
    }

    @Override
    public List<BoltPattern> getBoltPattern(String brand, String model) {
        List<Object[]> response = carRepository.getUniqueCarBoltPattern(brand, model);
        List<BoltPattern> bps = new ArrayList<>();
        for (Object[] object : response) {
            for (Object o : object) {
                BoltPattern bp = new BoltPattern();
                bp.setName((String) o);
                bps.add(bp);
            }
        }
        return bps;
    }

    @Override
    public boolean saveTempData(String token, Callback callback) {
        TempData tempData = new TempData();
        tempData.setCallback(callback.toString());
        tempData.setToken(token);
        tempData.setCreatedDate(new Date());
        try {
            TempData result = tempDataRepository.save(tempData);
            return true;
        } catch (Exception e) {
            //TODO логировать эту историю
            return false;
        }
    }

    @Override
    public Callback getTempData(String token) {
        List<TempData> result = tempDataRepository.findByToken(token);
        if (result.size() != 1) {
            throw new RuntimeException("Result size not equals 1!");
        }
        String stringCallBack = result.get(0).getCallback();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(stringCallBack, Callback.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkUser(String id) {
        List<Client> c = clientRepository.findByLogin(id);
        return c.size() != 0;
    }

    public User getUser(String id) {
        List<Client> c = clientRepository.findByLogin(id);
        Client client = c.get(0);
        Region region = regionRepository.findById(client.getRegionId()).get();

        com.dumpBot.storage.entity.Car car = carRepository.findById(client.getCarid()).get();

        return new User(
                client.getId(),
                client.getCreatedDate(),
                Util.findEnumConstant(Role.class, client.getRole()),
                client.getLogin(),
                region.toCity(),
                car.toCarModel(),
                client.isWaitingMessages(),
                client.getClientAction(),
                client.getLastCallback()
        );
    }


    @Override
    public boolean saveUser(User user) {
        Car car = user.getCar();
        int carId;

        if (car.getId() == 0) {
            carId = getCarId(car);
        } else {
            carId = car.getId();
        }

        Client client = convertUserToClient(user, carId);
        try {
            Object o1 = clientRepository.save(client);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private int getCarId(Car car) {
        List<Object[]> o = carRepository.getCar(car.getBrand().getName(), car.getModel().getName(), car.getEngine().getName(), car.getBoltPattern().getName());
        if (o.size() == 1) {
            return (Integer) o.get(0)[0];
        }
        return 0;
    }

    private Client convertUserToClient(User user, Integer carId) {
        Client client = new Client();
        client.setId(user.getId());
        client.setCreatedDate(new Date());
        client.setRole(user.getRole().name());
        client.setRegionId(user.getRegion().getRegionId());
        if (user.getCar().getId() != 0) {
            client.setCarid(user.getCar().getId());
        } else {
            client.setCarid(carId);
        }
        client.setLastCallback(user.getLastCallback());
        client.setLogin(user.getLogin());
        client.setClientAction(user.getClientAction());
        client.setWaitingMessages(user.isWaitingMessages());
        return client;
    }
}
