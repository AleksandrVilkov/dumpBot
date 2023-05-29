package com.dumpBot.storage;

import com.dumpBot.model.*;

import java.util.List;

public interface IUserStorage {
    boolean checkUser(String id);
    User getUser(String id);
    List<User> getAllUsers();
    List<User> getAllUsersByCarId(int carId);
    boolean saveUser(User user);
    List<User> findAdmins();

}
