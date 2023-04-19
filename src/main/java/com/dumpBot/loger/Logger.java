package com.dumpBot.loger;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class Logger implements ILogger {

    private final Path PATH;

    public Logger() {
        PATH = Path.of("");

    }

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

    public void write(String test) {

        try(FileWriter writer = new FileWriter("notes3.txt", false))
        {
            // запись всей строки
            String text = "Hello Gold!";
            writer.write(text);
            // запись по символам
            writer.append('\n');
            writer.append('E');

            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
