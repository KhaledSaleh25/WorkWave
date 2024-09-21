package org.example.freelancev1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static org.example.freelancev1.LogInPage.createLoginScene;

class welcomePage {

    public static Scene createWelcomePage(){
        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(166, 1, 189));
        glow.setWidth(30);
        glow.setHeight(30);
        glow.setSpread(0.5);



        // Create Labels
        Label welcomeText = new Label("Welcome to");
        welcomeText.setTextFill(Color.WHITE);
        welcomeText.setFont(new Font("Calibri", 20));
        welcomeText.setEffect(glow);


        Label appName = new Label("Work Wave");
        appName.setTextFill(Color.WHITE);
        appName.setFont(new Font("Calibri", 35));
        appName.setEffect(glow);

        Label appDesc = new Label("Freelancing App");
        appDesc.setTextFill(Color.rgb(119, 0, 199));
        appDesc.setFont(new Font("Calibri Bold", 18));

        // Create Buttons
        Button newUserButton = new Button("New User");
        newUserButton.setStyle("-fx-background-color:rgb(166, 1, 189);-fx-text-fill: white; -fx-background-radius: 30;");
        newUserButton.setFont(new Font(18));
        newUserButton.setPrefWidth(200);
        newUserButton.prefHeight(50);
        newUserButton.setEffect(glow);
        newUserButton.setOnAction(e->{
            Stage stage=(Stage) newUserButton.getScene().getWindow();
            stage.setScene(signUpPage.createSignUpScene());
        });


        Button existingUserButton = new Button("Already have an account");
        existingUserButton.setStyle("-fx-background-color:rgb(166, 1, 189); -fx-text-fill: white;-fx-background-radius: 30;");
        existingUserButton.setFont(new Font("Calibri", 18));
        existingUserButton.prefHeight(200);
        existingUserButton.prefHeight(50);
        existingUserButton.setEffect(glow);
        existingUserButton.setOnAction(e->{
            Stage stage=(Stage) existingUserButton.getScene().getWindow();
            stage.setScene(createLoginScene());
        });


        // Create VBox
        VBox root = new VBox(20);
        root.setBackground(new Background(new BackgroundFill(Color.rgb(50, 16, 75), null, null)));
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        root.setPrefWidth(400); // Adjust the width as needed
        root.setPrefHeight(500);
        Scene scene=new Scene(root);

        // Add elements to VBox
        root.getChildren().addAll(welcomeText, appName, appDesc, newUserButton, existingUserButton);

        // Set Scene
        return scene;
    }
}
