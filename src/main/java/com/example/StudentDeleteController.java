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
    private Button btnSupprimer;  // Le bouton Supprimer dans student_delete.fxml

    @FXML
    private Button btnQuitter;    // Le bouton Quitter dans student_delete.fxml

    @FXML
    private void initialize() {
        // Pas obligatoire ici mais pratique si tu veux ajouter des actions au démarrage
    }

    @FXML
    private void fermerFenetre() {
        try {
            // Récupérer la fenêtre actuelle
            Stage stage = (Stage) btnQuitter.getScene().getWindow();

            // Charger le menu principal à nouveau
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
