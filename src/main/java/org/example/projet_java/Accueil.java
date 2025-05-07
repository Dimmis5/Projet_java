package org.example.projet_java;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;

public class Accueil extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Button Etudiant = new Button("Etudiant");
        Button Enseignant = new Button("Enseignant");
        Button Administrateur = new Button("Administrateur");

        VBox buttonV = new VBox(25, Etudiant,Enseignant, Administrateur);
        buttonV.setAlignment(Pos.CENTER);

        Scene sceneAccueil = new Scene(buttonV, 400, 400);

        Etudiant.setOnAction(e -> stage.setScene(EtudiantScene.getScene(stage, sceneAccueil)));
        Enseignant.setOnAction(e -> stage.setScene(EnseignantScene.getScene(stage, sceneAccueil)));
        Administrateur.setOnAction(e -> stage.setScene(AdministrateurScene.getScene(stage, sceneAccueil)));

        stage.setTitle("Accueil");
        stage.setScene(sceneAccueil);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}