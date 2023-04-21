package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.model.Action;
import com.dumpBot.processor.msgProcessor.StartRegistrationProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsgProcessFactory {
    private static MsgProcess startRegistrationProcess;
    private static MsgProcess mainMenuProcess;
    private static MsgProcess defaultProcess;
    private static MsgProcess searchProcess;
    private static MsgProcess readyProcess;

    @Autowired
    public MsgProcessFactory(StartRegistrationProcess srp, MainMenuProcess mmp, DefaultProcess df,
                             SearchProcess sp, ReadyProcess rp) {
        MsgProcessFactory.startRegistrationProcess = srp;
        MsgProcessFactory.mainMenuProcess = mmp;
        MsgProcessFactory.defaultProcess = df;
        MsgProcessFactory.searchProcess = sp;
        MsgProcessFactory.readyProcess = rp;
    }

    public static MsgProcess getProcess(Command command) {
        switch (command) {
            case START -> {
                return startRegistrationProcess;
            }
            case READY -> {
                return readyProcess;
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
                return searchProcess;
            }
            default -> {
                return defaultProcess;
            }
        }
    }
}
