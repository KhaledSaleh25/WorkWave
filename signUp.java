package org.example.freelancev1;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


class signUpPage {
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/mysql";
    static final String JDBC_USER = "root";
    static final String JDBC_PASSWORD = "1234";


    public static boolean insertDataCustomer(String firstName, String lastName, String password, String email) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO workwave.user(useremail,userfname,userlname,userpassword) VALUES (?,?,?,?)")) {
                statement.setString(1, email);
                statement.setString(2, firstName);
                statement.setString(3, lastName);
                statement.setString(4, password);


                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    private static boolean insertDataFreelancer(String email, String firstName, String lastName, String password) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            System.out.println("Connected to the database."); // Print message for debugging
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO workwave.freelancer (freelanceremail, freelancerfname, freelancerlname, freelancerpassword) VALUES (?, ?, ?, ?)")) {
                stmt.setString(1, email);
                stmt.setString(2, firstName);
                stmt.setString(3, lastName);
                stmt.setString(4, password);
                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " rows affected."); // Print number of rows affected for debugging
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException: " + e.getMessage()); // Print specific SQL error message
            return false;
        }
    }



    public static Scene createSignUpScene() {

        Image image = new Image("D:\\New folder\\Javafx\\test\\src\\main\\resources\\imgs\\arrow.png");
        ImageView imageView = new ImageView(image);
        Button backButton = new Button();
        backButton.setBackground(new Background(new BackgroundFill(Color.rgb(50, 16, 75), null, null)));
        backButton.setGraphic(imageView);
        backButton.setAlignment(Pos.CENTER_LEFT);
        backButton.setOnAction((e) -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(welcomePage.createWelcomePage());
        });

        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(166, 1, 189));
        glow.setWidth(30);
        glow.setHeight(30);
        glow.setSpread(0.5);


        Label welcomeText = new Label("Sign Up");
        welcomeText.setTextFill(Color.WHITE);
        welcomeText.setFont(new Font(35));
        welcomeText.setAlignment(Pos.CENTER);
        welcomeText.setEffect(glow);


        // Create a container for the back button and welcome text
        HBox topBar = new HBox(backButton, welcomeText);
        topBar.setAlignment(Pos.CENTER_LEFT); // Align elements to the left
        topBar.setSpacing(50); // Add spacing between elements

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First name");
        firstNameField.setMaxSize(300, 35);
        firstNameField.setStyle("-fx-background-color:rgb(231, 221, 255);-fx-background-radius: 30;");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last name");
        lastNameField.setMaxSize(300, 35);
        lastNameField.setStyle("-fx-background-color:rgb(231, 221, 255);-fx-background-radius: 30;");

        TextField emailUserField = new TextField();
        emailUserField.setPromptText("Email");
        emailUserField.setMaxSize(300, 35);
        emailUserField.setStyle("-fx-background-color:rgb(231, 221, 255);-fx-background-radius: 30;");


        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxSize(300, 35);
        passwordField.setStyle("-fx-background-color: rgb(231, 221, 255);-fx-background-radius: 30;");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm password");
        confirmPasswordField.setMaxSize(300, 35);
        confirmPasswordField.setStyle("-fx-background-color: rgb(231, 221, 255);-fx-background-radius: 30;");

        Button signUpButton = new Button("Sign up");
        signUpButton.setTextFill(Color.WHITE);
        signUpButton.setStyle("-fx-background-color: rgb(166, 1, 189);-fx-text-fill: white; -fx-background-radius: 30;");
        signUpButton.setPrefWidth(100);
        signUpButton.setFont(new Font(18));
        signUpButton.setEffect(glow);


        Button freeLancer = new Button("Free Lancer");
        Button customer = new Button("Customer");
        freeLancer.setTextFill(Color.WHITE);
        freeLancer.setPrefSize(140, 25);
        freeLancer.setStyle("-fx-background-color: transparent; -fx-border-radius: 30px; -fx-border-color: rgb(166, 1, 189);");
        freeLancer.setTextFill(Color.WHITE);
        freeLancer.setFont(new Font("System Bold", 12));
        freeLancer.setEffect(glow);


        customer.setTextFill(Color.WHITE);
        customer.setPrefSize(140, 25);
        customer.setStyle("-fx-background-color: transparent; -fx-border-radius: 30px; -fx-border-color: rgb(166, 1, 189);");
        customer.setTextFill(Color.WHITE);
        customer.setFont(new Font("System Bold", 12));
        customer.setEffect(glow);


        HBox hBox = new HBox(customer, freeLancer);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(30);


        VBox signUpPage = new VBox(20);
        signUpPage.setPadding(new Insets(20));
        signUpPage.setBackground(new Background(new BackgroundFill(Color.rgb(50, 16, 75), null, null)));

        Label warning = new Label("Password is incorrect");
        warning.setTextFill(Color.RED);
        signUpPage.getChildren().addAll(topBar, firstNameField, lastNameField, emailUserField, passwordField, confirmPasswordField);


        // Define flags to track which type of user is signing up
        final boolean[] isCustomerSelected = {false};
        final boolean[] isFreelancerSelected = {false};


        // Event handler for customer button
        customer.setOnAction(event -> {

            customer.setStyle("-fx-background-color: rgb(166, 1, 189); -fx-border-color: rgb(166, 1, 189); -fx-border-radius: 30px;");
            freeLancer.setStyle("-fx-background-color: transparent; -fx-border-color: rgb(166, 1, 189); -fx-border-radius: 30px;");
            isCustomerSelected[0] = true;
            isFreelancerSelected[0] = false;
        });

        // Event handler for freelancer button
        freeLancer.setOnAction(event -> {
            freeLancer.setStyle("-fx-background-color: rgb(166, 1, 189); -fx-border-color: rgb(166, 1, 189); -fx-border-radius: 30px;");
            customer.setStyle("-fx-background-color: transparent; -fx-border-color: rgb(166, 1, 189); -fx-border-radius: 30px;");
            isFreelancerSelected[0] = true;
            isCustomerSelected[0] = false;
        });

        // Sign-up button event handler
        signUpButton.setOnAction(event -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailUserField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if(!firstName.isEmpty()&&!lastName.isEmpty()&&!email.isEmpty()&&!password.isEmpty()&&!confirmPassword.isEmpty())
            {
                if (password.equals(confirmPassword)) {
                boolean success = false;
                if (isCustomerSelected[0]) {
                    // If customer is selected, insert data for customer
                    {
                        success = insertDataCustomer(firstName, lastName, password, email);
                    }
                } else if (isFreelancerSelected[0]) {
                    // If freelancer is selected, insert data for freelancer
                    success = insertDataFreelancer(email, firstName, lastName, password);
                }

                // Check if insertion was successful
                if (success) {
                    if (isFreelancerSelected[0]) {
                        // If freelancer sign-up was successful, navigate to freelancer data page
                        Stage stage = (Stage) signUpButton.getScene().getWindow();
                        stage.setScene(freelancerData.createFreelancerData(email));
                        System.out.println("Freelancer registration successful.");
                    } else if (isCustomerSelected[0]) {
                        // If customer sign-up was successful, navigate to customer profile page
                        Stage stage = (Stage) signUpButton.getScene().getWindow();
                        stage.setScene(profileImage.createProfileImagePage(firstName,lastName,email,password));
                        System.out.println("Customer registration successful.");
                    }
                } else {
                    // If insertion failed, display error message
                    System.out.println("Registration failed. Please try again.");
                }
                }
                else System.out.println("Passwords do not match. Please try again.");
            }
            else{
                System.out.println("Cannot leave any field empty");
            }


        });

        signUpPage.getChildren().addAll(hBox, signUpButton);

        signUpPage.setAlignment(Pos.CENTER);

        return new Scene(signUpPage, 500, 500);
    }
}
