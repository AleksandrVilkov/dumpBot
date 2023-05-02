package com.dumpBot.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class Registration {
    @Value("${resources.msgs.registration.hello}")
    private String hello;
    @Value("${resources.msgs.registration.go}")
    private String go;
    @Value("${resources.msgs.registration.description}")
    private String description;
    @Value("${resources.msgs.registration.tapRegistration}")
    private String tapRegistration;
}
