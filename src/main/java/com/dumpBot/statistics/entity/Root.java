
package com.dumpBot.statistics.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Root {
    private List<Message> messages = new ArrayList<>();
    private String name;
    private String type;
}
