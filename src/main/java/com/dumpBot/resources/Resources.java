package com.dumpBot.resources;
// Welcome3.java

import com.dumpBot.config.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;

import java.io.File;

public class Resources {
    private Buttonstext buttonstext;
    private Msgs msgs;
    private Errors errors;
    private Success success;

    public Buttonstext getButtonstext() {
        return buttonstext;
    }

    public void setButtonstext(Buttonstext value) {
        this.buttonstext = value;
    }

    public Msgs getMsgs() {
        return msgs;
    }

    public void setMsgs(Msgs value) {
        this.msgs = value;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors value) {
        this.errors = value;
    }

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success value) {
        this.success = value;
    }

    @SneakyThrows
    public static Resources init() {
        File file = new File("/home/vilkov/IdeaProjects/dumpBot/src/main/resources/resources.yaml");
        // Создание нового ObjectMapper как YAMLFactory
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        return om.readValue(file, Resources.class);
    }

}
