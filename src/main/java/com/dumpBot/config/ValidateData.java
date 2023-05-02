package com.dumpBot.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class ValidateData {
    @Value("${validateData.channelID}")
    private long channelID;
    @Value("${validateData.channelURL}")
    private String channelURL;
}
