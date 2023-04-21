package com.dumpBot.storage;

import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.*;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.IStorage;
import com.dumpBot.storage.entity.*;
import com.dumpBot.storage.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
    @Autowired
    UserAccommodationRepository searchTermsRepository;

    @Autowired
    ILogger logger;

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
        List<RegionEntity> result = regionRepository.findAll();
        List<City> cities = new ArrayList<>();
        for (RegionEntity c : result) {
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
        try {
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
        } catch (Exception e) {
            logger.writeStackTrace(e);
            throw e;
        }
    }

    @Override
    public boolean saveTempData(String token, Callback callback) {
        TempDataEntity tempDataEntity = new TempDataEntity();
        tempDataEntity.setCallback(callback.toString());
        tempDataEntity.setToken(token);
        tempDataEntity.setCreatedDate(new Date());
        try {
            TempDataEntity result = tempDataRepository.save(tempDataEntity);
            return true;
        } catch (Exception e) {
            logger.writeStackTrace(e);
            return false;
        }
    }

    @Override
    public Callback getTempData(String token) {
        List<TempDataEntity> result = new ArrayList<>();
        try {
            result = tempDataRepository.findByToken(token);
        } catch (Exception e) {
            logger.writeStackTrace(e);
        }

        if (result == null || result.size() != 1) {
            logger.writeError("Result size in getTempData not equals 1!");
            throw new RuntimeException("Result size not equals 1!");
        }
        String stringCallBack = result.get(0).getCallback();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(stringCallBack, Callback.class);
        } catch (JsonProcessingException e) {
            logger.writeStackTrace(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkUser(String id) {
        try {
            List<ClientEntity> c = clientRepository.findByLogin(id);
            return c.size() != 0;
        } catch (Exception e) {
            logger.writeStackTrace(e);
            throw e;
        }
    }

    public User getUser(String id) {
        List<ClientEntity> c = clientRepository.findByLogin(id);
        ClientEntity clientEntity = c.get(0);
        RegionEntity regionEntity = regionRepository.findById(clientEntity.getRegionId()).get();
        CarEntity carEntity = carRepository.findById(clientEntity.getCarid()).get();
        return new User(
                clientEntity.getId(),
                clientEntity.getUserName(),
                clientEntity.getCreatedDate(),
                Util.findEnumConstant(Role.class, clientEntity.getRole()),
                clientEntity.getLogin(),
                regionEntity.toCity(),
                carEntity.toCarModel(),
                clientEntity.isWaitingMessages(),
                clientEntity.getClientAction(),
                clientEntity.getLastCallback()
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
        ClientEntity clientEntity = convertUserToClient(user, carId);
        try {
            Object o1 = clientRepository.save(clientEntity);
            return true;
        } catch (Exception e) {
            logger.writeStackTrace(e);
            return false;
        }
    }

    @Override
    public boolean saveUserAccommodation(UserAccommodation userAccommodation) {
        UserAccommodationEntity accommodation = new UserAccommodationEntity();
        accommodation.setType(userAccommodation.getType().toString());
        accommodation.setCreatedDate(userAccommodation.getCreatedDate());
        accommodation.setClientLogin(userAccommodation.getClientLogin());
        accommodation.setClientId(userAccommodation.getClientId());
        accommodation.setMinPrice(userAccommodation.getMinPrice());
        accommodation.setMaxPrice(userAccommodation.getMaxPrice());
        accommodation.setApproved(userAccommodation.isApproved());
        accommodation.setRejected(userAccommodation.isRejected());
        accommodation.setTopical(userAccommodation.isTopical());
        accommodation.setDescription(userAccommodation.getDescription());
        Set<PhotoEntity> photo = new HashSet<>();
        if (userAccommodation.getPhotos() != null) {
            for (String id : userAccommodation.getPhotos()) {
                PhotoEntity photoEntity = new PhotoEntity();
                photoEntity.setTelegramId(id);
                photoEntity.setUserAccommodationEntity(accommodation);
                photo.add(photoEntity);
            }
        }
        accommodation.setPhoto(photo);
        searchTermsRepository.save(accommodation);
        return true;
    }


    private int getCarId(Car car) {
        List<Object[]> o = carRepository.getCar(car.getBrand().getName(),
                car.getModel().getName(),
                car.getEngine().getName(),
                car.getBoltPattern().getName());
        if (o.size() == 1) {
            return (Integer) o.get(0)[0];
        }
        return 0;
    }

    private ClientEntity convertUserToClient(User user, Integer carId) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(user.getId());
        clientEntity.setUserName(user.getUserName());
        clientEntity.setCreatedDate(new Date());
        clientEntity.setRole(user.getRole().name());
        clientEntity.setRegionId(user.getRegion().getRegionId());
        if (user.getCar().getId() != 0) {
            clientEntity.setCarid(user.getCar().getId());
        } else {
            clientEntity.setCarid(carId);
        }
        clientEntity.setLastCallback(user.getLastCallback());
        clientEntity.setLogin(user.getLogin());
        clientEntity.setClientAction(user.getClientAction());
        clientEntity.setWaitingMessages(user.isWaitingMessages());
        return clientEntity;
    }
}
