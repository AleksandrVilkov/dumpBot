package com.dumpBot.loger;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@Component
public class Logger implements ILogger {

    private final String PATH;

    public Logger() {
        final String DIR = "temp";
        final String FILE = "botServerLog.log";
        File dir = new File(DIR);
        boolean createdDir = dir.mkdir();
        File myFile = new File(DIR + "/" + FILE);
        try {
            boolean createdFile = myFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PATH = DIR + "/" + FILE;
    }

    @Override
    public void writeInfo(String msg) {
        write(new Date() + ": INFO: " + msg);
        System.out.println(msg);
    }

    @Override
    public void writeWarning(String msg) {
        write(new Date() + ": WARNING: " + msg);
    }

    @Override
    public void writeError(String msg) {
        write(new Date() + ": ERROR!: " + msg);
    }

    public void writeStackTrace(Exception e) {
        writeError(e.getMessage());
        StackTraceElement[] stackTraceElement = e.getStackTrace();
        for (StackTraceElement traceElement : stackTraceElement) {
            write(traceElement.toString());
        }
    }

    public void write(String test) {
        try (FileWriter writer = new FileWriter(PATH, true)) {
            writer.write(test +"\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
