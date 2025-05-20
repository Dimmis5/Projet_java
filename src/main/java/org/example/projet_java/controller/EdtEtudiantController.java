package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.projet_java.service.CsvService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EdtEtudiantController implements Initializable {

    @FXML
    private Label titreSemaine;

    @FXML
    private GridPane agendaGrid;

    @FXML
    private TextField textDate;

    @FXML
    private TextField textMessage;

    private static final String CSV = "COURS.csv";
    private String idEtudiant;
    private List<String> semaine = new ArrayList<>();
    private String[] heures = {"8h00", "9h00", "10h00", "11h00", "12h00", "13h00", "14h00", "15h00", "16h00", "17h00", "18h00", "19h00", "20h00"};
    private Button[][] cellules = new Button[6][13];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initData(String id_etudiant) {
        this.idEtudiant = id_etudiant;

        configureSemaine();

        configureAgendaGrid();

        chargerCours();

        Stage stage = (Stage) agendaGrid.getScene().getWindow();
        stage.setTitle("Emploi du Temps - " + idEtudiant);
    }

    private void configureSemaine() {
        semaine.clear();
        LocalDate aujourdhui = LocalDate.now();
        LocalDate lundi = aujourdhui.with(DayOfWeek.MONDAY);
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            semaine.add(lundi.plusDays(i).format(dateformatter));
        }

        titreSemaine.setText("Semaine du " + semaine.get(0) + " au " + semaine.get(6));
    }

    private void configureAgendaGrid() {
        // Nettoyer la grille
        agendaGrid.getChildren().clear();

        for (int i = 0; i < 6; i++) {
            Label jourLabel = new Label(semaine.get(i));
            agendaGrid.add(jourLabel, i + 1, 0);
        }

        for (int heure = 0; heure <= 12; heure++) {
            Label heureLabel = new Label(heures[heure]);
            agendaGrid.add(heureLabel, 0, heure + 1);
        }

        for (int jour = 0; jour < 6; jour++) {
            for (int heure = 0; heure <= 12; heure++) {
                Button celluleVide = new Button();
                celluleVide.setPrefWidth(100);
                celluleVide.setPrefHeight(30);
                agendaGrid.add(celluleVide, jour + 1, heure + 1);
                cellules[jour][heure] = celluleVide;
            }
        }
    }

    private void chargerCours() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] cours = line.split(";");
                if (cours[0].equals(idEtudiant)) {
                    ajouterCours(cours);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            afficherErreur("Erreur de chargement", "Impossible de charger les données des cours.");
        }
    }

    private void ajouterCours(String[] cours) {
        String dateCours = cours[12];
        String heureCours = cours[13];
        String heurefinCours = cours[14];
        String matiere = cours[11];
        String enseignant_nom = cours[17];
        String enseignant_prenom = cours[18];
        String salle = cours[6];

        int jourIndex = semaine.indexOf(dateCours);
        int heureIndex = -1;
        for (int i = 0; i < heures.length; i++) {
            if (heures[i].equals(heureCours)) {
                heureIndex = i;
                break;
            }
        }

        if (jourIndex >= 0 && jourIndex < 6 && heureIndex >= 0 && heureIndex <= 12) {
            Button coursButton = new Button(matiere);
            coursButton.setPrefWidth(100);
            coursButton.setPrefHeight(30);

            agendaGrid.getChildren().remove(cellules[jourIndex][heureIndex]);
            agendaGrid.add(coursButton, jourIndex + 1, heureIndex + 1);

            final String matiereInfo = matiere;
            coursButton.setOnAction(e -> afficherDetailsCours(
                    matiereInfo, dateCours, heureCours, heurefinCours,
                    enseignant_nom, enseignant_prenom, salle
            ));
        }
    }

    private void afficherDetailsCours(String matiere, String date, String heureDebut, String heureFin,
                                      String nomEnseignant, String prenomEnseignant, String salle) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails du cours");
        alert.setHeaderText("Cours de " + matiere);
        alert.setContentText("Date : " + date
                + "\nHeure : " + heureDebut + " - " + heureFin
                + "\nEnseignant : " + nomEnseignant + " " + prenomEnseignant
                + "\nSalle : " + salle);
        alert.showAndWait();
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}