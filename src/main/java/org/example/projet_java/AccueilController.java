package org.example.projet_java;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;

public class AccueilController {

    @FXML
    private Button etudiantButton;

    @FXML
    private Button enseignantButton;

    @FXML
    private Button administrateurButton;

    @FXML
    private GridPane edtGrid;

    @FXML
    private VBox formVBox;

    private final String[] jours = {"", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};
    private final String[] heures = {"", "09h00", "10h00", "11h00", "12h00", "13h00", "14h00", "15h00", "16h00"};

    public void afficherConnexion() {
        etudiantButton.setVisible(false);
        enseignantButton.setVisible(false);
        administrateurButton.setVisible(false);

        Label identifiant = new Label("Identifiant");
        TextField textidentifiant = new TextField();

        HBox hidentifiant = new HBox(10);
        hidentifiant.setPadding(new Insets(20));
        hidentifiant.setAlignment(Pos.CENTER);
        hidentifiant.getChildren().addAll(identifiant, textidentifiant);

        Label mdp = new Label("Mot de passe");
        TextField textmdp = new TextField();

        HBox hmdp = new HBox(10);
        hmdp.setPadding(new Insets(20));
        hmdp.setAlignment(Pos.CENTER);
        hmdp.getChildren().addAll(mdp, textmdp);

        Button connexionButton = new Button("Se connecter");
        connexionButton.setOnAction(event -> edt());

        formVBox.getChildren().addAll(hidentifiant, hmdp, connexionButton);
    }

    public void edt() {
        formVBox.setVisible(false);
        edtGrid.setVisible(true);

        edtGrid.getChildren().clear();

        for (int i = 1; i < jours.length; i++) {
            Label labelJour = new Label(jours[i]);
            edtGrid.add(labelJour, i, 0);
        }

        for (int j = 1; j < heures.length; j++) {
            Label labelHeure = new Label(heures[j]);
            edtGrid.add(labelHeure, 0, j);
        }

        for (int i = 1; i < jours.length; i++) {
            for (int j = 1; j < heures.length; j++) {
                Rectangle rect = new Rectangle(100, 80);
                rect.setFill(Color.LIGHTGRAY);
                rect.setStroke(Color.BLACK);
                edtGrid.add(rect, i, j);
            }
        }
    }

    @FXML
    public void choisirProfilEtudiant() {
        afficherConnexion();
    }

    @FXML
    public void choisirProfilEnseignant() {
        afficherConnexion();
    }

    @FXML
    public void choisirProfilAdministrateur() {
        afficherConnexion();
    }
}
