package org.example.freelancev1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene welcomeAppPage=welcomePage.createWelcomePage();
        primaryStage.setScene(welcomeAppPage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
