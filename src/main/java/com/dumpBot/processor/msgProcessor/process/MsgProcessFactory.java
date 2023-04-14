package com.dumpBot.processor.msgProcessor.process;

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
}
