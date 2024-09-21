package org.example.freelancev1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.*;

class LogInPage {
   private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "1234";

    static boolean  authenticateData(String email, String password) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM workwave.freelancer WHERE freelanceremail = ? AND freelancerpassword = ?")) {

            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            // If resultSet has any rows, authentication is successful
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Authentication failed due to an error
        }
    }

    public static Scene createLoginScene() {

        Image image = new Image("D:\\New folder\\Javafx\\test\\src\\main\\resources\\imgs\\arrow.png");
        ImageView imageView = new ImageView(image);
        Button backButton = new Button();
        backButton.setBackground(new Background(new BackgroundFill(Color.rgb(50, 16, 75),null,null)));
        backButton.setGraphic(imageView);
        backButton.setAlignment(Pos.CENTER_LEFT);
        backButton.setOnAction((e) -> {
            Stage stage = (Stage)backButton.getScene().getWindow();
            stage.setScene(welcomePage.createWelcomePage());
        });


        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(166, 1, 189));
        glow.setWidth(30);
        glow.setHeight(30);
        glow.setSpread(0.5);



        Label welcomeText = new Label("Log in");
        welcomeText.setTextFill(Color.WHITE);
        welcomeText.setFont(new Font(35));
        welcomeText.setAlignment(Pos.CENTER);
        welcomeText.setEffect(glow);


        HBox topBar = new HBox(backButton, welcomeText);
        topBar.setAlignment(Pos.CENTER_LEFT); // Align elements to the left
        topBar.setSpacing(50); // Add spacing between elements

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxSize(300, 35);
        emailField.setStyle("-fx-background-color: rgb(231, 221, 255);-fx-background-radius: 30;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxSize(300, 35);
        passwordField.setStyle("-fx-background-color: rgb(231, 221, 255);-fx-background-radius: 30;");

        Button loginButton = new Button("Log in");
        loginButton.setTextFill(Color.WHITE);
        loginButton.setStyle("-fx-background-color: rgb(166, 1, 189);-fx-text-fill: white; -fx-background-radius: 30;");
        loginButton.setPrefWidth(100);
        loginButton.setFont(new Font(18));
        loginButton.setEffect(glow);



        VBox loginPage = new VBox(20);
        loginPage.setPadding(new Insets(20));
        loginPage.setBackground(new Background(new BackgroundFill(Color.rgb(50, 16, 75), null, null)));
        loginPage.getChildren().addAll(topBar, emailField, passwordField, loginButton);
        loginPage.setAlignment(Pos.CENTER);

        loginButton.setOnAction(e-> {
            String email = emailField.getText();
            String password = passwordField.getText();
            Label errorLabel ;
            if (!email.isEmpty() && !password.isEmpty())
            {
                boolean isValid = authenticateData(email, password);
                if (isValid) {
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(profilePageFreelancer.createProfilePageFreelancer(email));
                } else {
                    // Clear previous error message, if any
                    loginPage.getChildren().removeIf(node -> node instanceof Label);
                    // Add new error message
                    errorLabel=new Label("Invalid email or password.");
                    errorLabel.setTextFill(Color.RED);
                    loginPage.getChildren().add(errorLabel);
                }
            }
            else
            {
                errorLabel=new Label("Cannot leave any field empty");
                errorLabel.setTextFill(Color.RED);
                loginPage.getChildren().addAll(errorLabel);
            }
        });


        loginPage.setPrefHeight(500);
        loginPage.setPrefWidth(400);
        return new Scene(loginPage);
    }
}
