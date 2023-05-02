package com.dumpBot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Getter
@Setter
@Component
public class Config {
    @Autowired
    BotConfig bot;
    @Autowired
    private CommandsConfig commands;
    @Autowired
    private ValidateData validateData;
    @Autowired
    private WebApp webApp;
}


