package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.projet_java.model.Cours;
import org.example.projet_java.model.Enseignant;
import org.example.projet_java.service.AuthentificationService;
import org.example.projet_java.service.CsvService;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EdtEtudiantController {
    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMAT_HEURE = DateTimeFormatter.ofPattern("H:mm");
    private static final DateTimeFormatter FORMAT_MOIS_ANNEE = DateTimeFormatter.ofPattern("MMMM yyyy");

    private static final String CSV_EDT = "CSV_Java/edt.csv";
    private static final String CSV_COURS = "CSV_Java/Cours.csv";

    private final LocalTime heureDebut = LocalTime.of(8, 0);
    private final LocalTime heureFin = LocalTime.of(18, 0);
    private final int intervalMinutes = 60;

    private LocalDate dateDebutSemaineCourante;
    private LocalDate dateCourante;
    private LocalDate premierJourMoisCourant;

    private CsvService csvService;
    private AuthentificationService authService;

    @FXML private GridPane grilleCalendrier;
    @FXML private Label etiquetteMoisAnnee;
    @FXML private Button boutonSemainePrecedente;
    @FXML private Button boutonSemaineSuivante;
    @FXML private Button boutonSemaineCourante;
    @FXML private Button boutonVueJour;
    @FXML private Button boutonVueSemaine;
    @FXML private Button boutonVueMois;

    private enum ModeAffichage { JOUR, SEMAINE, MOIS }
    private ModeAffichage modeAffichage = ModeAffichage.SEMAINE;

    private String idEtudiantConnecte;
    private List<Cours> coursDeLEtudiant = new ArrayList<>();

    public EdtEtudiantController() {
        this.csvService = CsvService.getInstance();
        this.authService = AuthentificationService.getInstance();
    }

    private StackPane creerCelluleCoursAvecCadre(VBox contenuCours) {
        StackPane cellule = new StackPane(contenuCours);

        // Style du cadre
        String style = "-fx-background-color: #e3f2fd; " +  // Couleur de fond légère
                "-fx-border-color: #1976d2; " +      // Bordure bleue
                "-fx-border-width: 2px; " +          // Épaisseur de la bordure
                "-fx-border-radius: 5px; " +         // Coins arrondis
                "-fx-padding: 5px; " +               // Marge intérieure
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 3, 0, 0);"; // Ombre légère

        cellule.setStyle(style);
        cellule.getStyleClass().add("cellule-cours");

        return cellule;
    }

    @FXML
    public void initialize() {
        configurerControlesCalendrier();
        dateCourante = LocalDate.now();
        dateDebutSemaineCourante = dateCourante.with(DayOfWeek.MONDAY);
        premierJourMoisCourant = dateCourante.withDayOfMonth(1);

        // Configuration des boutons de vue
        boutonVueJour.setOnAction(e -> changerModeAffichage(ModeAffichage.JOUR));
        boutonVueSemaine.setOnAction(e -> changerModeAffichage(ModeAffichage.SEMAINE));
        boutonVueMois.setOnAction(e -> changerModeAffichage(ModeAffichage.MOIS));

        // Afficher la vue par défaut
        afficherVueSemaine();
    }

    public void setIdEtudiantConnecte(String idEtudiant) {
        this.idEtudiantConnecte = idEtudiant;
        this.coursDeLEtudiant = csvService.CoursEtudiant(idEtudiantConnecte);
        rafraichirAffichage();
    }

    private void changerModeAffichage(ModeAffichage nouveauMode) {
        modeAffichage = nouveauMode;
        rafraichirAffichage();
    }

    private void rafraichirAffichage() {
        switch (modeAffichage) {
            case JOUR:
                afficherVueJour(dateCourante);
                break;
            case SEMAINE:
                afficherVueSemaine();
                break;
            case MOIS:
                afficherVueMois();
                break;
        }
    }

    // ==================== VUE JOUR ====================
    private void afficherVueJour(LocalDate date) {
        grilleCalendrier.getChildren().clear();
        configurerDispositionCalendrierJour();
        mettreAJourEtiquetteDate(date.format(FORMAT_DATE));

        List<Cours> coursDuJour = filtrerCoursParDate(date);
        afficherCoursDansGrilleJour(coursDuJour);
    }

    private void configurerDispositionCalendrierJour() {
        grilleCalendrier.getColumnConstraints().clear();
        grilleCalendrier.getRowConstraints().clear();

        // Colonne pour les heures
        ColumnConstraints colHeures = new ColumnConstraints(80);
        grilleCalendrier.getColumnConstraints().add(colHeures);

        // Colonne principale
        ColumnConstraints colPrincipale = new ColumnConstraints();
        colPrincipale.setHgrow(Priority.ALWAYS);
        grilleCalendrier.getColumnConstraints().add(colPrincipale);

        // Lignes pour les créneaux horaires
        int nombreCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
        for (int i = 0; i <= nombreCreneaux; i++) {
            RowConstraints row = new RowConstraints(60);
            if (i == 0) row.setPrefHeight(30); // En-tête
            grilleCalendrier.getRowConstraints().add(row);
        }

        // Ajouter les heures
        ajouterEnTetesHeures();
    }

    private void afficherCoursDansGrilleJour(List<Cours> cours) {
        for (Cours c : cours) {
            try {
                LocalTime heureDebutCours = LocalTime.parse(c.getHeure_debut().replace("h", ":"), FORMAT_HEURE);
                LocalTime heureFinCours = LocalTime.parse(c.getHeure_fin().replace("h", ":"), FORMAT_HEURE);

                int ligneDebut = (int) Duration.between(heureDebut, heureDebutCours).toMinutes() / intervalMinutes + 2;
                int hauteur = (int) Duration.between(heureDebutCours, heureFinCours).toMinutes() / intervalMinutes;

                VBox contenuCours = creerContenuCours(c);
                StackPane celluleCours = creerCelluleCoursAvecCadre(contenuCours);

                GridPane.setRowIndex(celluleCours, ligneDebut);
                GridPane.setColumnIndex(celluleCours, 1);
                GridPane.setRowSpan(celluleCours, hauteur);

                grilleCalendrier.getChildren().add(celluleCours);
            } catch (Exception e) {
                System.err.println("Erreur affichage cours: " + c);
                e.printStackTrace();
            }
        }
    }

    // ==================== VUE SEMAINE ====================
    private void afficherVueSemaine() {
        grilleCalendrier.getChildren().clear();
        configurerDispositionCalendrierSemaine();
        mettreAJourEtiquetteDate("Semaine du " + dateDebutSemaineCourante.format(FORMAT_DATE) +
                " au " + dateDebutSemaineCourante.plusDays(6).format(FORMAT_DATE));

        ajouterEnTetesJours(dateDebutSemaineCourante);
        ajouterEnTetesHeures();

        // Créer les cellules vides
        int nombreCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
        for (int ligne = 1; ligne <= nombreCreneaux; ligne++) {
            for (int col = 1; col <= 7; col++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("cellule-semaine");
                grilleCalendrier.add(pane, col, ligne);
            }
        }

        afficherCoursDansGrilleSemaine();
    }

    private void configurerDispositionCalendrierSemaine() {
        grilleCalendrier.getColumnConstraints().clear();
        grilleCalendrier.getRowConstraints().clear();

        // Colonne pour les heures
        ColumnConstraints colHeures = new ColumnConstraints(80);
        grilleCalendrier.getColumnConstraints().add(colHeures);

        // Colonnes pour les jours
        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
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

    private void afficherCoursDansGrilleSemaine() {
        LocalDate finSemaine = dateDebutSemaineCourante.plusDays(6);
        List<Cours> coursSemaine = filtrerCoursParPeriode(dateDebutSemaineCourante, finSemaine);

        for (Cours cours : coursSemaine) {
            try {
                LocalDate dateCours = LocalDate.parse(cours.getDate(), FORMAT_DATE);
                LocalTime heureDebutCours = LocalTime.parse(
                        cours.getHeure_debut().replace("h", ":"), FORMAT_HEURE);
                LocalTime heureFinCours = LocalTime.parse(
                        cours.getHeure_fin().replace("h", ":"), FORMAT_HEURE);

                int colonne = dateCours.getDayOfWeek().getValue();
                int ligneDebut = (int) Duration.between(heureDebut, heureDebutCours).toMinutes() / intervalMinutes + 1;
                int hauteur = (int) Duration.between(heureDebutCours, heureFinCours).toMinutes() / intervalMinutes;

                VBox contenuCours = creerContenuCours(cours);
                StackPane celluleCours = creerCelluleCoursAvecCadre(contenuCours);

                GridPane.setRowIndex(celluleCours, ligneDebut);
                GridPane.setColumnIndex(celluleCours, colonne);
                GridPane.setRowSpan(celluleCours, hauteur);

                grilleCalendrier.getChildren().add(celluleCours);
            } catch (Exception e) {
                System.err.println("Erreur affichage cours: " + cours);
                e.printStackTrace();
            }
        }
    }

    // ==================== VUE MOIS ====================
    private void afficherVueMois() {
        grilleCalendrier.getChildren().clear();
        configurerDispositionCalendrierMois();
        mettreAJourEtiquetteDate(premierJourMoisCourant.format(FORMAT_MOIS_ANNEE));

        // En-têtes des jours
        String[] joursSemaine = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        for (int i = 0; i < 7; i++) {
            Label label = new Label(joursSemaine[i]);
            label.getStyleClass().add("en-tete-jour-mois");
            grilleCalendrier.add(label, i, 0);
        }

        // Remplir le calendrier
        LocalDate premierJour = premierJourMoisCourant;
        LocalDate dernierJour = premierJourMoisCourant.withDayOfMonth(premierJourMoisCourant.lengthOfMonth());

        // Commencer au lundi précédent si nécessaire
        LocalDate dateCourante = premierJour.with(DayOfWeek.MONDAY);
        if (premierJour.getDayOfWeek() != DayOfWeek.MONDAY) {
            dateCourante = premierJour.minusDays(premierJour.getDayOfWeek().getValue() - 1);
        }

        int ligne = 1;
        while (dateCourante.isBefore(dernierJour.plusDays(1)) || ligne <= 6) {
            for (int col = 0; col < 7; col++) {
                VBox cellule = new VBox();
                cellule.getStyleClass().add("cellule-mois");

                if (dateCourante.getMonth() != premierJourMoisCourant.getMonth()) {
                    cellule.getStyleClass().add("cellule-hors-mois");
                }

                Label labelJour = new Label(String.valueOf(dateCourante.getDayOfMonth()));
                cellule.getChildren().add(labelJour);

                // Ajouter les cours pour ce jour
                afficherCoursDansCelluleMois(cellule, dateCourante);

                grilleCalendrier.add(cellule, col, ligne);
                dateCourante = dateCourante.plusDays(1);
            }
            ligne++;
        }
    }

    private void configurerDispositionCalendrierMois() {
        grilleCalendrier.getColumnConstraints().clear();
        grilleCalendrier.getRowConstraints().clear();

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

        // 6 lignes pour les semaines
        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints(100);
            row.setVgrow(Priority.ALWAYS);
            grilleCalendrier.getRowConstraints().add(row);
        }
    }

    private void afficherCoursDansCelluleMois(VBox cellule, LocalDate date) {
        List<Cours> coursDuJour = filtrerCoursParDate(date);
        if (coursDuJour.isEmpty()) return;

        for (Cours cours : coursDuJour) {
            try {
                Label labelCours = new Label(cours.getMatiere());
                labelCours.setStyle("-fx-background-color: #bbdefb; " +
                        "-fx-border-color: #1976d2; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 3px; " +
                        "-fx-padding: 2px 5px;");
                labelCours.getStyleClass().add("cours-mois");
                labelCours.setTooltip(creerTooltipCours(cours));
                cellule.getChildren().add(labelCours);
            } catch (Exception e) {
                System.err.println("Erreur affichage cours mois: " + cours);
            }
        }
    }

    // ==================== METHODES COMMUNES ====================
    private void configurerControlesCalendrier() {
        boutonSemainePrecedente.setOnAction(e -> naviguer(-1));
        boutonSemaineSuivante.setOnAction(e -> naviguer(1));
        boutonSemaineCourante.setOnAction(e -> {
            dateCourante = LocalDate.now();
            dateDebutSemaineCourante = dateCourante.with(DayOfWeek.MONDAY);
            premierJourMoisCourant = dateCourante.withDayOfMonth(1);
            rafraichirAffichage();
        });
    }

    private void naviguer(int decalage) {
        switch (modeAffichage) {
            case JOUR:
                dateCourante = dateCourante.plusDays(decalage);
                break;
            case SEMAINE:
                dateDebutSemaineCourante = dateDebutSemaineCourante.plusWeeks(decalage);
                break;
            case MOIS:
                premierJourMoisCourant = premierJourMoisCourant.plusMonths(decalage);
                break;
        }
        rafraichirAffichage();
    }

    private void mettreAJourEtiquetteDate(String texte) {
        etiquetteMoisAnnee.setText(texte);
    }

    private List<Cours> filtrerCoursParDate(LocalDate date) {
        List<Cours> result = new ArrayList<>();
        for (Cours c : coursDeLEtudiant) {
            try {
                LocalDate dateCours = LocalDate.parse(c.getDate(), FORMAT_DATE);
                if (dateCours.equals(date)) {
                    result.add(c);
                }
            } catch (Exception e) {
                System.err.println("Erreur parsing date pour cours: " + c);
            }
        }
        return result;
    }

    private List<Cours> filtrerCoursParPeriode(LocalDate debut, LocalDate fin) {
        List<Cours> result = new ArrayList<>();
        for (Cours c : coursDeLEtudiant) {
            try {
                LocalDate dateCours = LocalDate.parse(c.getDate(), FORMAT_DATE);
                if (!dateCours.isBefore(debut) && !dateCours.isAfter(fin)) {
                    result.add(c);
                }
            } catch (Exception e) {
                System.err.println("Erreur parsing date pour cours: " + c);
            }
        }
        return result;
    }

    private VBox creerContenuCours(Cours cours) {
        VBox contenu = new VBox(3);
        contenu.setAlignment(Pos.CENTER);
        contenu.setPadding(new Insets(5));

        Label matiere = new Label(cours.getMatiere());
        matiere.setStyle("-fx-font-weight: bold;");

        Label salle = new Label("Salle: " + cours.getId_salle());
        Label horaire = new Label(cours.getHeure_debut() + "-" + cours.getHeure_fin());

        contenu.getChildren().addAll(matiere, salle, horaire);

        // Ajouter l'enseignant si disponible
        String enseignantId = cours.getId_enseignant().trim();
        Enseignant enseignant = csvService.getEnseignantById(enseignantId);
        if (enseignant != null) {
            Label prof = new Label(enseignant.getNom() + " " + enseignant.getPrenom());
            contenu.getChildren().add(prof);
        }

        return contenu;
    }

    private Tooltip creerTooltipCours(Cours cours) {
        Tooltip tooltip = new Tooltip();
        StringBuilder sb = new StringBuilder();

        sb.append("Matière: ").append(cours.getMatiere()).append("\n");
        sb.append("Salle: ").append(cours.getId_salle()).append("\n");
        sb.append("Horaire: ").append(cours.getHeure_debut()).append("-").append(cours.getHeure_fin()).append("\n");

        String enseignantId = cours.getId_enseignant().trim();
        Enseignant enseignant = csvService.getEnseignantById(enseignantId);
        if (enseignant != null) {
            sb.append("Enseignant: ").append(enseignant.getNom()).append(" ").append(enseignant.getPrenom());
        }

        tooltip.setText(sb.toString());
        return tooltip;
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