package com.musicplayer.musicplayerv1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    protected void onStartButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("page2.fxml"));
            Parent root = loader.load();
            PageTwoController pageTwoController = loader.getController();
            // Create a new scene and set it on the stage
            Scene newScene = new Scene(root, 1100, 600);
            newScene.getStylesheets().add(getClass().getResource("page-two-style.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
}