package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.projet_java.AdministrateurScene;
import org.example.projet_java.model.Administrateur;
import org.example.projet_java.service.AuthentificationService;

public class LoginAdministrateurController {
    
    @FXML
    private TextField tfIdentifiant;
    
    @FXML
    private PasswordField pfMotDePasse;
    
    @FXML
    private Button btnSeConnecter;
    
    @FXML
    private Button btnRetour;
    
    private Stage stage;
    private Scene sceneAccueil;
    private AuthentificationService authentificationService;
    
    public void initialize() {
        authentificationService = new AuthentificationService();
        
        btnSeConnecter.setOnAction(event -> handleConnexion());
        btnRetour.setOnAction(event -> handleRetour());
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void setSceneAccueil(Scene sceneAccueil) {
        this.sceneAccueil = sceneAccueil;
    }
    
    private void handleConnexion() {
        String identifiant = tfIdentifiant.getText();
        String motDePasse = pfMotDePasse.getText();
        
        Administrateur administrateur = authentificationService.authentifierAdministrateur(identifiant, motDePasse);
        
        if (administrateur != null) {
            try {
                new AdministrateurScene(administrateur).show(stage);
            } catch (Exception e) {
                e.printStackTrace();
                // Show error message
            }
        } else {
            // Show error message for invalid credentials
        }
    }
    
    private void handleRetour() {
        stage.setScene(sceneAccueil);
    }
}