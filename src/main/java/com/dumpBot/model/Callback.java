package com.dumpBot.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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

