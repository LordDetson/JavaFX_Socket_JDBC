package by.bsuir.gui;

import by.bsuir.util.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public abstract class OkCancelDialogPane extends DialogPane implements Initializable {
    private Button okButton;
    private Button cancelButton;

    @Override
    public void init() {
        initComponents();
        initActions();
        placeComponents();
    }

    protected void initComponents() {
        getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        okButton = (Button) lookupButton(ButtonType.OK);
        okButton.disableProperty().set(true);
        cancelButton = (Button) lookupButton(ButtonType.CANCEL);
    }

    protected abstract void initActions();

    protected abstract void placeComponents();

    public void setOkAction(EventHandler<ActionEvent> action) {
        okButton.setOnAction(action);
    }

    public void setCancelAction(EventHandler<ActionEvent> action) {
        cancelButton.setOnAction(action);
    }

    public void disabledOkButton(boolean disabled) {
        okButton.disableProperty().set(disabled);
    }
}
