package com.dumpBot.storage;

import com.dumpBot.common.Util;
import com.dumpBot.loger.ILogger;
import com.dumpBot.model.*;
import com.dumpBot.model.enums.Role;
import com.dumpBot.processor.IUserStorage;
import com.dumpBot.storage.entity.*;
import com.dumpBot.storage.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserStorage implements IUserStorage {
    @Autowired
    CarRepository carRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    RegionRepository regionRepository;

    @Autowired
    ILogger logger;

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

//    @Override
//    public boolean saveUserAccommodation(UserAccommodation userAccommodation) {
//        UserAccommodationEntity accommodation = new UserAccommodationEntity();
//        accommodation.setType(userAccommodation.getType().toString());
//        accommodation.setCreatedDate(userAccommodation.getCreatedDate());
//        accommodation.setClientLogin(userAccommodation.getClientLogin());
//        accommodation.setClientId(userAccommodation.getClientId());
//        accommodation.setMinPrice(userAccommodation.getMinPrice());
//        accommodation.setMaxPrice(userAccommodation.getMaxPrice());
//        accommodation.setApproved(userAccommodation.isApproved());
//        accommodation.setRejected(userAccommodation.isRejected());
//        accommodation.setTopical(userAccommodation.isTopical());
//        accommodation.setDescription(userAccommodation.getDescription());
//        Set<PhotoEntity> photo = new HashSet<>();
//        if (userAccommodation.getPhotos() != null) {
//            for (String id : userAccommodation.getPhotos()) {
//                PhotoEntity photoEntity = new PhotoEntity();
//                photoEntity.setTelegramId(id);
//                photoEntity.setUserAccommodationEntity(accommodation);
//                photo.add(photoEntity);
//            }
//        }
//        accommodation.setPhoto(photo);
//        searchTermsRepository.save(accommodation);
//        return true;
//    }


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
