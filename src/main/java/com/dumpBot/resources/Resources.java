package com.dumpBot.resources;

import com.dumpBot.config.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Resources {
    private ButtonsText buttonsText;
    private Msgs msgs;
    private Errors errors;
    private Success success;

    public Resources() {
    }

    public ButtonsText getButtonsText() {
        return buttonsText;
    }

    public void setButtonsText(ButtonsText value) {
        this.buttonsText = value;
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
