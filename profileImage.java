package org.example.freelancev1;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;

public class profileImage {

    private static Connection connection;
    private static ImageView imageView;
    private static Label statusLabel;
    private static File selectedFile;

    public static Scene createProfileImagePage(String firstName,String lastName,String email,String password) {
        // Connect to the database
        connectToDatabase();

        // UI components
        Button chooseImageButton = new Button("Choose Image");
        Button continueButton = new Button("Continue");
        imageView = new ImageView();
        statusLabel = new Label();
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setClip(new Circle(50, 50, 50));

        chooseImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image File");
            selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                try {
                    Image image = new Image(new FileInputStream(selectedFile));
                    imageView.setImage(image);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        chooseImageButton.setStyle("-fx-background-color: rgb(166, 1, 189); -fx-text-fill: white;");
        chooseImageButton.setFont(new Font("System Bold", 12));

        continueButton.setOnAction(e -> {
            if (selectedFile != null) {
                uploadImageToDatabase(selectedFile,firstName,lastName,email,password);
                Stage stage=(Stage) continueButton.getScene().getWindow();
                stage.setScene(profilePageCustomer.createProfilePageCustomer(email));
                System.out.println("Image uploaded successfully!");
            } else {
                // Navigate to the next page or perform the desired action
                Stage stage=(Stage) continueButton.getScene().getWindow();
                stage.setScene(profilePageCustomer.createProfilePageCustomer(email));
            }
        });
        continueButton.setStyle("-fx-background-color: rgb(166, 1, 189); -fx-text-fill: white;");
        continueButton.setFont(new Font("System Bold", 12));

        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(chooseImageButton, continueButton);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        // Layout
        VBox root = new VBox(20);
        root.setBackground(new Background(new BackgroundFill(Color.rgb(50, 16, 75), null, null)));
        root.getChildren().addAll(imageView, buttonBox, statusLabel);
        root.setPadding(new Insets(20));

        // Scene
        return new Scene(root, 400, 400);
    }

    private static void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "1234");
            System.out.println("Connected to database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void uploadImageToDatabase(File file, String firstName, String lastName, String email, String password) {
        try {
            FileInputStream fis = new FileInputStream(file);
            PreparedStatement ps = connection.prepareStatement("UPDATE workwave.user SET profileuserimage = ? WHERE userfname = ? AND userlname = ? AND useremail = ? AND userpassword = ?");
            ps.setBinaryStream(1, fis, (int) file.length());
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, email);
            ps.setString(5, password);
            ps.executeUpdate();
            ps.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
