package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.model.enums.Action;
import com.dumpBot.processor.msgProcessor.process.ready.MainReadyProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsgProcessFactory {
    private static MsgProcess registration;
    private static MsgProcess start;
    private static MsgProcess defaultProcess;

    private static MsgProcess readyProcess;

    @Autowired
    public MsgProcessFactory(RegistrationProcess srp, StartProcess mmp, MainReadyProcess rp) {
        MsgProcessFactory.registration = srp;
        MsgProcessFactory.start = mmp;
        MsgProcessFactory.readyProcess = rp;
    }

    public static MsgProcess getProcess(Command command) {
        switch (command) {
            case START -> {
                return start;
            }
            case READY -> {
                return readyProcess;
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
