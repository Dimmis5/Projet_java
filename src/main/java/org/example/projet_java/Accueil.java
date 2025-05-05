package org.example.projet_java;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Accueil extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Button Etudiant = new Button("Etudiant");
        Button Enseignant = new Button("Enseignant");
        Button Administrateur = new Button("Administrateur");

        VBox buttonV = new VBox(25, Etudiant,Enseignant, Administrateur);
        buttonV.setAlignment(Pos.CENTER);

        Scene sceneAccueil = new Scene(buttonV, 400, 400);

        Etudiant.setOnAction(e ->{
            Label labelEtudiant = new Label("Bienvenue Etudiant");
            Button retour = new Button("Retour");

            VBox etudiantLayout = new VBox(20, labelEtudiant, retour);
            etudiantLayout.setAlignment(Pos.CENTER);

            Scene sceneEtudiant = new Scene(etudiantLayout, 400, 400);
            stage.setScene(sceneEtudiant);

            retour.setOnAction(ev -> stage.setScene(sceneAccueil));
        });

        Enseignant.setOnAction(e ->{
            Label labelEnseignant = new Label("Bienvenue Enseignant");
            Button retour = new Button("Retour");

            VBox enseignantLayout = new VBox(20, labelEnseignant, retour);
            enseignantLayout.setAlignment(Pos.CENTER);

            Scene sceneEnseignant = new Scene(enseignantLayout, 400, 400);
            stage.setScene(sceneEnseignant);

            retour.setOnAction(ev -> stage.setScene(sceneAccueil));
        });

        Administrateur.setOnAction(e ->{
            Label labelAdministrateur = new Label("Bienvenue Administrateur");
            Button retour = new Button("Retour");

            VBox administrateurLayout = new VBox(20, labelAdministrateur, retour);
            administrateurLayout.setAlignment(Pos.CENTER);

            Scene sceneAdministrateur = new Scene(administrateurLayout, 400, 400);
            stage.setScene(sceneAdministrateur);

            retour.setOnAction(ev -> stage.setScene(sceneAccueil));
        });

        stage.setTitle("Accueil");
        stage.setScene(sceneAccueil);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}