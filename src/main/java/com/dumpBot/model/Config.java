package com.dumpBot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Config {
    String token;
    Command command;
    InternalCommands internalCommands;
    ValidateData validateData;
    Storage storage;
}

@Getter
@Setter
class Command {
    private String start;
    private String rules;
    private String registration;
    private String subscription;
    private String sale;

}

@Getter
@Setter
class InternalCommands {
    private String enterCarBrand;
    private String enterCarModel;
    private String enterCarEngine;
}

@Getter
@Setter
class ValidateData {
    private int channelID;
    private String channelUrl;
}

@Getter
@Setter
class Storage {
    String login;
    String password;
    String sslMode;
    String driverName;
    String databaseName;
    String attemptsConnection;
}

