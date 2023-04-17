package com.dumpBot.storage;

import com.dumpBot.model.Callback;
import com.dumpBot.processor.IStorage;
import com.dumpBot.storage.repository.TempDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PSQL implements IStorage {
    @Autowired
    TempDataRepository tempDataRepository;

    @Override
    public boolean saveTempData(String token, Callback callback) {
       // Object save1 = tempDataRepository.findById(1);
        return false;
    }

    @Override
    public Callback getTempData(String token) {
        return null;
    }

    @Override
    public boolean CheckUser(String id) {
        return false;
    }
}
