package com.dumpBot.config;

// Psql.java
public class StorageConfig {
    private String login;
    private String password;
    private String sslMode;
    private String driverName;
    private String databaseName;
    private long attemptsConnection;

    public String getLogin() {
        return login;
    }

    public void setLogin(String value) {
        this.login = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String getSslMode() {
        return sslMode;
    }

    public void setSslMode(String value) {
        this.sslMode = value;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String value) {
        this.driverName = value;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String value) {
        this.databaseName = value;
    }

    public long getAttemptsConnection() {
        return attemptsConnection;
    }

    public void setAttemptsConnection(long value) {
        this.attemptsConnection = value;
    }

    public StorageConfig() {
    }
}
