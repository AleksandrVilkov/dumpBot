package com.dumpBot.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Msgs {
    private String noSendingModeration;
    private String sendingModeration;
    private String welcomeMessage;
    private String welcomeRegistered;
    private String startSale;
    private String rules;
    private Registration registration;
    private Search search;
    private Photo photo;

}
