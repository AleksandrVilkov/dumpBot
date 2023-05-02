package com.dumpBot.resources;

import jdk.jfr.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Success {
    @Value("${resources.success.successReservation}")
    private String successReservation;
    @Value("${resources.success.excellent}")
    private String excellent;
    @Value("${resources.success.wonderful}")
    private String wonderful;
    @Value("${resources.success.possibilities}")
    private String possibilities;
    @Value("${resources.success.menuAccess}")
    private String menuAccess;
}
