package com.dumpBot.storage;

import com.dumpBot.model.User;
import com.dumpBot.model.UserAccommodation;

public interface IAccommodationStorage {
    boolean saveAccommodation(UserAccommodation accommodation);
}
