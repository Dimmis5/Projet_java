package org.example.projet_java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projet_java.model.Etudiant;
import org.example.projet_java.controller.EdtEtudiantController;

import java.io.IOException;

public class EtudiantScene {
    
    private Etudiant etudiant;
    
    public EtudiantScene(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
    
    public static Scene getLoginScene(Stage stage, Scene sceneAccueil) {
        try {
            FXMLLoader loader = new FXMLLoader(EtudiantScene.class.getResource("login-etudiant.fxml"));
            Parent root = loader.load();
            
            org.example.projet_java.controller.LoginEtudiantController controller = loader.getController();
            controller.setStage(stage);
            controller.setSceneAccueil(sceneAccueil);
            
            Scene scene = new Scene(root, 400, 400);
            String css = EtudiantScene.class.getResource("styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void show(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edt-etudiant.fxml"));
        Parent root = loader.load();
        
        // Get the controller and pass the etudiant object
        EdtEtudiantController controller = loader.getController();
        controller.setEtudiant(etudiant);
        controller.initialize();
        
        Scene scene = new Scene(root, 1024, 768);
        String css = this.getClass().getResource("styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setTitle("Emploi du temps - Étudiant");
        stage.setScene(scene);
        stage.show();
    }
}
