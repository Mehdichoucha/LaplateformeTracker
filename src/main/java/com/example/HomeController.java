package com.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class HomeController {

    @FXML
    private TextField identifiantField;

    @FXML
    private TextField motdepasseField;

    @FXML
    void logintomenu(ActionEvent event) throws IOException {
        String identifiant = identifiantField.getText();
        String motdepasse = motdepasseField.getText();

        if (UsersDAO.checkLogin(identifiant, motdepasse)) {
            App.setRoot("menu_main");
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText("Identifiants incorrects");
            alert.setContentText("Veuillez vérifier votre identifiant et votre mot de passe.");
            alert.showAndWait();
        }
    }
}
