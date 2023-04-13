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
    String start;
    String rules;
    String registration;
    String subscription;
    String sale;

}

@Getter
@Setter
class InternalCommands {
    String enterCarBrand;
    String enterCarModel;
    String enterCarEngine;
}

@Getter
@Setter
class ValidateData {
    int channelID;
    String channelUrl;
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

