package ru.unn.agile.RB.ViewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

        public String toString() {
            return name;
        }


    }

    public void actionInsert() {
        try {
            tree.insert(Integer.parseInt(key.get()), value.get());
            status = Status.SUCCESS;
        } catch (NumberFormatException e) {
            status = Status.BAD_KEY_FORMAT;
        }
    }

    public void actionFind() {
        try {
        RBNode<Integer, String> found = tree.find(Integer.parseInt(key.get()));

        if (found != null) {
            status = Status.SUCCESS;
        } else {
            status = Status.NOT_FOUND;
        }

        } catch (NumberFormatException e) {
            status = Status.BAD_KEY_FORMAT;
        }
    }

    public StringProperty keyProperty() {
        return key;
    }

    public StringProperty valueProperty() {
        return value;
    }

    public Status status() {
        return status;
    }

    private final StringProperty value = new SimpleStringProperty("");
    private final StringProperty key   = new SimpleStringProperty("");
    private Status status = Status.WAITING_FOR_INPUT;

    private final RBTree<Integer, String> tree = new RBTree<>();
}
