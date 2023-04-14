package com.dumpBot.storage;

import com.dumpBot.buttonMaker.IStorage;
import com.dumpBot.model.Callback;
import org.springframework.stereotype.Component;

@Component
public class PSQL implements IStorage {
    @Override
    public boolean saveTempData(String token, Callback callback) {
        return false;
    }
}
