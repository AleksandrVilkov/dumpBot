package com.dumpBot.msgProcessor.process;

import com.dumpBot.msgProcessor.Command;

public class ProcessFactory {
    public Process getProcess(Command command) {
        switch (command) {
            case START -> {
                return new StartProcess();
            }
            default -> {
                return new DefaultProcess();
            }
        }
    }
}
