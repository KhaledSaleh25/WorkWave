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
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class freelancerData {
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/mysql";
    static final String JDBC_USER = "root";
    static final String JDBC_PASSWORD = "1234";
    static String specialty="specialty";
    static int yOfExperience=0;
    public static boolean insertDataFreelancer(String specialty, String description, int yearsOfExperience, String email) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            // Update the row if the email exists
            try (PreparedStatement updateStatement = conn.prepareStatement("UPDATE workwave.freelancer SET freelancerspecialty = ?, freelanceryoexperience = ?, freelancerdescription = ? WHERE freelanceremail = ?")) {
                updateStatement.setString(1, specialty);
                updateStatement.setInt(2, yearsOfExperience);
                updateStatement.setString(3, description);
                updateStatement.setString(4, email);
                int rowsAffected = updateStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the exception for debugging purposes
            return false;
        }
    }


    public static Scene createFreelancerData(String email)  {
        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(166, 1, 189));
        glow.setWidth(30);
        glow.setHeight(30);
        glow.setSpread(0.5);


        Image image = new Image("D:\\New folder\\Javafx\\test\\src\\main\\resources\\imgs\\arrow.png");
        ImageView imageView = new ImageView(image);
        Button backButton = new Button();
        backButton.setBackground(new Background(new BackgroundFill(Color.rgb(50, 16, 75),null,null)));
        backButton.setGraphic(imageView);
        backButton.setAlignment(Pos.CENTER_LEFT);
        backButton.setPadding(new Insets(10));
        backButton.setOnAction((e) -> {
            Stage stage = (Stage)backButton.getScene().getWindow();
            stage.setScene(signUpPage.createSignUpScene());
        });


        VBox root = new VBox();
        root.setPrefSize(520, 500);
        root.setSpacing(10);
        root.setStyle("-fx-background-color: rgb(50, 16, 75);");


        Label titleLabel = new Label("Choose your specialty");
        titleLabel.setPrefSize(170, 25);
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(new Font("Calibri Bold", 18));
        VBox.setMargin(titleLabel, new Insets(7, 0, 0, 10));

        HBox buttonsContainer = new HBox();
        buttonsContainer.setPrefSize(500, 175);
        buttonsContainer.setSpacing(30);

        // Lists to store all toggle buttons across containers
        List<ToggleButton> allToggleButtons = new ArrayList<>();

        // First column of buttons
        VBox firstColumn = new VBox();
        firstColumn.setPrefSize(150, 200);
        firstColumn.setSpacing(7);
        addToggleButtons(firstColumn, allToggleButtons, "UI/UX Designer", "Frontend Developer", "Web App Developer", "Mobile App Developer", "Backend Developer");

        // Second column of buttons
        VBox secondColumn = new VBox();
        secondColumn.setPrefSize(150, 200);
        secondColumn.setSpacing(7);
        addToggleButtons(secondColumn, allToggleButtons, "Full-Stack Developer", "Game Developer", "QA Tester", "Project Manager", "Technical Writer");

        // Third column of buttons
        VBox thirdColumn = new VBox();
        thirdColumn.setPrefSize(150, 200);
        thirdColumn.setSpacing(7);
        addToggleButtons(thirdColumn, allToggleButtons, "DevOps Engineer", "Security Specialist", "Data Analyst", "Content Creator", "Localization Specialist");

        buttonsContainer.getChildren().addAll(firstColumn, secondColumn, thirdColumn);
        VBox.setMargin(buttonsContainer, new Insets(0, 0, 0, 10));

        TextField descriptionTextArea = new TextField();
        descriptionTextArea.setPrefSize(400, 135);
        descriptionTextArea.setPromptText("Write your description about yourself");
        descriptionTextArea.setStyle("-fx-background-color: transparent; -fx-border-color: rgb(166, 1, 189);");
        VBox.setMargin(descriptionTextArea, new Insets(30, 0, 0, 0));

        MenuButton experienceMenuButton = new MenuButton("Years of experience");
        experienceMenuButton.setPrefSize(151, 26);
        experienceMenuButton.setStyle("-fx-background-color: transparent; -fx-border-color: rgb(166, 1, 189); -fx-border-radius: 30px;");
        experienceMenuButton.setTextFill(Color.WHITE);
        experienceMenuButton.setFont(Font.font("System Bold", 12));

        MenuItem menuItem1 = new MenuItem("1");
        MenuItem menuItem2 = new MenuItem("2");
        MenuItem menuItem3 = new MenuItem("3");
        MenuItem menuItem4 = new MenuItem("4");
        MenuItem menuItem5 = new MenuItem("5");

        // Apply styles to menu items
        applyMenuItemStyles(menuItem1);
        applyMenuItemStyles(menuItem2);
        applyMenuItemStyles(menuItem3);
        applyMenuItemStyles(menuItem4);
        applyMenuItemStyles(menuItem5);

        experienceMenuButton.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5);

        // Set action for menu items
        menuItem1.setOnAction(e -> {
            experienceMenuButton.setText(menuItem1.getText());
            yOfExperience=1;
        });
        menuItem2.setOnAction(e -> {
            experienceMenuButton.setText(menuItem2.getText());
            yOfExperience=2;
        });
        menuItem3.setOnAction(e -> {
            experienceMenuButton.setText(menuItem3.getText());
            yOfExperience=3;
        });
        menuItem4.setOnAction(e -> {
            experienceMenuButton.setText(menuItem4.getText());
            yOfExperience=4;
        }
        );
        menuItem5.setOnAction(e -> {
            experienceMenuButton.setText(menuItem5.getText());
            yOfExperience=5;
        });

        experienceMenuButton.setEffect(glow);

        experienceMenuButton.setOnMouseEntered(e -> experienceMenuButton.setStyle("-fx-background-color: rgb(166, 1, 189); -fx-border-color: rgb(166, 1, 189); -fx-border-radius: 30px;"));
        experienceMenuButton.setOnMouseExited(e -> experienceMenuButton.setStyle("-fx-background-color: transparent; -fx-border-color: rgb(166, 1, 189); -fx-border-radius: 30px;"));

        Button signUpButton = new Button("Sign up");
        signUpButton.setTextFill(Color.WHITE);
        signUpButton.setStyle("-fx-background-color: rgb(166, 1, 189);-fx-text-fill: white; -fx-background-radius: 30;");
        signUpButton.setPrefWidth(100);
        signUpButton.setFont(new Font(18));
        signUpButton.setEffect(glow);
        signUpButton.setAlignment(Pos.CENTER);

        if(insertDataFreelancer(specialty, descriptionTextArea.getText(), yOfExperience, email)) {
            signUpButton.setOnAction(event -> {
                if (insertDataFreelancer(specialty, descriptionTextArea.getText(), yOfExperience, email)) {
                    Stage stage = (Stage) signUpButton.getScene().getWindow();
                    stage.setScene(profilePageFreelancer.createProfilePageFreelancer(email));
                }
            });
        }



        VBox.setMargin(experienceMenuButton, new Insets(25, 0, 0, 0));

        root.getChildren().addAll(backButton,titleLabel, buttonsContainer, descriptionTextArea, experienceMenuButton,signUpButton);

        return new Scene(root,500,600);
    }

    private static void applyMenuItemStyles(MenuItem menuItem) {
        menuItem.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-font-family: 'System'; -fx-font-size: 12;");
    }

    private static void addToggleButtons(VBox container, List<ToggleButton> allToggleButtons, String... buttonTexts) {
        ToggleGroup toggleGroup = new ToggleGroup();

        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(166, 1, 189));
        glow.setWidth(30);
        glow.setHeight(30);
        glow.setSpread(0.5);

        for (String buttonText : buttonTexts) {
            ToggleButton toggleButton = new ToggleButton(buttonText);
            toggleButton.setPrefSize(140, 70);
            toggleButton.setStyle("-fx-background-color: transparent; -fx-border-radius: 30px; -fx-border-color: rgb(166, 1, 189);");
            toggleButton.setTextFill(Color.WHITE);
            toggleButton.setFont(new Font("System Bold", 12));
            toggleButton.setEffect(glow);

            toggleButton.setToggleGroup(toggleGroup);

            toggleButton.setOnAction(e -> {
                if (toggleButton.isSelected()) {
                    // Set the clicked button to active style
                    toggleButton.setStyle("-fx-background-color: rgb(166, 1, 189); -fx-border-radius: 30px; -fx-border-color: rgb(166, 1, 189);");
                    specialty = toggleButton.getText();
                    // Deselect all other buttons in all containers
                    for (ToggleButton btn : allToggleButtons) {
                        if (btn != toggleButton) {
                            btn.setSelected(false);
                            btn.setStyle("-fx-background-color: transparent; -fx-border-radius: 30px; -fx-border-color: rgb(166, 1, 189);");
                        }
                    }
                } else {
                    // Set the clicked button to transparent style when deselected
                    toggleButton.setStyle("-fx-background-color: transparent; -fx-border-radius: 30px; -fx-border-color: rgb(166, 1, 189);");
                }
            });

            toggleButton.setOnMouseEntered(e -> {
                if (!toggleButton.isSelected()) {
                    //specialty = toggleButton.getText();
                    toggleButton.setStyle("-fx-background-color: rgb(166, 1, 189); -fx-border-color: rgb(166, 1, 189); -fx-border-radius: 30px;");
                }
            });

            toggleButton.setOnMouseExited(e -> {
                if (!toggleButton.isSelected()) {
                    toggleButton.setStyle("-fx-background-color: transparent; -fx-border-radius: 30px; -fx-border-color: rgb(166, 1, 189);");
                }
            });

            container.getChildren().add(toggleButton);
            allToggleButtons.add(toggleButton); // Add toggle button to the list

            // If this is the initially selected button, set specialty
            if (toggleButton.isSelected()) {
                specialty = toggleButton.getText();
            }
        }
    }
}
