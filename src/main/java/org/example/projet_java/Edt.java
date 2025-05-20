package org.example.projet_java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projet_java.controller.MainController;

import java.io.IOException;

public class Edt extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        
        // Get the controller and set the stage
        MainController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setCurrentScene(scene);
        
        stage.setTitle("Gestion des emplois du temps");
        
        // Apply CSS
        String css = this.getClass().getResource("styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}