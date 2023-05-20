package com.dumpBot.storage;

import com.dumpBot.model.UserAccommodation;

import java.util.List;

public interface IAccommodationStorage {
    boolean saveAccommodation(UserAccommodation accommodation);
    List<UserAccommodation> getAll();
    UserAccommodation getById(int id);
    List<UserAccommodation> getAllInconsistent();
}
