package com.dumpBot.buttonMaker;

import com.dumpBot.model.Callback;

public interface IStorage {
    boolean saveTempData(String token, Callback callback);
}
