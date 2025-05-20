package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.example.projet_java.model.Cours;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EdtEtudiantController {

    @FXML
    private GridPane agendaGrid;

    private final LocalTime startTime = LocalTime.of(8, 0);
    private final LocalTime endTime = LocalTime.of(18, 0);
    private final int intervalMinutes = 60;

    private LocalDate monday;

    @FXML
    public void initialize() {
        afficherSemaineCourante();
    }

    private void afficherSemaineCourante() {
        agendaGrid.getChildren().clear(); // Efface les anciens nœuds

        // Obtenir la date du lundi de la semaine courante
        LocalDate currentDate = LocalDate.now();
        monday = currentDate.with(DayOfWeek.MONDAY);

        // Définir les en-têtes de colonnes (jours)
        for (int i = 0; i < 7; i++) {
            LocalDate jour = monday.plusDays(i);
            Label jourLabel = new Label(jour.getDayOfWeek().toString() + "\n" + jour.toString());
            agendaGrid.add(jourLabel, i + 1, 0); // colonne i+1, ligne 0
        }

        // Définir les horaires sur la première colonne
        LocalTime heure = startTime;
        for (int i = 0; heure.isBefore(endTime); i++) {
            Label heureLabel = new Label(heure.toString());
            agendaGrid.add(heureLabel, 0, i + 1); // colonne 0, ligne i+1
            heure = heure.plusMinutes(intervalMinutes);
        }

        // Charger et afficher les cours
        List<Cours> coursDeLaSemaine = chargerCoursDepuisCSV("CSV_Java/Cours.csv", "G4A");
        afficherCoursDansGrille(coursDeLaSemaine);
    }

    private List<Cours> chargerCoursDepuisCSV(String chemin, String classeEtudiant) {
        List<Cours> coursList = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            List<String> lignes = Files.readAllLines(Paths.get(chemin));
            lignes.remove(0); // Supprimer l'en-tête

            for (String ligne : lignes) {
                String[] tokens = ligne.split(",");
                if (tokens.length < 9) continue;

                String id_cours = tokens[0];
                String id_salle = tokens[1];
                String matiere = tokens[2];
                String date = tokens[3];
                String heure_debut = tokens[4];
                String heure_fin = tokens[5];
                String id_enseignant = tokens[6];
                String classe = tokens[7];
                boolean annulation = Boolean.parseBoolean(tokens[8]);

                if (!annulation && classeEtudiant.equals(classe)) {
                    Cours cours = new Cours(id_cours, id_salle, matiere, date, heure_debut, heure_fin, id_enseignant, classe, annulation);
                    coursList.add(cours);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return coursList;
    }

    private void afficherCoursDansGrille(List<Cours> coursList) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Cours cours : coursList) {
            try {
                LocalDate coursDate = LocalDate.parse(cours.getDate(), dateFormatter);

                if (!coursDate.isBefore(monday) && !coursDate.isAfter(monday.plusDays(6))) {
                    int dayCol = coursDate.getDayOfWeek().getValue(); // Lundi = 1

                    // Convertir heure début en index de ligne
                    LocalTime heureDebut = LocalTime.parse(cours.getHeure_debut().replace("h", ":"));
                    int rowIndex = (int) Duration.between(startTime, heureDebut).toMinutes() / intervalMinutes + 1;

                    if (dayCol >= 1 && dayCol <= 7 && rowIndex >= 1 && rowIndex <= 10) {
                        Label coursLabel = new Label(cours.getMatiere() + "\n" + cours.getHeure_debut() + " - " + cours.getHeure_fin());
                        coursLabel.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-padding: 5px;");
                        agendaGrid.add(coursLabel, dayCol, rowIndex);
                    }
                }
            } catch (Exception e) {
                System.err.println("Erreur lors du traitement du cours : " + cours.getMatiere());
                e.printStackTrace();
            }
        }
    }
}
