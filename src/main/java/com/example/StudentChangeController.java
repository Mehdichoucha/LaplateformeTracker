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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class StudentChangeController implements Initializable {

    @FXML private Button btnModifier;
    @FXML private Button btnQuitter;
    @FXML private MenuButton menuEleves;
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtAge;
    @FXML private TextField txtMoyenne;

    private ElevesDAO elevesDAO = new ElevesDAO();
    private Eleves eleveSelectionne = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Charger la liste des élèves dans le menu
        menuEleves.getItems().clear();
        for (Eleves eleve : elevesDAO.getAllEleves()) {
            MenuItem item = new MenuItem(eleve.getNom() + " " + eleve.getPrenom() + " (id=" + eleve.getId() + ")");
            item.setOnAction(e -> remplirChamps(eleve));
            menuEleves.getItems().add(item);
        }
    }

    private void remplirChamps(Eleves eleve) {
        this.eleveSelectionne = eleve;
        txtNom.setText(eleve.getNom());
        txtPrenom.setText(eleve.getPrenom());
        txtAge.setText(String.valueOf(eleve.getAge()));
        txtMoyenne.setText(String.valueOf(eleve.getMoyenne()));
        menuEleves.setText(eleve.getNom() + " " + eleve.getPrenom() + " (id=" + eleve.getId() + ")");
    }

    @FXML
    private void retourMenuChange() {
        if (eleveSelectionne == null) {
            showAlert("Erreur", "Veuillez sélectionner un élève à modifier.");
            return;
        }
        String nom = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();
        String ageStr = txtAge.getText().trim();
        if (nom.isEmpty() || prenom.isEmpty() || ageStr.isEmpty()) {
            showAlert("Erreur", "Tous les champs sont obligatoires.");
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'âge doit être un nombre entier.");
            return;
        }
        int moyenne;
        try {
            moyenne = Integer.parseInt(txtMoyenne.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "La moyenne doit être un nombre entier.");
            return;
        }
        eleveSelectionne.setNom(nom);
        eleveSelectionne.setPrenom(prenom);
        eleveSelectionne.setAge(age);
        eleveSelectionne.setMoyenne(moyenne);
        boolean success = elevesDAO.modifierEleve(eleveSelectionne);
        if (success) {
            showAlert("Succès", "Élève modifié avec succès.");
            retournerMenu();
        } else {
            showAlert("Erreur", "Erreur lors de la modification de l'élève.");
        }
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
