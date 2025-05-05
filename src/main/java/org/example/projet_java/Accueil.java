package org.example.projet_java;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

            Label identifiant = new Label("Identidiant");
            TextField text1 = new TextField();

            Label mdp = new Label("Mot de passe");
            TextField text2 = new TextField();

            Button confirmation = new Button("Se connecter");
            Button retour = new Button("Retour");

            VBox etudiantLayout = new VBox(20, labelEtudiant);
            etudiantLayout.setAlignment(Pos.CENTER);
            VBox idEtudiant = new VBox(1, identifiant, text1);
            VBox mot = new VBox(1, mdp, text2);

            VBox afficher = new VBox(30, etudiantLayout, idEtudiant, mot, confirmation, retour);


            Scene sceneEtudiant = new Scene(afficher, 400, 400);
            stage.setScene(sceneEtudiant);

            retour.setOnAction(ev -> stage.setScene(sceneAccueil));
        });

        Enseignant.setOnAction(e ->{
            Label labelEnseignant = new Label("Bienvenue Enseignant");

            Label identifiant = new Label("Identifiant");
            TextField text1 = new TextField();

            Label motdepasse = new Label("Mot de passe");
            TextField text2 = new TextField();

            Button conneceter = new Button("Se conneceter");
            Button retour = new Button("Retour");

            VBox enseignantLayout = new VBox(20, labelEnseignant);
            enseignantLayout.setAlignment(Pos.CENTER);

            VBox id = new VBox(1, identifiant, text1);
            VBox mot = new VBox(1,motdepasse,text2);

            VBox afficher = new VBox(30, enseignantLayout, id, mot, conneceter, retour);

            Scene sceneEnseignant = new Scene(afficher, 400, 400);
            stage.setScene(sceneEnseignant);

            retour.setOnAction(ev -> stage.setScene(sceneAccueil));
        });

        Administrateur.setOnAction(e ->{
            Label labelAdministrateur = new Label("Bienvenue Administrateur");

            Label iden = new Label("Identifiant");
            TextField text1 = new TextField();

            Label motdepasse = new Label("Mot de passe");
            TextField text2 = new TextField();

            Button conneter = new Button("Se connecter");
            Button retour = new Button("Retour");

            VBox administrateurLayout = new VBox(20, labelAdministrateur, retour);
            administrateurLayout.setAlignment(Pos.CENTER);

            VBox identi = new VBox(1,iden, text1);
            VBox mot = new VBox(1, motdepasse, text2);

            VBox afficher = new VBox(30, administrateurLayout,identi, mot, conneter, retour);

            Scene sceneAdministrateur = new Scene(afficher, 400, 400);
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