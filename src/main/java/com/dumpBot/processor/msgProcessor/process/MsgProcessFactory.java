package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.model.Action;
import com.dumpBot.processor.msgProcessor.StartRegistrationProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class MsgProcessFactory {
    private static MsgProcess startRegistrationProcess;
    private static MsgProcess mainMenuProcess;
    private static MsgProcess defaultProcess;

    @Autowired
    public MsgProcessFactory(StartRegistrationProcess srp, MainMenuProcess mmp, DefaultProcess df) {
        MsgProcessFactory.startRegistrationProcess = srp;
        MsgProcessFactory.mainMenuProcess = mmp;
        MsgProcessFactory.defaultProcess = df;
    }

    public static MsgProcess getProcess(Command command) {
        switch (command) {
            case START -> {
                return startRegistrationProcess;
            }
            case MAIN_MENU -> {
                return mainMenuProcess;
            }
            default -> {
                return defaultProcess;
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
