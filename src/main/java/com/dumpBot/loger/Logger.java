package com.dumpBot.loger;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@Component
public class Logger implements ILogger {

    private final String PATH;
    private final String ANSI_RESET;
    private final String ANSI_RED;
    private final String ANSI_GREEN;
    private final String ANSI_BLUE;
    private final String ANSI_YELLOW;

    public Logger() {
        ANSI_RESET = "\u001B[0m";
        ANSI_RED = "\u001B[31m";
        ANSI_GREEN = "\u001B[32m";
        ANSI_BLUE = "\u001B[34m";
        ANSI_YELLOW = "\u001B[33m";
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
    public void writeInfo(String msg, Class cls) {
        String txtColor = new Date() + "[" + cls + "]" + ANSI_BLUE + ": [INFO] " + ANSI_RESET + msg;
        String txt = new Date() + "[" + cls + "]: [INFO] " + msg;
        write(txt);
        System.out.println(txtColor);
    }

    @Override
    public void writeOk(String msg, Class cls) {
        String txtColor = new Date() + "[" + cls + "]" + ANSI_GREEN + ": [OK] " + ANSI_RESET + msg;
        String txt = new Date() + "[" + cls + "]: [OK] " + msg;
        write(txt);
        System.out.println(txtColor);
    }

    @Override
    public void writeWarning(String msg, Class cls) {
        String txtColor = new Date() + "[" + cls + "]" + ANSI_YELLOW + ": [WARNING] " + ANSI_RESET + msg;
        String txt = new Date() + "[" + cls + "]: [WARNING] " + msg;
        write(txt);
        System.out.println(txtColor);
    }

    @Override
    public void writeError(String msg, Class cls) {
        String txtColor = new Date() + "[" + cls + "]" + ANSI_YELLOW + ": [ERROR] " + ANSI_RESET + msg;
        String txt = new Date() + "[" + cls + "]: [ERROR] " + msg;
        write(txt);
        System.out.println(txtColor);
    }

    public void writeStackTrace(Exception e) {
        System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
        write(e.getMessage());
        StackTraceElement[] stackTraceElement = e.getStackTrace();
        for (StackTraceElement traceElement : stackTraceElement) {
            write(traceElement.toString());
            System.out.println(ANSI_RED + traceElement.toString() + ANSI_RESET);
        }
    }

    public void write(String test) {
        try (FileWriter writer = new FileWriter(PATH, true)) {
            writer.write(test + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
