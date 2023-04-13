package com.dumpBot.msgProcessor;

public enum Command {
    START("/start");
    final String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
