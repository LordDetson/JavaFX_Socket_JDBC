package by.bsuir.exception;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptionHandler {

    private static final Logger log = LogManager.getLogger(ExceptionHandler.class);

    private ExceptionHandler() { }

    public static void handle(Throwable e) {
        handle(Thread.currentThread(), e);
    }

    public static void handle(Thread t, Throwable e) {
        StringBuilder exMsgBuilder = new StringBuilder(e.toString());
        for (StackTraceElement element : e.getStackTrace()) {
            exMsgBuilder.append("\n\t").append(element.toString());
        }
        log.error(exMsgBuilder.toString());
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e, exMsgBuilder.toString());
        } else {
            log.error("An unexpected error occurred in " + t);
        }
    }

    private static void showErrorDialog(Throwable e, String msgDetails) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText(e.toString());
        alert.setContentText("The exception stacktrace was:");

        TextArea textArea = new TextArea(msgDetails);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
