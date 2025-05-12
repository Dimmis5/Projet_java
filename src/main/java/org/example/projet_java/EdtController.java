package org.example.projet_java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EdtController {

    @FXML
    public void connexion(ActionEvent event) {
        Label identifiant = new Label("Identifiant");
        TextField textidentifiant = new TextField();

        HBox hidentifiant = new HBox(20);
        hidentifiant.setAlignment(Pos.CENTER);
        hidentifiant.getChildren().addAll(identifiant, textidentifiant);

        Label mdp = new Label("Mot de passe");
        TextField textmdp = new TextField();

        HBox hmdp = new HBox(20);
        hmdp.setAlignment(Pos.CENTER);
        hmdp.getChildren().addAll(mdp, textmdp);

        Button connexion = new Button("Se connecter");
        connexion.setOnAction(ouvrir -> {
            edt();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(hidentifiant, hmdp, connexion);

        Scene scene = new Scene(layout, 300, 200);
        Stage stage = new Stage();
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void edt() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);

        HBox semainesSection = new HBox(10);
        semainesSection.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 30; i++) {
            final int semaineNumber = i;
            Button semaineButton = new Button(String.valueOf(i));
            // Ajouter action bouton
            semainesSection.getChildren().add(semaineButton);
        }

        layout.getChildren().add(semainesSection);

        GridPane agendaGrid = new GridPane();
        agendaGrid.setHgap(20);
        agendaGrid.setVgap(20);

        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        for (int i = 0; i < jours.length; i++) {
            Label jourLabel = new Label(jours[i]);
            agendaGrid.add(jourLabel, i + 1, 0);
        }

        for (int i = 8; i <= 20; i++) {
            Label heureLabel = new Label(i + ":00");
            agendaGrid.add(heureLabel, 0, i - 7);

            for (int j = 0; j < jours.length; j++) {
                Button timeSlot = new Button("Libre");
                timeSlot.setPrefWidth(100);
                agendaGrid.add(timeSlot, j + 1, i - 7);
            }
        }

        layout.getChildren().add(agendaGrid);

        Scene scene = new Scene(layout, 400, 400);
        Stage stage = new Stage();
        stage.setTitle("Emploi du Temps");
        stage.setScene(scene);
        stage.show();
    }
}
