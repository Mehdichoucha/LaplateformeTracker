package com.example;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    
    @FXML private TableView<Eleves> tableEleves;
    @FXML private TableColumn<Eleves, Integer> colId;
    @FXML private TableColumn<Eleves, String> colNom;
    @FXML private TableColumn<Eleves, String> colPrenom;
    @FXML private TableColumn<Eleves, Integer> colAge;
    @FXML private TableColumn<Eleves, Integer> colMoyenne;
    @FXML private Button btnCharger;
    @FXML private Button btnEffacerFiltres;
    @FXML private Label lblStatus;
    

    @FXML private TextField txtFiltreNom;
    @FXML private TextField txtFiltrePrenom;
    @FXML private TextField txtFiltreAge;
    @FXML private TextField txtFiltreAgeMax;
    @FXML private TextField txtFiltreMoyenne;
    @FXML private TextField txtFiltreMoyenneMax;
    @FXML private Button btnDetailsEleve;
    
    private ElevesDAO elevesDAO;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        elevesDAO = new ElevesDAO();
        

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colMoyenne.setCellValueFactory(new PropertyValueFactory<>("moyenne"));
        

        if (tableEleves != null) {
            colId.prefWidthProperty().bind(tableEleves.widthProperty().multiply(0.1));
            colNom.prefWidthProperty().bind(tableEleves.widthProperty().multiply(0.25));
            colPrenom.prefWidthProperty().bind(tableEleves.widthProperty().multiply(0.25));
            colAge.prefWidthProperty().bind(tableEleves.widthProperty().multiply(0.2));
            colMoyenne.prefWidthProperty().bind(tableEleves.widthProperty().multiply(0.2));
        }
        

        chargerDonnees();
    }
    
    @FXML
    private void chargerDonnees() {
        Task<ObservableList<Eleves>> task = new Task<ObservableList<Eleves>>() {
            @Override
            protected ObservableList<Eleves> call() throws Exception {
                updateMessage("Chargement des données...");
                

                String filtreNom = txtFiltreNom.getText().trim();
                String filtrePrenom = txtFiltrePrenom.getText().trim();
                String filtreAge = txtFiltreAge.getText().trim();
                String filtreAgeMax = txtFiltreAgeMax.getText().trim();
                String filtreMoyenne = txtFiltreMoyenne.getText().trim();
                String filtreMoyenneMax = txtFiltreMoyenneMax.getText().trim();
                

                boolean hasFilters = !filtreNom.isEmpty() || !filtrePrenom.isEmpty() || 
                                   !filtreAge.isEmpty() || !filtreAgeMax.isEmpty() ||
                                   !filtreMoyenne.isEmpty() || !filtreMoyenneMax.isEmpty();
                
                if (hasFilters) {
                    return elevesDAO.getElevesWithFilters(filtreNom, filtrePrenom, filtreAge, filtreAgeMax, filtreMoyenne, filtreMoyenneMax);
                } else {
                    return elevesDAO.getAllEleves();
                }
            }
            
            @Override
            protected void succeeded() {
                tableEleves.setItems(getValue());
                lblStatus.setText("Données chargées avec succès (" + getValue().size() + " élèves)");
            }
            
            @Override
            protected void failed() {
                lblStatus.setText("Erreur lors du chargement des données");
                getException().printStackTrace();
            }
        };
        
        lblStatus.textProperty().bind(task.messageProperty());
        
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    
    @FXML
    private void actualiserDonnees() {
        chargerDonnees();
    }
    
    @FXML
    private void effacerFiltres() {
        txtFiltreNom.clear();
        txtFiltrePrenom.clear();
        txtFiltreAge.clear();
        txtFiltreAgeMax.clear();
        txtFiltreMoyenne.clear();
        txtFiltreMoyenneMax.clear();
        chargerDonnees();
    }

    @FXML
    private Button btnDeconnexion;

    @FXML
    private void logoffmenu() {
        try {
            App.setRoot("menu_home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button btnAjouterEleve;


    @FXML
    private void ouvrirAjoutEleve() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student_add.fxml"));
            AnchorPane root = loader.load();

            Stage stage = (Stage) btnAjouterEleve.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter un élève");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button btnModifierEleve;

    @FXML
    private void ouvrirModificationEleve() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student_change.fxml"));
            AnchorPane root = loader.load();

            Stage stage = (Stage) btnModifierEleve.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Modifier un élève");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button btnSupprimerEleve;

    @FXML
    private void ouvrirSuppressionEleve() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student_delete.fxml"));
            AnchorPane root = loader.load();

            Stage stage = (Stage) btnSupprimerEleve.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Supprimer un élève");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}