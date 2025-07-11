package com.example;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StudentChangeController {

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnQuitter;

    @FXML
    private void retourMenuChange() {
        retournerMenu();
    }

    @FXML
    private void retourMenuQuit() {
        retournerMenu();
    }

    private void retournerMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu_main.fxml"));
            AnchorPane root = loader.load();

            Stage stage = (Stage) btnModifier.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}