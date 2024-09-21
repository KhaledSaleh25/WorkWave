package org.example.freelancev1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class searchPage {

    public static Scene createSearchPage() {
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color:rgb(50, 16, 75)");
        vBox.setSpacing(20);  // Add spacing for better layout

        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(166, 1, 189));
        glow.setWidth(30);
        glow.setHeight(30);
        glow.setSpread(0.5);

        Hyperlink github=new Hyperlink();

        Label welcomeText = new Label("Search");
        welcomeText.setTextFill(Color.WHITE);
        welcomeText.setFont(new Font(35));
        welcomeText.setAlignment(Pos.CENTER);
        welcomeText.setEffect(glow);

        ToggleButton writingButton = createToggleButton("Writing and Content Creation");
        ToggleButton designButton = createToggleButton("Graphic Design and Illustration");
        ToggleButton fullStackButton = createToggleButton("Full-Stack Developer");
        ToggleButton digitalMarketing = createToggleButton("Digital Marketing");
        ToggleButton photographyAndVideography = createToggleButton("Photography and Videography");

        ToggleGroup specialtyToggleGroup = new ToggleGroup();
        writingButton.setToggleGroup(specialtyToggleGroup);
        designButton.setToggleGroup(specialtyToggleGroup);
        fullStackButton.setToggleGroup(specialtyToggleGroup);
        digitalMarketing.setToggleGroup(specialtyToggleGroup);
        photographyAndVideography.setToggleGroup(specialtyToggleGroup);

        Spinner<Integer> yearsSpinner = new Spinner<>(1, 5, 1);
        yearsSpinner.setEditable(true);

        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> {
            String selectedSpecialty = getSelectedSpecialty(specialtyToggleGroup);
            int years = yearsSpinner.getValue();

            // Perform search and filtering based on searchText, selectedSpecialty, and years
            performSearch(selectedSpecialty, years, vBox);
        });

        HBox specialtyButtons = new HBox(10);
        specialtyButtons.getChildren().addAll(
                writingButton, designButton, fullStackButton, digitalMarketing, photographyAndVideography
        );

        vBox.getChildren().addAll(welcomeText, specialtyButtons, yearsSpinner, searchButton);
        vBox.setPrefWidth(400);
        vBox.setPrefHeight(500);
        Scene scene = new Scene(vBox);
        return scene;
    }

    private static String getSelectedSpecialty(ToggleGroup toggleGroup) {
        ToggleButton selectedToggle = (ToggleButton) toggleGroup.getSelectedToggle();
        return selectedToggle != null ? selectedToggle.getText() : "";
    }

    private static void performSearch(String specialty, int years, VBox vBox) {
        try {
            // Connect to your database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "1234");

            // Prepare SQL statement with parameters
            String sql = "SELECT * FROM workwave.freelancer WHERE freelancerspecialty = ? AND freelanceryoexperience <= ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, specialty);
            statement.setInt(2, years);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result set and populate the search results
            ListView<String> resultsListView = new ListView<>();
            while (resultSet.next()) {
                String name = resultSet.getString("freelancername");
                String job = resultSet.getString("freelancerjob");
                int experience = resultSet.getInt("freelanceryoexperience");

                // Add the search result to the ListView
                resultsListView.getItems().add("Name: " + name + ", Job: " + job + ", Years of Experience: " + experience);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();

            // Clear the previous search results, if any
            vBox.getChildren().removeIf(node -> node instanceof ListView);

            // Add the ListView containing the search results to the scene
            vBox.getChildren().add(resultsListView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ToggleButton createToggleButton(String text) {
        ToggleButton button = new ToggleButton(text);
        button.getStyleClass().add("field-button");
        return button;
    }
}
