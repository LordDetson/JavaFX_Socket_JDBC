package by.bsuir.gui;

import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class DialogStage extends Stage {

    public DialogStage(Window owner, DialogPane dialogPane, String title) {
        setTitle(title);
        initOwner(owner);
        initModality(Modality.WINDOW_MODAL);
        initStyle(StageStyle.UTILITY);
        setResizable(false);
        Scene scene = new Scene(dialogPane);
        setScene(scene);
    }
}
