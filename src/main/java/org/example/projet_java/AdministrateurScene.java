package org.example.projet_java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projet_java.model.Administrateur;
import org.example.projet_java.controller.EdtAdministrateurController;

import java.io.IOException;

public class AdministrateurScene {
    
    private Administrateur administrateur;
    
    public AdministrateurScene(Administrateur administrateur) {
        this.administrateur = administrateur;
    }
    
    public static Scene getLoginScene(Stage stage, Scene sceneAccueil) {
        try {
            FXMLLoader loader = new FXMLLoader(AdministrateurScene.class.getResource("login-administrateur.fxml"));
            Parent root = loader.load();
            
            org.example.projet_java.controller.LoginAdministrateurController controller = loader.getController();
            controller.setStage(stage);
            controller.setSceneAccueil(sceneAccueil);
            
            Scene scene = new Scene(root, 400, 400);
            String css = AdministrateurScene.class.getResource("styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void show(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edt-administrateur.fxml"));
        Parent root = loader.load();
        
        // Get the controller and pass the administrateur object
        EdtAdministrateurController controller = loader.getController();
        controller.setAdministrateur(administrateur);
        controller.initialize();
        
        Scene scene = new Scene(root, 1024, 768);
        String css = this.getClass().getResource("styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setTitle("Emploi du temps - Administration");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
