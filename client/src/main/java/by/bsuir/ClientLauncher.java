package by.bsuir;

import by.bsuir.event.Client;
import by.bsuir.exception.ExceptionHandler;
import by.bsuir.gui.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientLauncher extends Application {
    private static final int FRAME_WIDTH = 1500;
    private static final int FRAME_HEIGHT = 760;

    public static void main(String[] args) {
        Client.launch();
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler::handle);
        primaryStage.setOnCloseRequest(event -> Client.stop());

        MainPane mainPane = new MainPane(primaryStage);
        mainPane.init();
        Scene scene = new Scene(mainPane, FRAME_WIDTH, FRAME_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Faculty");
        primaryStage.show();
    }
}
