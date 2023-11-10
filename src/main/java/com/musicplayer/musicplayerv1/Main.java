package com.musicplayer.musicplayerv1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println(System.getProperty("javafx.runtime.version"));
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource("hello-style.css").toExternalForm());
            stage.setTitle("Music Player");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
            showInitializationErrorDialog(e.getMessage());
        }
    }

    private void showInitializationErrorDialog(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Initialization Error");
        alert.setHeaderText("Application failed to start");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}