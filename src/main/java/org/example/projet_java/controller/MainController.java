package org.example.projet_java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.projet_java.Edt;

import java.io.IOException;

public class MainController {
    @FXML
    private Button btnEtudiant;

    @FXML
    private Button btnEnseignant;

    @FXML
    private Button btnAdministrateur;

    @FXML
    protected void onEtudiantButtonClick(ActionEvent event) {
        try {
            // Charger la vue de connexion étudiant
            FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource("login-etudiant.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Connexion Étudiant");
            stage.setScene(scene);
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onEnseignantButtonClick(ActionEvent event) {
        try {
            // Charger la vue de connexion enseignant
            FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource("login-enseignant.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Connexion Enseignant");
            stage.setScene(scene);
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onAdministrateurButtonClick(ActionEvent event) {
        try {
            // Charger la vue de connexion administrateur
            FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource("login-administrateur.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Connexion Administrateur");
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
