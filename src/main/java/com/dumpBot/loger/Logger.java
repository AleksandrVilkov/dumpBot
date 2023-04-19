package com.dumpBot.loger;

import org.springframework.stereotype.Component;

@Component
public class Logger implements ILogger {
    @Override
    public void writeInfo(String msg) {
        System.out.println(msg);
    }

    @Override
    public void writeWarning(String msg) {
        System.out.println(msg);
    }

    @Override
    public void writeError(String msg) {
        System.out.println(msg);
    }
}
