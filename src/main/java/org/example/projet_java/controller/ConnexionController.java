package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.projet_java.Edt;
import org.example.projet_java.model.Utilisateur;
import org.example.projet_java.service.AuthentificationService;

import java.io.IOException;

public class ConnexionController {
    @FXML
    protected TextField identifiantField;
    @FXML
    protected PasswordField motDePasseField;
    @FXML
    protected Button connexionButton;
    @FXML
    protected Button retourButton;
    @FXML
    protected Text messageErreur;

    protected AuthentificationService authentificationService = new AuthentificationService();

    @FXML
    public void initialize() {
        // Initialize the connection controller
        messageErreur.setVisible(false);
    }

    @FXML
    protected void onConnexionButtonClick() {
        String identifiant = identifiantField.getText();
        String motDePasse = motDePasseField.getText();
        
        if (identifiant.isEmpty() || motDePasse.isEmpty()) {
            showErrorMessage("Veuillez remplir tous les champs");
            return;
        }
        
        // Login logic to be implemented by subclasses
        login(identifiant, motDePasse);
    }

    protected void login(String identifiant, String motDePasse) {
        // To be overridden by subclasses
        showErrorMessage("Méthode de connexion non implémentée");
    }

    @FXML
    protected void onRetourButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource("main-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) retourButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void showErrorMessage(String message) {
        messageErreur.setText(message);
        messageErreur.setVisible(true);
    }

    protected void navigateToEdtView(String fxmlFile, String title, Utilisateur utilisateur) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            
            // Get the controller and set the user
            Object controller = fxmlLoader.getController();
            if (controller instanceof EdtController) {
                ((EdtController) controller).setUtilisateur(utilisateur);
            }
            
            Stage stage = (Stage) connexionButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Erreur lors du chargement de l'emploi du temps");
        }
    }
}