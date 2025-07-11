package com.example;

import java.io.IOException;

import com.example.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/menu_home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Laplateforme Tracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/example/" + fxml + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
    }

    @Override
    public void stop() {
        // Fermer la connexion à la base de données à la fermeture de l'application
        DatabaseConnection.closeConnection();
    }

    public static void main(String[] args) {
        launch();
    }
}
