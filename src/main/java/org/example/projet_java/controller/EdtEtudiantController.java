package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.projet_java.model.Cours;
import org.example.projet_java.service.AuthentificationService;
import org.example.projet_java.service.CsvService;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EdtEtudiantController {
    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMAT_HEURE = DateTimeFormatter.ofPattern("H'h'mm");

    private final LocalTime heureDebut = LocalTime.of(8, 0);
    private final LocalTime heureFin = LocalTime.of(18, 0);
    private final int intervalMinutes = 60;

    private LocalDate dateDebutSemaineCourante;

    private CsvService csvService;
    private AuthentificationService authService;

    @FXML
    private GridPane grilleCalendrier;

    @FXML
    private Label etiquetteMoisAnnee;

    @FXML
    private Button boutonSemainePrecedente;

    @FXML
    private Button boutonSemaineSuivante;

    @FXML
    private Button boutonSemaineCourante;

    private String idEtudiantConnecte;

    private List<Cours> coursDeLEtudiant = new ArrayList<>();

    public EdtEtudiantController() {
        this.csvService = CsvService.getInstance();
        this.authService = AuthentificationService.getInstance();
    }

    @FXML
    public void initialize() {
        configurerControlesCalendrier();
        dateDebutSemaineCourante = LocalDate.now().with(DayOfWeek.MONDAY);
    }

    public void setIdEtudiantConnecte(String idEtudiant) {
        this.idEtudiantConnecte = idEtudiant;
        System.out.println("Chargement des cours pour l'étudiant : " + idEtudiantConnecte);
        this.coursDeLEtudiant = csvService.CoursEtudiant(idEtudiantConnecte);
        System.out.println("Nombre total de cours récupérés : " + coursDeLEtudiant.size());
        afficherSemaine(dateDebutSemaineCourante);
    }

    private void afficherSemaine(LocalDate debutSemaine) {
        System.out.println("Affichage de la semaine à partir du " + debutSemaine);
        mettreAJourEtiquetteMoisAnnee(debutSemaine);

        grilleCalendrier.getChildren().clear();

        configurerDispositionCalendrier();
        ajouterEnTetesJours(debutSemaine);
        ajouterEnTetesHeures();

        int nombreCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
        for (int ligne = 1; ligne <= nombreCreneaux; ligne++) {
            for (int col = 1; col <= 7; col++) {
                Pane cellule = new Pane();
                grilleCalendrier.add(cellule, col, ligne);
            }
        }

        afficherCoursPourSemaine(debutSemaine);
    }

    private void afficherCoursPourSemaine(LocalDate debutSemaine) {
        LocalDate finSemaine = debutSemaine.plusDays(6);
        System.out.println("Filtrage des cours entre " + debutSemaine + " et " + finSemaine);
        List<Cours> coursSemaine = new ArrayList<>();
        for (Cours c : coursDeLEtudiant) {
            LocalDate dateCours = LocalDate.parse(c.getDate(), FORMAT_DATE);
            if (!dateCours.isBefore(debutSemaine) && !dateCours.isAfter(finSemaine)) {
                coursSemaine.add(c);
            }
        }
        System.out.println("Nombre de cours dans la semaine : " + coursSemaine.size());
        afficherCours(coursSemaine);
    }

    private void afficherCours(List<Cours> coursList) {
        System.out.println("Affichage des cours...");
        for (Cours c : coursList) {
            try {
                LocalDate dateCours = LocalDate.parse(c.getDate(), FORMAT_DATE);
                LocalTime heureDebutCours = LocalTime.parse(c.getHeure_debut().trim(), FORMAT_HEURE);
                LocalTime heureFinCours = LocalTime.parse(c.getHeure_fin().trim(), FORMAT_HEURE);

                int col = dateCours.getDayOfWeek().getValue();
                int ligneDebut = calculerIndiceLigneHeure(heureDebutCours);
                int dureeLignes = calculerNombreDeLignes(heureDebutCours, heureFinCours);

                Label labelCours = new Label(c.getMatiere() + "\n" + c.getId_salle());
                labelCours.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                labelCours.setWrapText(true);
                labelCours.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-padding: 5px;");

                grilleCalendrier.add(labelCours, col, ligneDebut, 1, dureeLignes);
                GridPane.setFillWidth(labelCours, true);
                GridPane.setFillHeight(labelCours, true);
            } catch (Exception e) {
                System.err.println("Erreur lors de l'affichage du cours : " + c);
                e.printStackTrace();
            }
        }
    }

    private void configurerControlesCalendrier() {
        boutonSemainePrecedente.setOnAction(e -> naviguerSemaine(-1));
        boutonSemaineSuivante.setOnAction(e -> naviguerSemaine(1));
        boutonSemaineCourante.setOnAction(e -> {
            dateDebutSemaineCourante = LocalDate.now().with(DayOfWeek.MONDAY);
            afficherSemaine(dateDebutSemaineCourante);
        });
    }

    private void naviguerSemaine(int decalage) {
        dateDebutSemaineCourante = dateDebutSemaineCourante.plusWeeks(decalage);
        afficherSemaine(dateDebutSemaineCourante);
    }

    private void mettreAJourEtiquetteMoisAnnee(LocalDate debutSemaine) {
        LocalDate finSemaine = debutSemaine.plusDays(6);
        etiquetteMoisAnnee.setText("Semaine du " + debutSemaine.format(FORMAT_DATE) +
                " au " + finSemaine.format(FORMAT_DATE));
    }

    private void configurerDispositionCalendrier() {
        grilleCalendrier.getColumnConstraints().clear();

        ColumnConstraints colonneHeure = new ColumnConstraints();
        colonneHeure.setPercentWidth(10);
        grilleCalendrier.getColumnConstraints().add(colonneHeure);

        for (int i = 0; i < 7; i++) {
            ColumnConstraints colonneJour = new ColumnConstraints();
            colonneJour.setPercentWidth(90.0 / 7);
            grilleCalendrier.getColumnConstraints().add(colonneJour);
        }

        grilleCalendrier.getRowConstraints().clear();

        RowConstraints ligneEnTete = new RowConstraints();
        ligneEnTete.setMinHeight(30);
        grilleCalendrier.getRowConstraints().add(ligneEnTete);

        int nombreCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
        for (int i = 0; i < nombreCreneaux; i++) {
            RowConstraints ligneCreneau = new RowConstraints();
            ligneCreneau.setMinHeight(60);
            grilleCalendrier.getRowConstraints().add(ligneCreneau);
        }
    }

    private void ajouterEnTetesJours(LocalDate debutSemaine) {
        for (int i = 0; i < 7; i++) {
            LocalDate date = debutSemaine.plusDays(i);
            String texteJour = date.format(FORMAT_DATE);

            Label etiquetteJour = new Label(texteJour);
            grilleCalendrier.add(etiquetteJour, i + 1, 0);
        }
    }

    private void ajouterEnTetesHeures() {
        LocalTime heure = heureDebut;
        int ligne = 1;

        while (!heure.isAfter(heureFin.minusMinutes(intervalMinutes))) {
            Label etiquetteHeure = new Label(heure.format(FORMAT_HEURE));
            grilleCalendrier.add(etiquetteHeure, 0, ligne);

            heure = heure.plusMinutes(intervalMinutes);
            ligne++;
        }
    }

    private int calculerIndiceLigneHeure(LocalTime heure) {
        long minutes = Duration.between(heureDebut, heure).toMinutes();
        return (int) (minutes / intervalMinutes) + 1;
    }

    private int calculerNombreDeLignes(LocalTime debut, LocalTime fin) {
        long dureeMinutes = Duration.between(debut, fin).toMinutes();
        return Math.max(1, (int) Math.ceil(dureeMinutes / (double) intervalMinutes));
    }
}
