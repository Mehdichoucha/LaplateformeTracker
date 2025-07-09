package com.example;

import com.example.DatabaseConnection;
import com.example.Eleves;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ElevesDAO {

    public ObservableList<Eleves> getAllEleves() {
        ObservableList<Eleves> eleves = FXCollections.observableArrayList();
        String query = "SELECT * FROM etudiant ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Eleves eleve = new Eleves(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getInt("age")
                );
                eleves.add(eleve);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des élèves : " + e.getMessage());
            e.printStackTrace();
        }
        
        return eleves;
    }
}