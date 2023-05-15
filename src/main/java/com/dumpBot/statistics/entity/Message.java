
package com.dumpBot.statistics.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Message {
    private String userId;
    private String from;
    private String text;
}
