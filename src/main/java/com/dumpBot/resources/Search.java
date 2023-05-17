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
public class Search {
    @Value("${resources.msgs.search.title}")
    private String title;
    @Value("${resources.msgs.search.botReady}")
    private String botReady;
    @Value("${resources.msgs.search.successSendSearchQuery}")
    private String successSendSearchQuery;
}
