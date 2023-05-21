package com.dumpBot.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Statistics {
    @Value("${resources.msgs.admin.statistics.registered}")
    private String registered;
    @Value("${resources.msgs.admin.statistics.cars}")
    private String cars;
    @Value("${resources.msgs.admin.statistics.inQuantity}")
    private String inQuantity;
    @Value("${resources.msgs.admin.statistics.things}")
    private String things;


}
