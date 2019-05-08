package ru.unn.agile.RB.ViewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import ru.unn.agile.RB.Model.RBNode;
import ru.unn.agile.RB.Model.RBTree;

public class ViewModel {

    enum Status {
        WAITING_FOR_INPUT("Waiting for input..."),
        SUCCESS("Operation completed successfully"),
        BAD_KEY_FORMAT("Key should be an integer"),
        NOT_FOUND("No such key was found in the tree");

        private final String name;

        Status(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    enum LoggerMessages {
        INSERTION_SUCCESSFUL("was inserted successfully"),
        KEY_FOUND("was found successfully."),
        KEY_NOT_FOUND("is not in the tree!"),
        BAD_KEY_FORMAT("cannot be parsed to Integer");

        private final String msg;

        LoggerMessages(final String msg) {
            this.msg  = msg;
        }

        @Override
        public String toString() {
            return this.msg;
        }

        public String toString(final String key) {
            return key + " " + this.msg;
        }

        public String toString(final String key, final String value) {
            return "[" +  key + ":" + value + "] " + this.msg;
        }
    }

    public void actionInsert() {
        try {
            tree.insert(Integer.parseInt(key.get()), value.get());
            status.setValue(Status.SUCCESS);
            this.log(LoggerMessages.INSERTION_SUCCESSFUL.toString(key.get(), value.get()));
        } catch (NumberFormatException e) {
            status.setValue(Status.BAD_KEY_FORMAT);
            this.log(LoggerMessages.BAD_KEY_FORMAT.toString(key.get()));
        }
    }

    public void actionFind() {
        try {
        RBNode<Integer, String> found = tree.find(Integer.parseInt(key.get()));

        if (found != null) {
            valueProperty().setValue(found.getVal());
            status.setValue(Status.SUCCESS);
            this.log(LoggerMessages.KEY_FOUND.toString(key.get(), found.getVal()));
        } else {
            status.setValue(Status.NOT_FOUND);
            this.log(LoggerMessages.KEY_NOT_FOUND.toString(key.get()));
        }

        } catch (NumberFormatException e) {
            status.setValue(Status.BAD_KEY_FORMAT);
            this.log(LoggerMessages.BAD_KEY_FORMAT.toString(key.get()));
        }
    }

    public StringProperty keyProperty() {
        return key;
    }

    public StringProperty valueProperty() {
        return value;
    }

    public ObjectProperty<Status> statusProperty() {
        return status;
    }

    public ILogger getLogger() {
        return logger;
    }

    public void setLogger(final ILogger logger) {
        this.logger = logger;
    }

    public ListProperty<String> outputWindowLoggerProperty() {
        return outputWindowLogger;
    }

    private void log(final String str) {
        if (logger != null) {
            logger.log(str);
            outputWindowLogger.setValue(FXCollections.observableArrayList(logger.getLog()));
        }
    }

    private final StringProperty value = new SimpleStringProperty("");
    private final StringProperty key   = new SimpleStringProperty("");
    private final ObjectProperty<Status> status
            = new SimpleObjectProperty<>(Status.WAITING_FOR_INPUT);
    private final ListProperty<String> outputWindowLogger = new SimpleListProperty<>();
    private final RBTree<Integer, String> tree = new RBTree<>();
    private ILogger logger = null;
}
