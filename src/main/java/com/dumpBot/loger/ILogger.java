package com.dumpBot.loger;

public interface ILogger {
    void writeInfo(String msg, Class cls);
    void writeOk(String msg, Class cls);
    void writeWarning(String msg, Class cls);
    void writeError(String msg, Class cls);
    void writeStackTrace(Exception e);
}
