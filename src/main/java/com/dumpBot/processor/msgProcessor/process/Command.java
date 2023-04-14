package com.dumpBot.processor.msgProcessor.process;

public enum Command {
    START("/start"),
    MAIN_MENU("/main_menu");
    final String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
