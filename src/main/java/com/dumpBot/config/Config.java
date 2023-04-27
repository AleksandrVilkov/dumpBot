package com.dumpBot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;

@Getter
@Setter
public class Config {
    private BotConfig bot;
    private CommandsConfig commands;
    private ValidateData validateData;
    private StorageConfig storageConfig;
    private WebApp webApp;
    @SneakyThrows
    public static Config init() {
        File file = new File("/home/vilkov/IdeaProjects/dumpbot/src/main/resources/config.yaml");
        // Создание нового ObjectMapper как YAMLFactory
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        return om.readValue(file, Config.class);
    }
}


