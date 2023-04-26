package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.model.enums.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsgProcessFactory {
    private static MsgProcess registration;
    private static MsgProcess start;
    private static MsgProcess defaultProcess;

    @Autowired
    public MsgProcessFactory(RegistrationProcess srp, StartProcess mmp) {
        MsgProcessFactory.registration = srp;
        MsgProcessFactory.start = mmp;
    }

    public static MsgProcess getProcess(Command command) {
        switch (command) {
            case START -> {
                return start;
            }
            default -> {
                return defaultProcess;
            }
        }
    }

    public static MsgProcess getProcess(Action action) {
        switch (action) {

            case REGISTRATION -> {
                return registration;
            }
            case START -> {
                return start;
            }
            default -> {
                return defaultProcess;
            }
        }
    }
}
