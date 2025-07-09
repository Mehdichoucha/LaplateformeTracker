package com.example;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    
    @FXML private TableView<Eleves> tableEleves;
    @FXML private TableColumn<Eleves, Integer> colId;
    @FXML private TableColumn<Eleves, String> colNom;
    @FXML private TableColumn<Eleves, String> colPrenom;
    @FXML private TableColumn<Eleves, Integer> colAge;
    //@FXML private TableColumn<Eleves, Integer> colMoyenne;
    @FXML private Button btnCharger;
    @FXML private Label lblStatus;
    
    private ElevesDAO elevesDAO;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        elevesDAO = new ElevesDAO();
        
        // Configuration des colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        //ajouter pour la moyenne
        
        // Redimensionnement automatique des colonnes
        if (tableEleves != null) {
            colId.prefWidthProperty().bind(tableEleves.widthProperty().multiply(0.1));
            colNom.prefWidthProperty().bind(tableEleves.widthProperty().multiply(0.3));
            colPrenom.prefWidthProperty().bind(tableEleves.widthProperty().multiply(0.3));
            colAge.prefWidthProperty().bind(tableEleves.widthProperty().multiply(0.3));
        }
        
        // Charger les données au démarrage
        chargerDonnees();
    }
    
    @FXML
    private void chargerDonnees() {
        Task<ObservableList<Eleves>> task = new Task<ObservableList<Eleves>>() {
            @Override
            protected ObservableList<Eleves> call() throws Exception {
                updateMessage("Chargement des données...");
                return elevesDAO.getAllEleves();
            }
            
            @Override
            protected void succeeded() {
                tableEleves.setItems(getValue());
                lblStatus.setText("Données chargées avec succès (" + getValue().size() + " élèves)");
                btnCharger.setDisable(false);
            }
            
            @Override
            protected void failed() {
                lblStatus.setText("Erreur lors du chargement des données");
                btnCharger.setDisable(false);
                getException().printStackTrace();
            }
        };
        
        lblStatus.textProperty().bind(task.messageProperty());
        btnCharger.setDisable(true);
        
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    
    @FXML
    private void actualiserDonnees() {
        chargerDonnees();
    }
}