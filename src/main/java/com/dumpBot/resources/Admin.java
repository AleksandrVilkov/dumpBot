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
public class Admin {
    @Value("${resources.msgs.admin.newAccommodation}")
    private String newAccommodation;
    @Value("${resources.msgs.admin.successApproved}")
    private String successApproved;
    @Value("${resources.msgs.admin.upd}")
    private String upd;
    @Value("${resources.msgs.admin.fromUser}")
    private String fromUser;
    @Value("${resources.msgs.admin.successApprovedForUser}")
    private String successApprovedForUser;
    @Value("${resources.msgs.admin.successApprovedForUser2}")
    private String successApprovedForUser2;
    @Value("${resources.msgs.admin.welcome}")
    private String welcome;
    @Value("${resources.msgs.admin.choice}")
    private String choice;
    @Value("${resources.msgs.admin.remember}")
    private String remember;
    @Value("${resources.msgs.admin.totalRequests}")
    private String totalRequests;
    @Autowired
    private Statistics statistics;


}
