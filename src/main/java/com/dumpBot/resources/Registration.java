package com.dumpBot.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Registration {
    private String choiceConcern;
    private String carBrandEnter;
    private String carModelEnter;
    private String carEngineEnter;
    private String carBoltPatternEnter;
    private String regionEnter;
    private String successRegistration;
    private String errorRegistration;
}
