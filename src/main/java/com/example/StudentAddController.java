package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class StudentAddController implements Initializable {

    @FXML private Button btnModifier;
    @FXML private Button btnQuitter;
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtAge;

    private ElevesDAO elevesDAO = new ElevesDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void retourMenuChange() {
        // Ajout d'un élève
        String nom = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();
        String ageStr = txtAge.getText().trim();
        System.out.println("nom=" + nom + ", prenom=" + prenom + ", age=" + ageStr);
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

        Eleves eleve = new Eleves(0, nom, prenom, age);
        boolean success = elevesDAO.ajouterEleve(eleve);
        if (success) {
            showAlert("Succès", "Élève ajouté avec succès.");
            retournerMenu();
        } else {
            showAlert("Erreur", "Erreur lors de l'ajout de l'élève.");
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
