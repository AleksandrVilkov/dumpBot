package com.dumpBot.storage;

import com.dumpBot.model.*;
import com.dumpBot.model.callback.Callback;
import com.dumpBot.processor.IStorage;
import com.dumpBot.storage.entity.TempData;
import com.dumpBot.storage.exception.MoreOneResultException;
import com.dumpBot.storage.repository.TempDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class PSQL implements IStorage {
    @Autowired
    TempDataRepository tempDataRepository;

    @Override
    public List<Concern> getConcerns() {
        return null;
    }

    @Override
    public List<City> getCities() {
        return null;
    }

    @Override
    public List<Brand> getBrands() {
        return null;
    }

    @Override
    public List<Model> getModels() {
        return null;
    }

    @Override
    public List<Engine> getEngines() {
        return null;
    }

    @Override
    public List<BoltPattern> getBoltPattern() {
        return null;
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
        if (result.size() > 1) {
            try {
                throw new MoreOneResultException();
            } catch (MoreOneResultException e) {
                throw new RuntimeException(e);
            }
        }
        String stringCallBack = result.get(0).getCallback();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Callback callback = objectMapper.readValue(stringCallBack, Callback.class);
            return callback;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean CheckUser(String id) {
        return false;
    }

    @Override
    public boolean SaveUser(User user) {
        return false;
    }
}
