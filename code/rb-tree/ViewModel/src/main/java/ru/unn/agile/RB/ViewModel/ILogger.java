package ru.unn.agile.RB.ViewModel;

import java.util.List;

public interface ILogger {
    void log(String msg);

    List<String> getLog();
}
