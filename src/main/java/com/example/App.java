// pour lancer le code dans le terminal : mvn clean javafx:run
// pour compiler le code dans le terminal : mvn clean package

package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setLayoutX(50);
        nameField.setLayoutY(50);
        nameField.setPrefWidth(300);
        nameField.setPrefHeight(30);

        Pane root = new Pane();
        root.getChildren().add(nameField);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Plateforme tracker");
        primaryStage.show();
    }
}
