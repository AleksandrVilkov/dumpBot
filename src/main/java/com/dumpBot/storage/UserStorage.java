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
        return new User(
                clientEntity.getId(),
                clientEntity.getUserName(),
                clientEntity.getCreatedDate(),
                Util.findEnumConstant(Role.class, clientEntity.getRole()),
                clientEntity.getLogin(),
                String.valueOf(clientEntity.getRegionId()),
                String.valueOf(clientEntity.getCarid()),
                clientEntity.isWaitingMessages(),
                clientEntity.getClientAction(),
                clientEntity.getLastCallback()
        );
    }


    @Override
    public boolean saveUser(User user) {
        ClientEntity clientEntity = convertUserToClient(user, Integer.valueOf(user.getCarId()));
        try {
            Object o1 = clientRepository.save(clientEntity);
            return true;
        } catch (Exception e) {
            logger.writeStackTrace(e);
            return false;
        }
    }


    private ClientEntity convertUserToClient(User user, Integer carId) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(user.getId());
        clientEntity.setUserName(user.getUserName());
        clientEntity.setCreatedDate(new Date());
        clientEntity.setRole(user.getRole().name());
        clientEntity.setRegionId(Integer.parseInt(user.getRegionId()));
        if (!user.getCarId().equalsIgnoreCase("")) {
            clientEntity.setCarid(Integer.parseInt(user.getCarId()));
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
