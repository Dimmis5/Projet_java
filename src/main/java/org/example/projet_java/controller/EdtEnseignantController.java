package org.example.projet_java.controller;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.projet_java.model.Cours;
import org.example.projet_java.service.CsvService;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

public class EdtEnseignantController {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMAT_H = DateTimeFormatter.ofPattern("H'h'mm");
    private static final DateTimeFormatter TIME_FORMAT_COLON = DateTimeFormatter.ofPattern("H:mm");
    private static final DateTimeFormatter HEADER_DATE_FORMAT = DateTimeFormatter.ofPattern("E dd/MM");
    private static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH);

    private final LocalTime heureDebut = LocalTime.of(8, 0);
    private final LocalTime heureFin = LocalTime.of(18, 0);
    private final int intervalMinutes = 60;

    private LocalDate dateCourante;
    private final CsvService csvService = CsvService.getInstance();
    private String idEnseignant;
    private String modeAffichage = "semaine"; // "jour", "semaine" ou "mois"

    @FXML private GridPane grilleCalendrier;
    @FXML private Label etiquetteMoisAnnee;
    @FXML private Button boutonPeriodePrecedente;
    @FXML private Button boutonPeriodeSuivante;
    @FXML private Button boutonAujourdhui;
    @FXML private Button boutonModeJour;
    @FXML private Button boutonModeSemaine;
    @FXML private Button boutonModeMois;


    @FXML
    public void initialize() {
        System.out.println("[DEBUG] Initialisation du contrôleur EDT Enseignant");
        dateCourante = LocalDate.now();
        configurerBoutons();
        initEnseignantDepuisCsv();
        rafraichirAffichage();
    }

    private void initEnseignantDepuisCsv() {
        try {
            List<String> idsEnseignants = csvService.getEnseignantIds();

            if (idsEnseignants != null && !idsEnseignants.isEmpty()) {
                this.idEnseignant = idsEnseignants.get(0);
                System.out.println("[DEBUG] ID enseignant récupéré depuis CSV: " + this.idEnseignant);
            } else {
                System.err.println("[ERREUR] Aucun enseignant trouvé dans le CSV");
                showAlert("Erreur", "Aucun enseignant disponible", "Aucun enseignant n'a été trouvé dans les données");
            }
        } catch (Exception e) {
            System.err.println("[ERREUR] Impossible de récupérer la liste des enseignants: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setIdEnseignant(String id) {
        if (id == null || id.isEmpty()) {
            System.err.println("[ERREUR] ID enseignant non valide");
            return;
        }

        this.idEnseignant = id.trim();
        System.out.println("[DEBUG] ID enseignant défini: " + this.idEnseignant);
        rafraichirAffichage();
    }

    private void configurerBoutons() {
        boutonPeriodePrecedente.setOnAction(e -> {
            System.out.println("[DEBUG] Clic sur Période précédente");
            changerPeriode(-1);
        });

        boutonPeriodeSuivante.setOnAction(e -> {
            System.out.println("[DEBUG] Clic sur Période suivante");
            changerPeriode(1);
        });

        boutonAujourdhui.setOnAction(e -> {
            System.out.println("[DEBUG] Clic sur Aujourd'hui");
            dateCourante = LocalDate.now();
            rafraichirAffichage();
        });

        boutonModeJour.setOnAction(e -> {
            modeAffichage = "jour";
            rafraichirAffichage();
            mettreAJourStyleBoutons();
        });

        boutonModeSemaine.setOnAction(e -> {
            modeAffichage = "semaine";
            rafraichirAffichage();
            mettreAJourStyleBoutons();
        });

        boutonModeMois.setOnAction(e -> {
            modeAffichage = "mois";
            rafraichirAffichage();
            mettreAJourStyleBoutons();
        });

    }

    private void mettreAJourStyleBoutons() {
        boutonModeJour.setStyle(modeAffichage.equals("jour") ? "-fx-background-color: #d0d0d0;" : "");
        boutonModeSemaine.setStyle(modeAffichage.equals("semaine") ? "-fx-background-color: #d0d0d0;" : "");
        boutonModeMois.setStyle(modeAffichage.equals("mois") ? "-fx-background-color: #d0d0d0;" : "");
    }

    private void changerPeriode(int delta) {
        switch (modeAffichage) {
            case "jour":
                dateCourante = dateCourante.plusDays(delta);
                break;
            case "semaine":
                dateCourante = dateCourante.plusWeeks(delta);
                break;
            case "mois":
                dateCourante = dateCourante.plusMonths(delta);
                break;
        }
        rafraichirAffichage();
    }

    private void rafraichirAffichage() {
        grilleCalendrier.getChildren().clear();

        switch (modeAffichage) {
            case "jour":
                afficherJour(dateCourante);
                break;
            case "semaine":
                afficherSemaine(dateCourante.with(DayOfWeek.MONDAY));
                break;
            case "mois":
                afficherMois(dateCourante.withDayOfMonth(1));
                break;
        }

        mettreAJourTitre();
    }

    private void mettreAJourTitre() {
        switch (modeAffichage) {
            case "jour":
                etiquetteMoisAnnee.setText(dateCourante.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH)));
                break;
            case "semaine":
                LocalDate debutSemaine = dateCourante.with(DayOfWeek.MONDAY);
                LocalDate finSemaine = debutSemaine.plusDays(6);
                etiquetteMoisAnnee.setText("Semaine du " + debutSemaine.format(DATE_FORMAT) +
                        " au " + finSemaine.format(DATE_FORMAT));
                break;
            case "mois":
                etiquetteMoisAnnee.setText(dateCourante.format(MONTH_FORMAT));
                break;
        }
    }

    private void afficherJour(LocalDate date) {
        configurerGrilleJour();

        List<Cours> cours = csvService.CoursEnseignant(idEnseignant);
        cours.stream()
                .filter(c -> estLeJour(c, date))
                .forEach(this::afficherCoursJour);
    }

    private void configurerGrilleJour() {
        grilleCalendrier.getColumnConstraints().clear();
        grilleCalendrier.getRowConstraints().clear();

        // Colonne heures
        ColumnConstraints colHeure = new ColumnConstraints(80);
        grilleCalendrier.getColumnConstraints().add(colHeure);

        // Colonne principale
        ColumnConstraints colPrincipale = new ColumnConstraints();
        colPrincipale.setHgrow(Priority.ALWAYS);
        grilleCalendrier.getColumnConstraints().add(colPrincipale);

        // Lignes créneaux
        int nbCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
        for (int i = 0; i < nbCreneaux; i++) {
            RowConstraints row = new RowConstraints(60);
            row.setVgrow(Priority.ALWAYS);
            grilleCalendrier.getRowConstraints().add(row);
        }

        // En-têtes heures
        LocalTime heure = heureDebut;
        int row = 0;
        while (!heure.isAfter(heureFin.minusMinutes(intervalMinutes))) {
            Label label = new Label(heure.format(TIME_FORMAT_H));
            label.setStyle("-fx-alignment: center-right; -fx-font-size: 11; -fx-background-color: #f0f0f0;");
            grilleCalendrier.add(label, 0, row);
            heure = heure.plusMinutes(intervalMinutes);
            row++;
        }
    }

    private void afficherCoursJour(Cours cours) {
        try {
            LocalTime debut = parseHeure(cours.getHeure_debut());
            LocalTime fin = parseHeure(cours.getHeure_fin());

            int row = ((int) Duration.between(heureDebut, debut).toMinutes() / intervalMinutes);
            int span = ((int) Duration.between(debut, fin).toMinutes() / intervalMinutes);

            VBox box = creerVBoxCours(cours);
            GridPane.setConstraints(box, 1, row, 1, span);
            grilleCalendrier.getChildren().add(box);

        } catch (Exception e) {
            System.err.println("[ERREUR] Impossible d'afficher le cours: " + cours.getMatiere());
            e.printStackTrace();
        }
    }

    private VBox creerVBoxCours(Cours cours) {
        VBox box = new VBox(3);
        box.setStyle("-fx-background-color: #e3f2fd; -fx-border-color: #bbdefb; -fx-border-radius: 3; -fx-padding: 5;");
        box.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Label lblMatiere = new Label(cours.getMatiere());
        lblMatiere.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");

        Label lblSalle = new Label("Salle: " + cours.getId_salle());
        lblSalle.setStyle("-fx-font-size: 11;");

        Label lblClasse = new Label("Classe: " + cours.getClasse());
        lblClasse.setStyle("-fx-font-size: 11;");

        Label lblHoraire = new Label(parseHeure(cours.getHeure_debut()).format(DateTimeFormatter.ofPattern("HH:mm")) +
                "-" + parseHeure(cours.getHeure_fin()).format(DateTimeFormatter.ofPattern("HH:mm")));
        lblHoraire.setStyle("-fx-font-size:10; -fx-text-fill: #555;");

        box.getChildren().addAll(lblMatiere, lblSalle, lblClasse, lblHoraire);
        return box;
    }

    private boolean estLeJour(Cours cours, LocalDate date) {
        try {
            LocalDate dateCours = LocalDate.parse(cours.getDate(), DATE_FORMAT);
            return dateCours.isEqual(date);
        } catch (DateTimeParseException e) {
            System.err.println("[ERREUR] Format de date invalide pour le cours: " + cours.getMatiere());
            return false;
        }
    }

    private void afficherSemaine(LocalDate debutSemaine) {
        configurerGrilleSemaine();
        afficherEnTetesSemaine(debutSemaine);

        List<Cours> cours = csvService.CoursEnseignant(idEnseignant);
        LocalDate finSemaine = debutSemaine.plusDays(6);

        cours.stream()
                .filter(c -> estDansPeriode(c, debutSemaine, finSemaine))
                .forEach(this::afficherCoursSemaine);
    }

    private void configurerGrilleSemaine() {
        grilleCalendrier.getColumnConstraints().clear();
        grilleCalendrier.getRowConstraints().clear();

        // Colonne heures
        ColumnConstraints colHeure = new ColumnConstraints(80);
        grilleCalendrier.getColumnConstraints().add(colHeure);

        // Colonnes jours
        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            grilleCalendrier.getColumnConstraints().add(col);
        }

        // Ligne en-tête
        RowConstraints rowHeader = new RowConstraints(30);
        grilleCalendrier.getRowConstraints().add(rowHeader);

        // Lignes créneaux
        int nbCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
        for (int i = 0; i < nbCreneaux; i++) {
            RowConstraints row = new RowConstraints(60);
            row.setVgrow(Priority.ALWAYS);
            grilleCalendrier.getRowConstraints().add(row);
        }
    }

    private void afficherEnTetesSemaine(LocalDate debutSemaine) {
        // En-têtes jours
        for (int i = 0; i < 7; i++) {
            LocalDate date = debutSemaine.plusDays(i);
            Label label = new Label(date.format(HEADER_DATE_FORMAT));
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            label.setStyle("-fx-alignment: center; -fx-font-weight: bold; -fx-background-color: #f0f0f0;");
            grilleCalendrier.add(label, i + 1, 0);
        }

        // En-têtes heures
        LocalTime heure = heureDebut;
        int row = 1;
        while (!heure.isAfter(heureFin.minusMinutes(intervalMinutes))) {
            Label label = new Label(heure.format(TIME_FORMAT_H));
            label.setStyle("-fx-alignment: center-right; -fx-font-size: 11; -fx-background-color: #f0f0f0;");
            grilleCalendrier.add(label, 0, row);
            heure = heure.plusMinutes(intervalMinutes);
            row++;
        }
    }

    private void afficherCoursSemaine(Cours cours) {
        try {
            LocalDate date = LocalDate.parse(cours.getDate(), DATE_FORMAT);
            LocalTime debut = parseHeure(cours.getHeure_debut());
            LocalTime fin = parseHeure(cours.getHeure_fin());

            int col = date.getDayOfWeek().getValue(); // Lundi=1 à Dimanche=7
            int row = ((int) Duration.between(heureDebut, debut).toMinutes() / intervalMinutes) + 1;
            int span = ((int) Duration.between(debut, fin).toMinutes() / intervalMinutes);

            VBox box = creerVBoxCours(cours);
            GridPane.setConstraints(box, col, row, 1, span);
            grilleCalendrier.getChildren().add(box);

        } catch (Exception e) {
            System.err.println("[ERREUR] Impossible d'afficher le cours: " + cours.getMatiere());
            e.printStackTrace();
        }
    }

    private void afficherMois(LocalDate debutMois) {
        configurerGrilleMois();
        afficherJoursMois(debutMois);

        List<Cours> cours = csvService.CoursEnseignant(idEnseignant);
        cours.stream()
                .filter(c -> estDansMois(c, debutMois))
                .forEach(this::afficherCoursMois);
    }

    private void configurerGrilleMois() {
        grilleCalendrier.getColumnConstraints().clear();
        grilleCalendrier.getRowConstraints().clear();

        // Colonnes jours (7 colonnes pour les jours de la semaine)
        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setMinWidth(80);
            grilleCalendrier.getColumnConstraints().add(col);
        }

        // Lignes (6 semaines max dans un mois)
        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints(80);
            row.setVgrow(Priority.ALWAYS);
            grilleCalendrier.getRowConstraints().add(row);
        }

        // En-têtes des jours
        for (int i = 0; i < 7; i++) {
            Label label = new Label(DayOfWeek.of((i % 7) + 1).getDisplayName(TextStyle.SHORT, Locale.FRENCH));
            label.setStyle("-fx-alignment: center; -fx-font-weight: bold; -fx-background-color: #f0f0f0;");
            grilleCalendrier.add(label, i, 0);
        }
    }

    private void afficherJoursMois(LocalDate debutMois) {
        LocalDate date = debutMois.withDayOfMonth(1);
        // Ajuster pour commencer par lundi
        while (date.getDayOfWeek() != DayOfWeek.MONDAY) {
            date = date.minusDays(1);
        }

        int row = 1;
        while (row < 6) {
            for (int col = 0; col < 7; col++) {
                if (date.getMonth() == debutMois.getMonth() || row == 1 || row == 5) {
                    Label label = new Label(String.valueOf(date.getDayOfMonth()));
                    label.setStyle(date.getMonth() == debutMois.getMonth()
                            ? "-fx-alignment: top-left; -fx-padding: 5 0 0 5;"
                            : "-fx-alignment: top-left; -fx-padding: 5 0 0 5; -fx-text-fill: #a0a0a0;");
                    label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    grilleCalendrier.add(label, col, row);
                }
                date = date.plusDays(1);
            }
            row++;
        }
    }

    private void afficherCoursMois(Cours cours) {
        try {
            LocalDate date = LocalDate.parse(cours.getDate(), DATE_FORMAT);
            int semaineDansMois = date.get(WeekFields.of(Locale.FRENCH).weekOfMonth()) - 1;
            int jourDansSemaine = date.getDayOfWeek().getValue() - 1;

            if (semaineDansMois >= 0 && semaineDansMois < 6) {
                Label label = new Label(cours.getMatiere() + "\n" +
                        parseHeure(cours.getHeure_debut()).format(DateTimeFormatter.ofPattern("HH:mm")));
                label.setStyle("-fx-alignment: top-left; -fx-font-size: 10; -fx-padding: 2;");
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                GridPane.setConstraints(label, jourDansSemaine, semaineDansMois + 1);
                grilleCalendrier.getChildren().add(label);
            }
        } catch (Exception e) {
            System.err.println("[ERREUR] Impossible d'afficher le cours en mode mois: " + cours.getMatiere());
            e.printStackTrace();
        }
    }

    private boolean estDansMois(Cours cours, LocalDate debutMois) {
        try {
            LocalDate dateCours = LocalDate.parse(cours.getDate(), DATE_FORMAT);
            return dateCours.getMonth() == debutMois.getMonth() &&
                    dateCours.getYear() == debutMois.getYear();
        } catch (DateTimeParseException e) {
            System.err.println("[ERREUR] Format de date invalide pour le cours: " + cours.getMatiere());
            return false;
        }
    }

    private boolean estDansPeriode(Cours cours, LocalDate debut, LocalDate fin) {
        try {
            LocalDate dateCours = LocalDate.parse(cours.getDate(), DATE_FORMAT);
            return !dateCours.isBefore(debut) && !dateCours.isAfter(fin);
        } catch (DateTimeParseException e) {
            System.err.println("[ERREUR] Format de date invalide pour le cours: " + cours.getMatiere());
            return false;
        }
    }

    private LocalTime parseHeure(String heureStr) throws DateTimeParseException {
        String heureNettoyee = heureStr.trim();

        try {
            return LocalTime.parse(heureNettoyee, TIME_FORMAT_COLON);
        } catch (DateTimeParseException e1) {
            try {
                return LocalTime.parse(heureNettoyee, TIME_FORMAT_H);
            } catch (DateTimeParseException e2) {
                try {
                    if (heureNettoyee.matches("\\d{3,4}")) {
                        if (heureNettoyee.length() == 3) {
                            heureNettoyee = "0" + heureNettoyee;
                        }
                        return LocalTime.parse(heureNettoyee.substring(0, 2) + ":" + heureNettoyee.substring(2),
                                TIME_FORMAT_COLON);
                    }
                    throw e2;
                } catch (DateTimeParseException e3) {
                    System.err.println("[ERREUR] Format d'heure invalide: " + heureStr);
                    throw new DateTimeParseException("Format d'heure invalide (attendu HH:mm ou Hhmm)", heureStr, 0);
                }
            }
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}