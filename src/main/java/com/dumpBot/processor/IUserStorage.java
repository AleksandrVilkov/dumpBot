package com.dumpBot.processor;

import com.dumpBot.model.*;

public interface IUserStorage {
    boolean checkUser(String id);
    User getUser(String id);
    boolean saveUser(User user);

}
