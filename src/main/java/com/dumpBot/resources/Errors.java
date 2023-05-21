package com.dumpBot.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Errors {
    @Value("${resources.errors.commonError}")
    private String commonError;
    @Value("${resources.errors.authError}")
    private String authError;
    @Value("${resources.errors.userNameErr}")
    private String userNameErr;
    @Value("${resources.errors.notAdminErr}")
    private String notAdminErr;
}
