package com.dumpBot.model.callback;

import com.dumpBot.model.CallbackSubsection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Callback implements Cloneable {
    String userId;
    CallbackSubsection subsection;
    com.dumpBot.model.Action Action;
    UserData userData;
    CarData carData;
    List<String> photos;
    String description;

    public Callback() {
        this.photos = new ArrayList<>();
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

    @Override
    public Callback clone() {
        try {
            return (Callback) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

