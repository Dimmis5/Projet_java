package org.example.projet_java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.projet_java.Edt;
import org.example.projet_java.model.Etudiant;
import org.example.projet_java.service.AuthentificationService;

import java.io.IOException;

public class LoginEtudiantController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    private final AuthentificationService authService = AuthentificationService.getInstance();

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Veuillez remplir tous les champs");
            return;
        }

        boolean loginSuccess = authService.loginEtudiant(email, password);

        if (loginSuccess) {
            try {
                // Charger la vue d'emploi du temps étudiant
                FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource("edt-etudiant.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 400,400);
                Stage stage = new Stage();
                stage.setTitle("Connexion Controller");
                stage.setScene(scene);
                stage.show();

                // Fermer la fenêtre de connexion
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage.setText("Erreur lors du chargement de l'emploi du temps");
            }
        } else {
            errorMessage.setText("Email ou mot de passe incorrect");
        }
    }

    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        try {
            // Revenir à la vue principale
            FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}