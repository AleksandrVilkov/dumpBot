package com.dumpBot.msgProcessor.process;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Process {
    SendMessage execute();
}
