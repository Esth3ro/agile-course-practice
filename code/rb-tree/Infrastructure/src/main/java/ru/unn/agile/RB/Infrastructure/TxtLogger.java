package ru.unn.agile.RB.Infrastructure;

import ru.unn.agile.RB.ViewModel.ILogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TxtLogger implements ILogger {
    public TxtLogger(final String file) {
        this.file = file;

        BufferedWriter logBufferWriter = null;

        try {
            logBufferWriter = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedWriter = logBufferWriter;
    }

    @Override
    public List<String> getLog() {
        FileReader file;
        BufferedReader bufferedReader;
        ArrayList<String> log = new ArrayList<>();
        try {

            file = new FileReader(this.file);
            bufferedReader = new BufferedReader(file);
            String line = bufferedReader.readLine();

            while (null != line) {

                log.add(line);
                    line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return log;
    }

    @Override
    public void log(final String str) {
        try {
            bufferedWriter.write(getCurrentTime() + " --> " + str);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String getCurrentTime() {
        return new SimpleDateFormat(DFORMAT).format(new Date());
    }

    private static final String DFORMAT = "yyyy-MM-dd HH:mm:ss";
    private final String file;
    private final BufferedWriter bufferedWriter;
}
