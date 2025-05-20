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
    protected void connexionEtudiant(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource("login-etudiant-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Connexion Étudiant");
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void connexionEnseignant(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource("login-enseignant-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Connexion Enseignant");
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void connexionAdministrateur(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Edt.class.getResource("login-administrateur-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Connexion Administrateur");
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
