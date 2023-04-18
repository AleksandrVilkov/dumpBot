package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.model.Action;

public class MsgProcessFactory {
    public static MsgProcess getProcess(Command command) {
        switch (command) {
            case START -> {
                return new StartRegistrationProcess();
            }
            case MAIN_MENU -> {
                return new MainMenuProcess();
            }
            default -> {
                return new DefaultProcess();
            }
        }
    }

    public static MsgProcess getProcess(Action action) {
        switch (action) {
            case SEARCH_REQUEST_ACTION -> {
                return new SearchProcess();
            }
            default -> {
                return new DefaultProcess();
            }
        }
    }
}
