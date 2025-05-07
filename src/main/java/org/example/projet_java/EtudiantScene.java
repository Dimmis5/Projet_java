package org.example.projet_java;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.YearMonth;

public class EtudiantScene {

    public static Scene getScene(Stage stage, Scene sceneAccueil) {
        Label labelEtudiant = new Label("Bienvenue Etudiant");

        Label identifiant = new Label("Identifiant");
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

        confirmation.setOnAction(evt -> {
            YearMonth moisActuel = YearMonth.now();
            VBox afficherDate = new VBox(10);

            Label titre = new Label(moisActuel.getMonth() + " " + moisActuel.getYear());
            titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            titre.setAlignment(Pos.CENTER);

            GridPane grille = new GridPane();
            grille.setHgap(10);
            grille.setVgap(10);
            grille.setAlignment(Pos.CENTER);

            String[] jourSemaine = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
            for (int i = 0; i < 7; i++) {
                grille.add(new Label(jourSemaine[i]), i, 0);
            }

            LocalDate premierJour = moisActuel.atDay(1);
            int joursemaine = premierJour.getDayOfWeek().getValue();
            int nbJours = moisActuel.lengthOfMonth();
            int ligne = 1;
            int colonne = joursemaine - 1;
            for (int jour = 1; jour <= nbJours; jour++) {
                grille.add(new Label(String.valueOf(jour)), colonne, ligne);
                colonne++;
                if (colonne > 6) {
                    colonne = 0;
                    ligne++;
                }
            }

            Button retoure = new Button("Retour");
            retoure.setOnAction(event -> stage.setScene(sceneAccueil));

            afficherDate.setAlignment(Pos.CENTER);
            afficherDate.getChildren().addAll(titre, grille, retoure);

            stage.setScene(new Scene(afficherDate, 400, 400));
        });

        retour.setOnAction(ev -> stage.setScene(sceneAccueil));

        return new Scene(afficher, 400, 400);
    }
}
