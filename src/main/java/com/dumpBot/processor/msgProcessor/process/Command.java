package com.dumpBot.processor.msgProcessor.process;

public enum Command {
    START("/start"),
    READY("/ready");
    final String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
