package com.dumpBot.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WebAppData {
    private String concern;
    private String brand;
    private String model;
    private String engine;
    private String price;
    private String boltPattern;
    private String country;
    private String city;
    private String description;
    private String action;
    private List<String> photos;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


