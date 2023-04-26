package com.dumpBot.model;

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
    private String city;
    private String description;
    private String action;
    private List<String> photos;
}


