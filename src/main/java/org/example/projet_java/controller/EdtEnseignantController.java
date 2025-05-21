package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.projet_java.model.Cours;
import org.example.projet_java.model.Enseignant;
import org.example.projet_java.service.AuthentificationService;
import org.example.projet_java.service.CsvService;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class EdtEnseignantController {
    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMAT_HEURE = DateTimeFormatter.ofPattern("H:mm");

    private static final String CSV_EDT = "CSV_Java/edt.csv";
    private static final String CSV_COURS = "CSV_Java/Cours.csv";

    private final LocalTime heureDebut = LocalTime.of(8, 0);
    private final LocalTime heureFin = LocalTime.of(18, 0);
    private final int intervalMinutes = 60;

    private LocalDate dateDebutSemaineCourante;

    private CsvService csvService;
    private AuthentificationService authService;

    @FXML private GridPane grilleCalendrier;
    @FXML private Label etiquetteMoisAnnee;
    @FXML private Button boutonSemainePrecedente;
    @FXML private Button boutonSemaineSuivante;
    @FXML private Button boutonSemaineCourante;

    private String idEtudiantConnecte;
    private List<Cours> coursDeLEtudiant = new ArrayList<>();

    public EdtEnseignantController() {
        this.csvService = CsvService.getInstance();
        this.authService = AuthentificationService.getInstance();
    }

    @FXML
    public void initialize() {
        configurerControlesCalendrier();
        dateDebutSemaineCourante = LocalDate.now().with(DayOfWeek.MONDAY);
    }

    public void setIdEtudiantConnecte(String idEtudiant) {
        System.out.println("ID Etudiant reçu: " + idEtudiant); // Debug
        this.idEtudiantConnecte = idEtudiant;
        this.coursDeLEtudiant = csvService.CoursEtudiant(idEtudiantConnecte);

        // Debug supplémentaire
        System.out.println("Fichier EDT: " + CSV_EDT);
        System.out.println("Fichier Cours: " + CSV_COURS);
        System.out.println("Cours trouvés: " + coursDeLEtudiant.size());

        afficherSemaine(dateDebutSemaineCourante);
    }

    private void afficherSemaine(LocalDate debutSemaine) {
        mettreAJourEtiquetteMoisAnnee(debutSemaine);
        grilleCalendrier.getChildren().clear();

        configurerDispositionCalendrier();
        ajouterEnTetesJours(debutSemaine);
        ajouterEnTetesHeures();

        int nombreCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
        for (int ligne = 1; ligne <= nombreCreneaux; ligne++) {
            for (int col = 1; col <= 7; col++) {
                grilleCalendrier.add(new Pane(), col, ligne);
            }
        }

        afficherCoursPourSemaine(debutSemaine);
    }

    private void afficherCoursPourSemaine(LocalDate debutSemaine) {
        LocalDate finSemaine = debutSemaine.plusDays(6);
        List<Cours> coursSemaine = new ArrayList<>();

        for (Cours c : coursDeLEtudiant) {
            try {
                LocalDate dateCours = LocalDate.parse(c.getDate(), FORMAT_DATE);
                if (!dateCours.isBefore(debutSemaine) && !dateCours.isAfter(finSemaine)) {
                    coursSemaine.add(c);
                }
            } catch (Exception e) {
                System.err.println("Erreur parsing date pour cours: " + c);
                e.printStackTrace();
            }
        }

        System.out.println("Cours cette semaine: " + coursSemaine.size()); // Debug
        afficherCoursDansGrille(coursSemaine);
    }

// Modification pour la méthode afficherCoursDansGrille dans EdtEtudiantController.java

    private void afficherCoursDansGrille(List<Cours> coursSemaine) {
        for (Cours cours : coursSemaine) {
            try {
                LocalDate dateCours = LocalDate.parse(cours.getDate(), FORMAT_DATE);
                LocalTime heureDebutCours = LocalTime.parse(
                        cours.getHeure_debut().replace("h", ":"), FORMAT_HEURE);
                LocalTime heureFinCours = LocalTime.parse(
                        cours.getHeure_fin().replace("h", ":"), FORMAT_HEURE);

                int colonne = dateCours.getDayOfWeek().getValue();
                int ligneDebut = (int) Duration.between(heureDebut, heureDebutCours).toMinutes() / intervalMinutes + 2 ;
                int hauteur = (int) Duration.between(heureDebutCours, heureFinCours).toMinutes() / intervalMinutes;

                // Debug - Afficher l'ID de l'enseignant tel qu'il est dans le cours
                System.out.println("ID enseignant dans le cours: '" + cours.getId_enseignant() + "'");

                // Récupérer l'enseignant avec trim sur l'ID pour éliminer les espaces
                String enseignantId = cours.getId_enseignant().trim();
                System.out.println("ID enseignant après trim: '" + enseignantId + "'");

                Enseignant enseignant = csvService.getEnseignantById(enseignantId);

                // Debug - Vérifier si l'enseignant a été trouvé
                if (enseignant != null) {
                    System.out.println("Enseignant trouvé: " + enseignant.getNom() + " " + enseignant.getPrenom());
                } else {
                    System.out.println("ATTENTION: Enseignant non trouvé pour l'ID: '" + enseignantId + "'");
                }

                String nomEnseignant = (enseignant != null)
                        ? enseignant.getNom() + " " + enseignant.getPrenom()
                        : "Enseignant inconnu (ID: " + enseignantId + ")";

                // Créer un VBox pour mieux organiser le contenu
                VBox contenuCours = new VBox(5); // 5 pixels de spacing entre les éléments

                Label matiere = new Label(cours.getMatiere());
                matiere.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

                Label salle = new Label("Salle: " + cours.getId_salle());
                Label horaire = new Label(cours.getHeure_debut() + "-" + cours.getHeure_fin());
                Label prof = new Label("Professeur: " + nomEnseignant);

                contenuCours.getChildren().addAll(matiere, salle, horaire, prof);
                contenuCours.setAlignment(javafx.geometry.Pos.CENTER);

                // Créer un Pane pour contenir le VBox
                StackPane celluleCours = new StackPane(contenuCours);
                celluleCours.setStyle(
                        "-fx-background-color: lightblue; " +
                                "-fx-border-color: black; " +
                                "-fx-border-width: 1px; " +
                                "-fx-padding: 5px;"
                );

                // Définir les contraintes pour positionner correctement la cellule
                GridPane.setRowIndex(celluleCours, ligneDebut);
                GridPane.setColumnIndex(celluleCours, colonne);
                GridPane.setRowSpan(celluleCours, hauteur);

                // S'assurer que le contenu s'adapte à l'espace disponible
                celluleCours.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                // Ajouter la cellule à la grille
                grilleCalendrier.getChildren().add(celluleCours);

            } catch (Exception e) {
                System.err.println("Erreur affichage cours: " + cours);
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
        grilleCalendrier.getRowConstraints().clear();

        // Colonne pour les heures
        ColumnConstraints colHeures = new ColumnConstraints(80);
        grilleCalendrier.getColumnConstraints().add(colHeures);

        // Colonnes pour les jours
        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setFillWidth(true);
            grilleCalendrier.getColumnConstraints().add(col);
        }

        // Ligne d'en-tête
        RowConstraints rowHeader = new RowConstraints(30);
        grilleCalendrier.getRowConstraints().add(rowHeader);

        // Lignes pour les créneaux horaires
        int nombreCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
        for (int i = 0; i < nombreCreneaux; i++) {
            RowConstraints row = new RowConstraints(60);
            row.setVgrow(Priority.ALWAYS);
            grilleCalendrier.getRowConstraints().add(row);
        }
    }

    private void ajouterEnTetesJours(LocalDate debutSemaine) {
        DateTimeFormatter formatterJour = DateTimeFormatter.ofPattern("E dd/MM");
        for (int i = 0; i < 7; i++) {
            LocalDate date = debutSemaine.plusDays(i);
            Label etiquetteJour = new Label(date.format(formatterJour));
            etiquetteJour.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            etiquetteJour.setStyle("-fx-alignment: center;");
            grilleCalendrier.add(etiquetteJour, i + 1, 0);
        }
    }

    private void ajouterEnTetesHeures() {
        LocalTime heure = heureDebut;
        int ligne = 1;
        while (!heure.isAfter(heureFin.minusMinutes(intervalMinutes))) {
            Label etiquetteHeure = new Label(heure.format(FORMAT_HEURE));
            etiquetteHeure.setStyle("-fx-alignment: center-right;");
            grilleCalendrier.add(etiquetteHeure, 0, ligne);
            heure = heure.plusMinutes(intervalMinutes);
            ligne++;
        }
    }
}