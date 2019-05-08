package ru.unn.agile.RB.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    @Override
    public void log(final String msg) {
        log.add(msg);
    }

    @Override
    public List<String> getLog() {
        return log;
    }

    private final List<String> log = new ArrayList<>();
}
