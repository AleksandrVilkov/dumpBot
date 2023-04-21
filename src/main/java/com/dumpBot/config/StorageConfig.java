package com.dumpBot.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StorageConfig {
    private String login;
    private String password;
    private String sslMode;
    private String driverName;
    private String databaseName;
    private long attemptsConnection;
}
