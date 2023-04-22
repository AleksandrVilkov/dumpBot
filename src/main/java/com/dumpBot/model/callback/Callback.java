package com.dumpBot.model.callback;

import com.dumpBot.model.enums.CallbackSubsection;
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
    private String userId;
    private CallbackSubsection subsection;

    private String price;
    private com.dumpBot.model.enums.Action Action;
    private UserData userData;
    private CarData carData;
    private List<String> photos;
    private String description;

    public Callback() {
        this.photos = new ArrayList<>();
    }

    public Callback(String userId, com.dumpBot.model.enums.Action action) {
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

