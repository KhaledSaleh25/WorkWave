package org.example.freelancev1;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.InputStream;
import java.sql.*;
public class profilePageCustomer {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "1234";
    private static int id=0;
    private static Connection connection;
    public static Scene createProfilePageCustomer(String email) {
        establishConnection();

        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(166, 1, 189));
        glow.setWidth(30);
        glow.setHeight(30);
        glow.setSpread(0.5);

        Font font=Font.font("Arial", FontWeight.BOLD,18);
        VBox profileBox=new VBox();


        ImageView profileImageView = new ImageView();
        profileImageView.setFitHeight(100);
        profileImageView.setFitWidth(100);
        profileImageView.setClip(new Circle(50, 50, 50));
        // Fetch user details including the profile image
        String userName = fetchDataFromDatabase(email, profileImageView);

        Label profileName = new Label(userName);
        profileName.setFont(font);
        profileName.setTextFill(Color.WHITE);
        profileName.setPadding(new Insets(20,0,0,10));

        Label profileId=new Label("ID:"+ id);
        profileId.setTextFill(Color.WHITE);
        profileId.setFont(font);
        profileId.setPadding(new Insets(5,0,0,10));



        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: rgb(50, 16, 75);");

        AnchorPane sidePane = new AnchorPane();
        sidePane.setPrefSize(200, 400);
        sidePane.setStyle("-fx-background-color: black;");
        sidePane.setVisible(false);

        VBox menuList = new VBox();
        menuList.setPrefSize(200, 400);
        menuList.setSpacing(10);
        menuList.setPadding(new Insets(10));

        Button arrowButton = new Button();
        arrowButton.setStyle("-fx-background-color: transparent;");
        arrowButton.setPrefSize(50, 50);

        Image arrowImage = new Image("D:\\New folder\\Javafx\\test\\src\\main\\resources\\imgs\\arrow.png");
        ImageView arrowImageView = new ImageView(arrowImage);
        arrowButton.setGraphic(arrowImageView);
        arrowImageView.setFitWidth(30);
        arrowImageView.setFitHeight(30);
        menuList.getChildren().add(arrowButton);
        arrowButton.setEffect(glow);

        Button profileButton = createButton("Profile", "D:\\New folder\\Javafx\\test\\src\\main\\resources\\imgs\\user.png", glow);
        Button searchButton = createButton("Search", "D:\\New folder\\Javafx\\test\\src\\main\\resources\\imgs\\search.png", glow);
        Button messagesButton = createButton("Messages", "D:\\New folder\\Javafx\\test\\src\\main\\resources\\imgs\\envelope.png", glow);
        Button aboutButton = createButton("About us", "D:\\New folder\\Javafx\\test\\src\\main\\resources\\imgs\\information (1).png", glow);
        Button menuButton=createButton("","D:\\New folder\\Javafx\\test\\src\\main\\resources\\imgs\\menu-burger (2).png",glow);


        menuButton.setOnAction(e-> sidePane.setVisible(true));
        arrowButton.setOnAction(e1 -> {
            sidePane.setVisible(false);
        });

        profileButton.setOnAction(e-> sidePane.setVisible(false));

        searchButton.setOnAction(e->{
            Stage stage=(Stage)searchButton.getScene().getWindow();
            stage.setScene(searchPage.createSearchPage());
        });


        menuList.getChildren().addAll(profileButton, searchButton, messagesButton, aboutButton);

        sidePane.getChildren().add(menuList);
        AnchorPane.setTopAnchor(menuList, 0.0);
        AnchorPane.setLeftAnchor(menuList, 0.0);
        AnchorPane.setRightAnchor(menuList, 0.0);

        HBox profileField=new HBox();
        VBox userData=new VBox();
        userData.getChildren().addAll(profileName,profileId);
        HBox user=new HBox();
        user.getChildren().addAll(profileImageView,userData);
        profileBox.getChildren().addAll(menuButton,user);
        profileField.getChildren().add(profileBox);
        root.getChildren().addAll(profileField, sidePane);
        root.setPrefHeight(500);
        root.setPrefWidth(400);
        return new Scene(root,500,400);
    }
    private static Button createButton(String text, String imageUrl, DropShadow glow) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: transparent;");
        button.setTextFill(Color.WHITE);
        Image image = new Image(imageUrl);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        button.setGraphic(imageView);
        button.setEffect(glow);
        return button;
    }
    private static String fetchDataFromDatabase(String email, ImageView profileImageView) {
        try {
            // Prepare SQL statement with a parameterized query
            String query = "SELECT iduser, userfname, userlname, profileuserimage FROM workwave.user WHERE useremail=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Fetch the data from the ResultSet
                id = resultSet.getInt("iduser");
                String firstName = resultSet.getString("userfname");
                String lastName = resultSet.getString("userlname");

                // Retrieve the profile image data
                InputStream inputStream = resultSet.getBinaryStream("profileuserimage");
                if (inputStream != null) {
                    // Convert the image data to an Image object
                    Image profileImage = new Image(inputStream);

                    // Set the profile image to the ImageView
                    profileImageView.setImage(profileImage);
                }

                // Concatenate the first and last names
                String fullName = firstName + " " + lastName;

                // Close the ResultSet and the prepared statement
                resultSet.close();
                preparedStatement.close();

                // Return the full name
                return fullName;
            } else {
                // Close the ResultSet and the prepared statement
                resultSet.close();
                preparedStatement.close();

                // If no matching record found, return null
                return null;
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
            return null;
        }
    }


    private static void establishConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
