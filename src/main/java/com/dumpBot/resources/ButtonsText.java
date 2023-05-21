package com.dumpBot.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ButtonsText {
    @Value("${resources.buttonsText.registration}")
    private String registration;
    @Value("${resources.buttonsText.searchRequest}")
    private String searchRequest;
    @Value("${resources.buttonsText.placeAnAd}")
    private String placeAnAd;
    @Value("${resources.buttonsText.rules}")
    private String rules;
    @Value("${resources.buttonsText.withoutPhoto}")
    private String withoutPhoto;
    @Value("${resources.buttonsText.newRequests}")
    private String newRequests;
    @Value("${resources.buttonsText.statistics}")
    private String statistics;
    @Value("${resources.buttonsText.approved}")
    private String approved;
    @Value("${resources.buttonsText.rejected}")
    private String rejected;
}
