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
}
