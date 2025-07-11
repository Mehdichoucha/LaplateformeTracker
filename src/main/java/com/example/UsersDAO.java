package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/laplateforme_tracker";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";

    public static boolean checkLogin(String nomUtilisateur, String motDePasse) {
        String sql = "SELECT * FROM admin WHERE nom_utilisateur = ? AND motdepasse = ?";

        try (
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, nomUtilisateur);
            stmt.setString(2, motDePasse);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
