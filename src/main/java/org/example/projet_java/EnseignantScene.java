package org.example.projet_java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projet_java.model.Enseignant;
import org.example.projet_java.controller.EdtEnseignantController;

import java.io.IOException;

public class EnseignantScene {
    
    private Enseignant enseignant;
    
    public EnseignantScene(Enseignant enseignant) {
        this.enseignant = enseignant;
    }
    
    public static Scene getLoginScene(Stage stage, Scene sceneAccueil) {
        try {
            FXMLLoader loader = new FXMLLoader(EnseignantScene.class.getResource("login-enseignant.fxml"));
            Parent root = loader.load();
            
            org.example.projet_java.controller.LoginEnseignantController controller = loader.getController();
            controller.setStage(stage);
            controller.setSceneAccueil(sceneAccueil);
            
            Scene scene = new Scene(root, 400, 400);
            String css = EnseignantScene.class.getResource("styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void show(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edt-enseignant.fxml"));
        Parent root = loader.load();
        
        // Get the controller and pass the enseignant object
        EdtEnseignantController controller = loader.getController();
        controller.setEnseignant(enseignant);
        controller.initialize();
        
        Scene scene = new Scene(root, 1024, 768);
        String css = this.getClass().getResource("styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setTitle("Emploi du temps - Enseignant");
        stage.setScene(scene);
        stage.show();
    }
}
