package com.dumpBot.processor;

import com.dumpBot.model.Callback;

public interface IStorage {
    boolean saveTempData(String token, Callback callback);
    Callback getTempData(String token);
    boolean CheckUser(String id);
}
