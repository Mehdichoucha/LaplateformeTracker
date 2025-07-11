package com.example;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StudentDeleteController {

    @FXML
    private Button btnSupprimer;

    @FXML
    private Button btnQuitter;

    @FXML
    private void initialize() {
    }

    @FXML
    private void fermerFenetre() {
        try {
            Stage stage = (Stage) btnQuitter.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu_main.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Menu principal");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerEtRetour() {
        
        fermerFenetre();
    }
}