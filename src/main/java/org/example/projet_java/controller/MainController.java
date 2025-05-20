package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.projet_java.AdministrateurScene;
import org.example.projet_java.EnseignantScene;
import org.example.projet_java.EtudiantScene;

public class MainController {
    
    @FXML
    private Button btnEtudiant;
    
    @FXML
    private Button btnEnseignant;
    
    @FXML
    private Button btnAdministrateur;
    
    private Stage stage;
    private Scene currentScene;
    
    public void initialize() {
        btnEtudiant.setOnAction(event -> handleOpenEtudiantLogin());
        btnEnseignant.setOnAction(event -> handleOpenEnseignantLogin());
        btnAdministrateur.setOnAction(event -> handleOpenAdministrateurLogin());
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }
    
    private void handleOpenEtudiantLogin() {
        Scene sceneEtudiant = EtudiantScene.getLoginScene(stage, currentScene);
        if (sceneEtudiant != null) {
            stage.setTitle("Connexion Étudiant");
            stage.setScene(sceneEtudiant);
        }
    }
    
    private void handleOpenEnseignantLogin() {
        Scene sceneEnseignant = EnseignantScene.getLoginScene(stage, currentScene);
        if (sceneEnseignant != null) {
            stage.setTitle("Connexion Enseignant");
            stage.setScene(sceneEnseignant);
        }
    }
    
    private void handleOpenAdministrateurLogin() {
        Scene sceneAdministrateur = AdministrateurScene.getLoginScene(stage, currentScene);
        if (sceneAdministrateur != null) {
            stage.setTitle("Connexion Administrateur");
            stage.setScene(sceneAdministrateur);
        }
    }
}