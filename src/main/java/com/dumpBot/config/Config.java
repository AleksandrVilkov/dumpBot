package com.dumpBot.config;

import com.dumpBot.config.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;

import java.io.File;

public class Config {
    private BotConfig bot;
    private CommandsConfig commands;
    private InternalCommands internalCommands;
    private ValidateData validateData;
    private StorageConfig storageConfig;

    public BotConfig getBot() { return bot; }
    public void setBot(BotConfig value) { this.bot = value; }

    public CommandsConfig getCommands() { return commands; }
    public void setCommands(CommandsConfig value) { this.commands = value; }

    public InternalCommands getInternalcommands() { return internalCommands; }
    public void setInternalCommands(InternalCommands value) { this.internalCommands = value; }

    public ValidateData getValidateData() { return validateData; }
    public void setValidateData(ValidateData value) { this.validateData = value; }

    public StorageConfig getStorageConfig() { return storageConfig; }
    public void setStorageConfig(StorageConfig value) { this.storageConfig = value; }

    @SneakyThrows
    public static Config init() {
        File file = new File("/home/vilkov/IdeaProjects/dumpBot/src/main/resources/config.yaml");
        // Создание нового ObjectMapper как YAMLFactory
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        return om.readValue(file, Config.class);
    }
}


