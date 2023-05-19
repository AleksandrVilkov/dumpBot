package com.dumpBot.processor.msgProcessor.process;

import com.dumpBot.model.enums.Action;
import com.dumpBot.processor.msgProcessor.admin.AdminProcess;
import com.dumpBot.processor.msgProcessor.process.ready.MainReadyProcess;
import com.dumpBot.processor.msgProcessor.process.registration.RegistrationProcess;
import com.dumpBot.processor.msgProcessor.process.search.SearchMsgProcess;
import com.dumpBot.processor.msgProcessor.process.start.StartProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsgProcessFactory {
    private static MsgProcess registration;
    private static MsgProcess start;
    private static MsgProcess defaultProcess;

    private static MsgProcess searchMgsProcess;

    private static MsgProcess readyProcess;
    private static MsgProcess adminProcess;

    @Autowired
    public MsgProcessFactory(RegistrationProcess srp, StartProcess mmp, MainReadyProcess rp, SearchMsgProcess smp, AdminProcess ap) {
        MsgProcessFactory.registration = srp;
        MsgProcessFactory.start = mmp;
        MsgProcessFactory.readyProcess = rp;
        MsgProcessFactory.searchMgsProcess = smp;
        MsgProcessFactory.adminProcess = ap;
    }

    public static MsgProcess getProcess(Command command) {
        switch (command) {
            case START -> {
                return start;
            }
            case READY -> {
                return readyProcess;
            }
            case ADMIN ->
            {
                return adminProcess;
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
            case SEARCH -> {
                return searchMgsProcess;
            }
            default -> {
                return defaultProcess;
            }
        }
    }
}
