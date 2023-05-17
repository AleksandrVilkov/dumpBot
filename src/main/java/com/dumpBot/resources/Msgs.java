package com.dumpBot.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class Msgs {
    @Value("${resources.msgs.welcomeMessage}")
    private String welcomeMessage;
    @Value("${resources.msgs.welcome}")
    private String welcome;
    @Autowired
    private Registration registration;
    @Autowired
    private Photo photo;
    @Autowired
    private Search search;

}
