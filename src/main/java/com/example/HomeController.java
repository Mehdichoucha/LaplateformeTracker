package com.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeController {

    @FXML
    void logintomenu(ActionEvent event) throws IOException {
        App.setRoot("menu_main");
    }
}