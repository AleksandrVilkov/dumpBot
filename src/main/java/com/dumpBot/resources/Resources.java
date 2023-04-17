package com.dumpBot.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Getter
@NoArgsConstructor
public class Resources {
    private ButtonsText buttonsText;
    private Msgs msgs;
    private Errors errors;
    private Success success;

    @SneakyThrows
    public static Resources init() {
        File file = new File("/home/vilkov/IdeaProjects/dumpBot/src/main/resources/resources.yaml");
        // Создание нового ObjectMapper как YAMLFactory
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        return om.readValue(file, Resources.class);
    }

}
