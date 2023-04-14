package com.dumpBot.storage;

import com.dumpBot.processor.IStorage;
import com.dumpBot.model.Callback;
import org.springframework.stereotype.Component;

@Component
public class PSQL implements IStorage {
    @Override
    public boolean saveTempData(String token, Callback callback) {
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
