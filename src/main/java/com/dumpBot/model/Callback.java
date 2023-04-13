package com.dumpBot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Callback {
    String userId;
    CallbackSubsection subsection;
    Action Action;
    UserData userData;
    CarData carData;

    public Callback() {
    }

    public Callback(String userId, com.dumpBot.model.Action action) {
        this.userId = userId;
        Action = action;
    }
}

@Getter
@Setter
class UserData {
    String regionName;
    int regionId;
}

@Getter
@Setter
class CarData {
    String Concern;
    String Brand;
    String Model;
    String EngineName;
    String BoltPatternSize;
}

