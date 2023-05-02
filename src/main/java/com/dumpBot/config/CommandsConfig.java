package com.dumpBot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CommandsConfig {
    @Value("${commands.start}")
    private String start;
    @Value("${commands.rules}")
    private String rules;
    @Value("${commands.registration}")
    private String registration;
    @Value("${commands.subscription}")
    private String subscription;
    @Value("${commands.sale}")
    private String sale;
}
