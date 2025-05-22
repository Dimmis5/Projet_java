package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
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

    private final LocalTime heureDebut = LocalTime.of(8, 0);
    private final LocalTime heureFin = LocalTime.of(19, 0);
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

    @FXML
    public void initialize() {
        configurerControlesCalendrier();
        dateCourante = LocalDate.now();
        dateDebutSemaineCourante = dateCourante.with(DayOfWeek.MONDAY);
        premierJourMoisCourant = dateCourante.withDayOfMonth(1);

        boutonVueJour.getStyleClass().add("button-normal");
        boutonVueSemaine.getStyleClass().add("button-normal");
        boutonVueMois.getStyleClass().add("button-normal");
        boutonVueSemaine.getStyleClass().add("button-active");

        boutonVueJour.setOnAction(e -> changerModeAffichage(ModeAffichage.JOUR));
        boutonVueSemaine.setOnAction(e -> changerModeAffichage(ModeAffichage.SEMAINE));
        boutonVueMois.setOnAction(e -> changerModeAffichage(ModeAffichage.MOIS));

        grilleCalendrier.getStyleClass().add("calendar-grid");
        afficherVueSemaine();
    }

    public void setIdEtudiantConnecte(String idEtudiant) {
        this.idEtudiantConnecte = idEtudiant;
        this.coursDeLEtudiant = csvService.CoursEtudiant(idEtudiantConnecte);
        rafraichirAffichage();
    }

    private void changerModeAffichage(ModeAffichage nouveauMode) {
        modeAffichage = nouveauMode;

        boutonVueJour.getStyleClass().remove("button-active");
        boutonVueSemaine.getStyleClass().remove("button-active");
        boutonVueMois.getStyleClass().remove("button-active");

        switch (modeAffichage) {
            case JOUR: boutonVueJour.getStyleClass().add("button-active"); break;
            case SEMAINE: boutonVueSemaine.getStyleClass().add("button-active"); break;
            case MOIS: boutonVueMois.getStyleClass().add("button-active"); break;
        }

        rafraichirAffichage();
    }

    private void rafraichirAffichage() {
        switch (modeAffichage) {
            case JOUR: afficherVueJour(dateCourante); break;
            case SEMAINE: afficherVueSemaine(); break;
            case MOIS: afficherVueMois(); break;
        }
    }

    private void afficherVueJour(LocalDate date) {
        grilleCalendrier.getChildren().clear();
        configurerDispositionCalendrierJour();
        mettreAJourEtiquetteDate(date.format(FORMAT_DATE));

        ajouterEnTetesHeures();
        creerGrilleVideJour();

        List<Cours> coursDuJour = filtrerCoursParDate(date);
        afficherCoursDansGrilleJour(coursDuJour);
    }

    private void configurerDispositionCalendrierJour() {
        grilleCalendrier.getColumnConstraints().clear();
        grilleCalendrier.getRowConstraints().clear();

        ColumnConstraints colHeures = new ColumnConstraints(80);
        colHeures.setHgrow(Priority.NEVER);

        ColumnConstraints colPrincipale = new ColumnConstraints();
        colPrincipale.setHgrow(Priority.ALWAYS);
        colPrincipale.setMinWidth(300);

        grilleCalendrier.getColumnConstraints().addAll(colHeures, colPrincipale);

        int nombreCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;

        for (int i = 0; i < nombreCreneaux; i++) {
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(80);
            row.setMinHeight(80);
            row.setMaxHeight(80);
            row.setVgrow(Priority.NEVER);
            grilleCalendrier.getRowConstraints().add(row);
        }
    }

    private void creerGrilleVideJour() {
        int nombreCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;

        for (int ligne = 0; ligne < nombreCreneaux; ligne++) {
            Pane celluleVide = new Pane();
            celluleVide.getStyleClass().add("calendar-cell");
            celluleVide.setPrefHeight(80);
            celluleVide.setMaxHeight(80);
            celluleVide.setMinHeight(80);
            GridPane.setRowIndex(celluleVide, ligne);
            GridPane.setColumnIndex(celluleVide, 1);
            GridPane.setFillHeight(celluleVide, true);
            GridPane.setFillWidth(celluleVide, true);
            grilleCalendrier.getChildren().add(celluleVide);
        }
    }

    private void afficherCoursDansGrilleJour(List<Cours> cours) {
        if (cours == null) return;

        cours.sort(Comparator.comparing(c -> parseHeure(c.getHeure_debut())));

        for (Cours c : cours) {
            try {
                LocalTime heureDebutCours = parseHeure(c.getHeure_debut());
                LocalTime heureFinCours = parseHeure(c.getHeure_fin());

                if (heureDebutCours.isBefore(heureDebut) || heureDebutCours.isAfter(heureFin.minusMinutes(intervalMinutes))) {
                    continue;
                }

                int ligneDebut = calculerLigneHoraire(heureDebutCours);
                int hauteur = calculerDureeEnLignes(heureDebutCours, heureFinCours);

                if (ligneDebut < 0) {
                    ligneDebut = 0;
                }

                VBox contenuCours = creerContenuCoursDetaille(c);
                StackPane celluleCours = creerCelluleCoursAvecCadre(contenuCours, c);

                celluleCours.setPrefHeight(hauteur * 80);
                celluleCours.setMaxHeight(hauteur * 80);
                celluleCours.setMinHeight(hauteur * 80);

                GridPane.setRowIndex(celluleCours, ligneDebut);
                GridPane.setColumnIndex(celluleCours, 1);
                GridPane.setRowSpan(celluleCours, hauteur);
                GridPane.setFillHeight(celluleCours, true);
                GridPane.setFillWidth(celluleCours, true);
                GridPane.setHalignment(celluleCours, HPos.CENTER);
                GridPane.setValignment(celluleCours, VPos.CENTER);

                grilleCalendrier.getChildren().add(celluleCours);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void afficherVueSemaine() {
        grilleCalendrier.getChildren().clear();
        configurerDispositionCalendrierSemaine();
        mettreAJourEtiquetteDate("Semaine du " + dateDebutSemaineCourante.format(FORMAT_DATE) + " au " + dateDebutSemaineCourante.plusDays(6).format(FORMAT_DATE));

        ajouterEnTetesJours(dateDebutSemaineCourante);
        ajouterEnTetesHeuresSemaine();
        creerGrilleVide();
        afficherCoursDansGrilleSemaine();
    }

    private void configurerDispositionCalendrierSemaine() {
        grilleCalendrier.getChildren().clear();
        grilleCalendrier.getColumnConstraints().clear();
        grilleCalendrier.getRowConstraints().clear();

        ColumnConstraints colHeure = new ColumnConstraints(100);
        colHeure.setHgrow(Priority.NEVER);
        grilleCalendrier.getColumnConstraints().add(colHeure);

        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setMinWidth(120);
            col.setMaxWidth(Double.MAX_VALUE);
            grilleCalendrier.getColumnConstraints().add(col);
        }

        RowConstraints rowHeader = new RowConstraints(35);
        rowHeader.setVgrow(Priority.NEVER);
        grilleCalendrier.getRowConstraints().add(rowHeader);

        int nbCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
        for (int i = 0; i < nbCreneaux; i++) {
            RowConstraints row = new RowConstraints(100);
            row.setVgrow(Priority.NEVER);
            row.setMinHeight(100);
            row.setMaxHeight(100);
            grilleCalendrier.getRowConstraints().add(row);
        }

        for (int row = 1; row <= nbCreneaux; row++) {
            for (int col = 1; col <= 7; col++) {
                Region celluleVide = new Region();
                celluleVide.getStyleClass().add("calendar-cell");
                celluleVide.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                celluleVide.setMinSize(0, 0);
                grilleCalendrier.add(celluleVide, col, row);
            }
        }
    }

    private void creerGrilleVide() {
        int nombreCreneaux = 11;
        for (int ligne = 1; ligne <= nombreCreneaux; ligne++) {
            for (int col = 1; col <= 7; col++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("calendar-cell");
                grilleCalendrier.add(pane, col, ligne);
            }
        }
    }

    private void afficherCoursDansGrilleSemaine() {
        LocalDate finSemaine = dateDebutSemaineCourante.plusDays(6);
        List<Cours> coursSemaine = filtrerCoursParPeriode(dateDebutSemaineCourante, finSemaine);
        if (coursSemaine == null) return;

        Map<LocalDate, List<Cours>> coursParJour = new HashMap<>();

        for (Cours cours : coursSemaine) {
            LocalDate dateCours = LocalDate.parse(cours.getDate(), FORMAT_DATE);
            coursParJour.computeIfAbsent(dateCours, k -> new ArrayList<>()).add(cours);
        }

        coursParJour.forEach((date, coursList) -> {
            coursList.sort(Comparator.comparing(c -> parseHeure(c.getHeure_debut())));

            Map<Integer, Boolean> creneauxOccupes = new HashMap<>();
            int nombreCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
            for (int i = 0; i < nombreCreneaux; i++) {
                creneauxOccupes.put(i, false);
            }

            for (Cours cours : coursList) {
                try {
                    LocalTime heureDebutCours = parseHeure(cours.getHeure_debut());
                    LocalTime heureFinCours = parseHeure(cours.getHeure_fin());

                    int ligneDebut = calculerLigneHoraire(heureDebutCours);
                    int hauteur = calculerDureeEnLignes(heureDebutCours, heureFinCours);

                    boolean creneauLibre = true;
                    for (int i = ligneDebut; i < ligneDebut + hauteur; i++) {
                        if (creneauxOccupes.getOrDefault(i, false)) {
                            creneauLibre = false;
                            break;
                        }
                    }

                    if (creneauLibre) {
                        for (int i = ligneDebut; i < ligneDebut + hauteur; i++) {
                            creneauxOccupes.put(i, true);
                        }

                        int colonne = date.getDayOfWeek().getValue();

                        VBox contenuCours = creerContenuCours(cours);
                        StackPane celluleCours = creerCelluleCoursAvecCadre(contenuCours, cours);

                        GridPane.setRowIndex(celluleCours, ligneDebut + 1);
                        GridPane.setColumnIndex(celluleCours, colonne);
                        GridPane.setRowSpan(celluleCours, hauteur);

                        grilleCalendrier.getChildren().add(celluleCours);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void afficherVueMois() {
        grilleCalendrier.getChildren().clear();
        configurerDispositionCalendrierMois();
        mettreAJourEtiquetteDate(premierJourMoisCourant.format(FORMAT_MOIS_ANNEE));

        String[] joursSemaine = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        for (int i = 0; i < 7; i++) {
            Label label = new Label(joursSemaine[i]);
            label.getStyleClass().add("calendar-header");
            grilleCalendrier.add(label, i, 0);
        }

        LocalDate dateCourante = premierJourMoisCourant.with(DayOfWeek.MONDAY);
        if (premierJourMoisCourant.getDayOfWeek() != DayOfWeek.MONDAY) {
            dateCourante = premierJourMoisCourant.minusDays(premierJourMoisCourant.getDayOfWeek().getValue() - 1);
        }

        int ligne = 1;
        LocalDate dernierJour = premierJourMoisCourant.withDayOfMonth(premierJourMoisCourant.lengthOfMonth());

        while (dateCourante.isBefore(dernierJour.plusDays(1)) || ligne <= 6) {
            for (int col = 0; col < 7; col++) {
                VBox cellule = new VBox(2);
                cellule.getStyleClass().add("month-day");
                cellule.setAlignment(Pos.TOP_CENTER);
                cellule.setPadding(new Insets(5));

                Label labelJour = new Label(String.valueOf(dateCourante.getDayOfMonth()));
                if (dateCourante.getMonth() == premierJourMoisCourant.getMonth()) {
                    labelJour.getStyleClass().add("month-day-number");
                } else {
                    labelJour.getStyleClass().add("month-day-number-other-month");
                }

                cellule.getChildren().add(labelJour);
                afficherCoursDansCelluleMois(cellule, dateCourante);

                grilleCalendrier.add(cellule, col, ligne);
                dateCourante = dateCourante.plusDays(1);
            }
            ligne++;
        }
    }

    private void afficherCoursDansCelluleMois(VBox cellule, LocalDate date) {
        List<Cours> coursDuJour = filtrerCoursParDate(date);
        if (coursDuJour == null || coursDuJour.isEmpty()) return;

        for (Cours cours : coursDuJour) {
            try {
                Label labelCours = new Label(cours.getMatiere());
                labelCours.getStyleClass().add("month-course");

                int colorIndex = Math.abs(cours.getMatiere().hashCode()) % 6 + 1;
                labelCours.getStyleClass().add("course-color-" + colorIndex);

                if (cours.isAnnulation()) {
                    labelCours.getStyleClass().add("course-box-cancelled");
                }

                labelCours.setTooltip(creerTooltipCours(cours));
                cellule.getChildren().add(labelCours);
            } catch (Exception e) {
                return;
            }
        }
    }

    private LocalTime parseHeure(String heureStr) {
        return LocalTime.parse(heureStr.replace("h", ":"), FORMAT_HEURE);
    }

    private int calculerLigneHoraire(LocalTime heure) {
        return (int) Duration.between(heureDebut, heure).toMinutes() / intervalMinutes;
    }

    private int calculerDureeEnLignes(LocalTime debut, LocalTime fin) {
        return (int) Math.ceil(Duration.between(debut, fin).toMinutes() / (double) intervalMinutes);
    }

    private StackPane creerCelluleCoursAvecCadre(VBox contenuCours, Cours cours) {
        StackPane cellule = new StackPane(contenuCours);
        cellule.getStyleClass().add("course-box");

        int colorIndex = Math.abs(cours.getMatiere().hashCode()) % 6 + 1;
        cellule.getStyleClass().add("course-color-" + colorIndex);

        if (cours.isAnnulation()) {
            cellule.getStyleClass().add("course-box-cancelled");
        }

        cellule.setAlignment(Pos.CENTER);
        return cellule;
    }

    private VBox creerContenuCours(Cours cours) {
        VBox contenu = new VBox(2);
        contenu.setAlignment(Pos.CENTER);
        contenu.setPadding(new Insets(3));

        Label matiere = new Label(cours.getMatiere());
        matiere.getStyleClass().add("course-title");

        Label salle = new Label("Salle: " + cours.getId_salle());
        salle.getStyleClass().add("course-detail");

        Label horaire = new Label(cours.getHeure_debut() + "-" + cours.getHeure_fin());
        horaire.getStyleClass().add("course-detail");

        contenu.getChildren().addAll(matiere, salle, horaire);

        if (cours.isAnnulation()) {
            Label annuleLabel = new Label("ANNULÉ");
            annuleLabel.getStyleClass().add("annuleLabel");
            contenu.getChildren().add(annuleLabel);
        }

        Enseignant enseignant = csvService.getEnseignantById(cours.getId_enseignant().trim());
        if (enseignant != null) {
            Label prof = new Label(enseignant.getNom() + " " + enseignant.getPrenom());
            prof.getStyleClass().add("course-detail");
            contenu.getChildren().add(prof);
        }

        return contenu;
    }

    private VBox creerContenuCoursDetaille(Cours cours) {
        VBox contenu = new VBox(5);
        contenu.setAlignment(Pos.CENTER_LEFT);
        contenu.setPadding(new Insets(8));

        Label matiere = new Label(cours.getMatiere());
        matiere.getStyleClass().add("course-title");

        Label salle = new Label("Salle: " + cours.getId_salle());
        salle.getStyleClass().add("course-detail");

        Label horaire = new Label("🕐 " + cours.getHeure_debut() + " - " + cours.getHeure_fin());
        horaire.getStyleClass().add("course-detail");

        contenu.getChildren().addAll(matiere, salle, horaire);

        if (cours.isAnnulation()) {
            Label annuleLabel = new Label("ANNULÉ");
            annuleLabel.getStyleClass().add("annuleLabel");
            contenu.getChildren().add(annuleLabel);
        }

        Enseignant enseignant = csvService.getEnseignantById(cours.getId_enseignant().trim());
        if (enseignant != null) {
            Label prof = new Label(" " + enseignant.getNom() + " " + enseignant.getPrenom());
            prof.getStyleClass().add("course-detail");
            contenu.getChildren().add(prof);
        }

        return contenu;
    }

    private Tooltip creerTooltipCours(Cours cours) {
        Tooltip tooltip = new Tooltip();
        StringBuilder sb = new StringBuilder();

        sb.append("Matière : ").append(cours.getMatiere()).append("\n");
        sb.append("Salle : ").append(cours.getId_salle()).append("\n");
        sb.append("Horaire : ").append(cours.getHeure_debut()).append("-").append(cours.getHeure_fin()).append("\n");

        if (cours.isAnnulation()) {
            sb.append("STATUT: ANNULÉ\n");
        }

        String enseignantId = cours.getId_enseignant().trim();
        Enseignant enseignant = csvService.getEnseignantById(enseignantId);
        if (enseignant != null) {
            sb.append("Enseignant: ").append(enseignant.getNom()).append(" ").append(enseignant.getPrenom());
        }

        tooltip.setText(sb.toString());
        return tooltip;
    }

    private void ajouterEnTetesJours(LocalDate debutSemaine) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd/MM");
        for (int i = 0; i < 7; i++) {
            LocalDate date = debutSemaine.plusDays(i);
            Label label = new Label(date.format(formatter));
            label.getStyleClass().add("calendar-header");
            grilleCalendrier.add(label, i + 1, 0);
        }
    }

    private void ajouterEnTetesHeures() {
        LocalTime heure = heureDebut;
        int ligne = 0;
        while (heure.isBefore(heureFin)) {
            Label label = new Label(heure.format(FORMAT_HEURE));
            label.getStyleClass().add("time-header");
            GridPane.setRowIndex(label, ligne);
            GridPane.setColumnIndex(label, 0);
            grilleCalendrier.getChildren().add(label);

            heure = heure.plusMinutes(intervalMinutes);
            ligne++;
        }
    }

    private void ajouterEnTetesHeuresSemaine() {
        LocalTime[] heures = {
                LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 0),
                LocalTime.of(11, 0), LocalTime.of(12, 0), LocalTime.of(13, 0),
                LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0),
                LocalTime.of(17, 0), LocalTime.of(18, 0)
        };

        for (int i = 0; i < heures.length; i++) {
            Label label = new Label(heures[i].format(FORMAT_HEURE));
            label.getStyleClass().add("time-header");
            grilleCalendrier.add(label, 0, i + 1);
        }
    }

    private void configurerDispositionCalendrierMois() {
        grilleCalendrier.getColumnConstraints().clear();
        grilleCalendrier.getRowConstraints().clear();

        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            grilleCalendrier.getColumnConstraints().add(col);
        }

        RowConstraints rowHeader = new RowConstraints(30);
        grilleCalendrier.getRowConstraints().add(rowHeader);

        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints(100);
            row.setVgrow(Priority.ALWAYS);
            grilleCalendrier.getRowConstraints().add(row);
        }
    }

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
            case JOUR: dateCourante = dateCourante.plusDays(decalage); break;
            case SEMAINE: dateDebutSemaineCourante = dateDebutSemaineCourante.plusWeeks(decalage); break;
            case MOIS: premierJourMoisCourant = premierJourMoisCourant.plusMonths(decalage); break;
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
                if (LocalDate.parse(c.getDate(), FORMAT_DATE).equals(date)) {
                    result.add(c);
                }
            } catch (Exception e) {
                return null;
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
                return null;
            }
        }
        return result;
    }
}