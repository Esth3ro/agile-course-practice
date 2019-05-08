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

    public void actionInsert() {
        try {
            tree.insert(Integer.parseInt(key.get()), value.get());
            status.setValue(Status.SUCCESS);
        } catch (NumberFormatException e) {
            status.setValue(Status.BAD_KEY_FORMAT);
        }
    }

    public void actionFind() {
        try {
        RBNode<Integer, String> found = tree.find(Integer.parseInt(key.get()));

        if (found != null) {
            valueProperty().setValue(found.getVal());
            status.setValue(Status.SUCCESS);
        } else {
            status.setValue(Status.NOT_FOUND);
        }

        } catch (NumberFormatException e) {
            status.setValue(Status.BAD_KEY_FORMAT);
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
