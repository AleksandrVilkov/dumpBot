package com.dumpBot.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Getter
@NoArgsConstructor
public class Resources {
    @Autowired
    private ButtonsText buttonsText;
    @Autowired
    private Msgs msgs;
    @Autowired
    private Errors errors;
    @Autowired
    private Success success;
    @Value("${resources.rules}")
    private String rules;
}
