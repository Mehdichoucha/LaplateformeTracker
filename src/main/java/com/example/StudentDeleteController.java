package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StudentDeleteController implements Initializable {

    @FXML private Button btnSupprimer;
    @FXML private Button btnQuitter;
    @FXML private MenuButton menuEleves;

    private ElevesDAO elevesDAO = new ElevesDAO();
    private Eleves eleveSelectionne = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Charger la liste des élèves dans le menu
        menuEleves.getItems().clear();
        for (Eleves eleve : elevesDAO.getAllEleves()) {
            MenuItem item = new MenuItem(eleve.getNom() + " " + eleve.getPrenom() + " (id=" + eleve.getId() + ")");
            item.setOnAction(e -> {
                eleveSelectionne = eleve;
                menuEleves.setText(eleve.getNom() + " " + eleve.getPrenom() + " (id=" + eleve.getId() + ")");
            });
            menuEleves.getItems().add(item);
        }
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
        if (eleveSelectionne == null) {
            showAlert("Erreur", "Veuillez sélectionner un élève à supprimer.");
            return;
        }
        boolean success = elevesDAO.supprimerEleve(eleveSelectionne.getId());
        if (success) {
            showAlert("Succès", "Élève supprimé avec succès.");
            fermerFenetre();
        } else {
            showAlert("Erreur", "Erreur lors de la suppression de l'élève.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
