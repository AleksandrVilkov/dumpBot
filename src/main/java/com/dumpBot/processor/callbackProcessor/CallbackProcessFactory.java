package com.dumpBot.processor.callbackProcessor;

import com.dumpBot.processor.callbackProcessor.process.ApprovedProcess;
import com.dumpBot.processor.callbackProcessor.process.RejectedProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CallbackProcessFactory {
    private static CallbackProcess a;
    private static CallbackProcess r;

    @Autowired
    public CallbackProcessFactory(ApprovedProcess a, RejectedProcess r) {
        CallbackProcessFactory.a = a;
        CallbackProcessFactory.r = r;
    }

    public static CallbackProcess getProcess(String s) {
        //TODO передедать на энамы
        switch (s) {
            case "approved" -> {
                return a;
            }
            case "SEARCH" -> {
                return r;
            }
            default -> {
                return null;
            }
        }
    }
}
