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
        String query = "SELECT e.id, e.nom, e.prenom, e.age, " +
                      "COALESCE(AVG(n.note), 0) as moyenne " +
                      "FROM etudiant e " +
                      "LEFT JOIN note n ON e.id = n.etudiant_id " +
                      "GROUP BY e.id, e.nom, e.prenom, e.age " +
                      "ORDER BY e.id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Eleves eleve = new Eleves(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getInt("age"),
                    (int) Math.round(rs.getDouble("moyenne"))
                );
                eleves.add(eleve);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des élèves : " + e.getMessage());
            e.printStackTrace();
        }
        
        return eleves;
    }
    
    public ObservableList<Eleves> getElevesWithFilters(String filtreNom, String filtrePrenom, 
                                                      String filtreAge, String filtreAgeMax,
                                                      String filtreMoyenne, String filtreMoyenneMax) {
        ObservableList<Eleves> eleves = FXCollections.observableArrayList();
        
        StringBuilder queryBuilder = new StringBuilder(
            "SELECT e.id, e.nom, e.prenom, e.age, " +
            "COALESCE(AVG(n.note), 0) as moyenne " +
            "FROM etudiant e " +
            "LEFT JOIN note n ON e.id = n.etudiant_id " +
            "WHERE 1=1"
        );
        
        // Ajouter les conditions de filtrage
        if (!filtreNom.isEmpty()) {
            queryBuilder.append(" AND e.nom ILIKE ?");
        }
        if (!filtrePrenom.isEmpty()) {
            queryBuilder.append(" AND e.prenom ILIKE ?");
        }
        if (!filtreAge.isEmpty()) {
            queryBuilder.append(" AND e.age >= ?");
        }
        if (!filtreAgeMax.isEmpty()) {
            queryBuilder.append(" AND e.age <= ?");
        }
        
        queryBuilder.append(" GROUP BY e.id, e.nom, e.prenom, e.age");
        
        // Filtres sur la moyenne (après le GROUP BY)
        if (!filtreMoyenne.isEmpty() || !filtreMoyenneMax.isEmpty()) {
            queryBuilder.append(" HAVING 1=1");
            if (!filtreMoyenne.isEmpty()) {
                queryBuilder.append(" AND COALESCE(AVG(n.note), 0) >= ?");
            }
            if (!filtreMoyenneMax.isEmpty()) {
                queryBuilder.append(" AND COALESCE(AVG(n.note), 0) <= ?");
            }
        }
        
        queryBuilder.append(" ORDER BY e.id");
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {
            
            int paramIndex = 1;
            
            // Définir les paramètres WHERE
            if (!filtreNom.isEmpty()) {
                stmt.setString(paramIndex++, "%" + filtreNom + "%");
            }
            if (!filtrePrenom.isEmpty()) {
                stmt.setString(paramIndex++, "%" + filtrePrenom + "%");
            }
            if (!filtreAge.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(filtreAge));
            }
            if (!filtreAgeMax.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(filtreAgeMax));
            }
            
            // Définir les paramètres HAVING (pour la moyenne)
            if (!filtreMoyenne.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(filtreMoyenne));
            }
            if (!filtreMoyenneMax.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(filtreMoyenneMax));
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Eleves eleve = new Eleves(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("age"),
                        (int) Math.round(rs.getDouble("moyenne"))
                    );
                    eleves.add(eleve);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du filtrage des élèves : " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format dans les filtres numériques : " + e.getMessage());
            e.printStackTrace();
        }
        
        return eleves;
    }
}