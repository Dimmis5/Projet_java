package org.example.projet_java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EdtController {
    // Il faudra modifier et mettre le csv dans ressource
    // Ajouter dans le csv l'administrateur en faire un autre ???
    private final String CSV = "C:\\Users\\Oriane\\OneDrive - ISEP\\ALGORITHMIQUE ET PROGRAMMATION\\csv.csv";

    @FXML
    public void connexionetudiant(ActionEvent event) {
        Label identifiant = new Label("Identifiant");
        TextField textidentifiant = new TextField();

        Label mdp = new Label("Mot de passe");
        PasswordField textmdp = new PasswordField();

        Button connexion = new Button("Se connecter");

        connexion.setOnAction(e -> {
            String id_etudiant = textidentifiant.getText();
            String mdp_etudiant = textmdp.getText();

            try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] valeurs = line.split(";");
                    if (valeurs[0].equals(id_etudiant) && valeurs[4].equals(mdp_etudiant)) {
                        edtEtudiant(id_etudiant);
                        break;
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });

        HBox hidentifiant = new HBox(20);
        hidentifiant.setAlignment(Pos.CENTER);
        hidentifiant.getChildren().addAll(identifiant, textidentifiant);

        HBox hmdp = new HBox(20);
        hmdp.setAlignment(Pos.CENTER);
        hmdp.getChildren().addAll(mdp, textmdp);

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
    public void connexionenseignant(ActionEvent event) {
        Label identifiant = new Label("Identifiant");
        TextField textidentifiant = new TextField();

        Label mdp = new Label("Mot de passe");
        PasswordField textmdp = new PasswordField();

        Button connexion = new Button("Se connecter");

        connexion.setOnAction(e -> {
            String id_enseignant = textidentifiant.getText();
            String mdp_enseignant = textmdp.getText();

            try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] valeurs = line.split(";");
                    if (valeurs[16].equals(id_enseignant) && valeurs[20].equals(mdp_enseignant)) {
                        edtEnseignant(id_enseignant);
                        break;
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });

        HBox hidentifiant = new HBox(20);
        hidentifiant.setAlignment(Pos.CENTER);
        hidentifiant.getChildren().addAll(identifiant, textidentifiant);

        HBox hmdp = new HBox(20);
        hmdp.setAlignment(Pos.CENTER);
        hmdp.getChildren().addAll(mdp, textmdp);

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
    public void edtEnseignant(String id_enseignant) {
        LocalDate dateActuelle = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateFormatee = dateActuelle.format(formatter);
        System.out.println(dateFormatee);

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
                Button cours = new Button("");
                cours.setPrefWidth(100);
                agendaGrid.add(cours, j + 1, i - 7);
            }
        }

        layout.getChildren().add(agendaGrid);

        Scene scene = new Scene(layout, 400, 400);
        Stage stage = new Stage();
        stage.setTitle("Emploi du Temps");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void edtEtudiant(String id_etudiant) {
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
                Button cours = new Button("");
                cours.setPrefWidth(100);
                agendaGrid.add(cours, j + 1, i - 7);
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
