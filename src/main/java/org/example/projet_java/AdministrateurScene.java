package org.example.projet_java;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdministrateurScene {

    public static Scene getScene(Stage stage, Scene sceneAccueil) {
        Label labelAdmin = new Label("Bienvenue Administrateur");

        Label identifiant = new Label("Identifiant");
        TextField text1 = new TextField();

        Label motdepasse = new Label("Mot de passe");
        TextField text2 = new TextField();

        Button connecter = new Button("Se connecter");
        Button retour = new Button("Retour");

        VBox id = new VBox(1, identifiant, text1);
        VBox mot = new VBox(1, motdepasse, text2);

        VBox afficher = new VBox(30, labelAdmin, id, mot, connecter, retour);
        afficher.setAlignment(Pos.CENTER);

        retour.setOnAction(ev -> stage.setScene(sceneAccueil));

        return new Scene(afficher, 400, 400);
    }
}
