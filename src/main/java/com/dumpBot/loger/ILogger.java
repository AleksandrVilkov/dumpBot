package com.dumpBot.loger;

public interface ILogger {
    void writeInfo(String msg);

    void writeWarning(String msg);

    void writeError(String msg);
}
