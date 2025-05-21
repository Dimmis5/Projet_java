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
    private TextField identifiantField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    private final AuthentificationService authService = AuthentificationService.getInstance();

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        String identifiant = identifiantField.getText();
        String password = passwordField.getText();

        if (identifiant.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Veuillez remplir tous les champs");
            return;
        }

        boolean loginSuccess = authService.loginEtudiant(identifiant, password);

        if (loginSuccess) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource("edt-etudiant-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                EdtEtudiantController controller = fxmlLoader.getController();
                controller.setIdEtudiantConnecte(identifiant);

                Stage stage = new Stage();
                stage.setTitle("Emploi du temps");
                stage.setScene(scene);
                stage.show();

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage.setText("Erreur lors du chargement de l'emploi du temps");
            }
        } else {
            errorMessage.setText("Identifiant ou mot de passe incorrect");
        }
    }

    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}