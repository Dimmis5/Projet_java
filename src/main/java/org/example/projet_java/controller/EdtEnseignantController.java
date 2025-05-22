package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.projet_java.model.Cours;
import org.example.projet_java.service.CsvService;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.List;

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
    private String modeAffichage = "semaine";

    // Couleurs pour différencier les matières
    private final Map<String, String> classesCouleursMatiere = new HashMap<>();
    private final String[] classesCouleurs = {
            "course-color-1",
            "course-color-2",
            "course-color-3",
            "course-color-4",
            "course-color-5",
            "course-color-6"
    };

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
        initialiserCouleurs();
        rafraichirAffichage();
    }

    private void initialiserCouleurs() {
        List<Cours> tousLesCours = csvService.CoursEnseignant(idEnseignant);
        Set<String> matieres = new HashSet<>();
        for (Cours cours : tousLesCours) {
            matieres.add(cours.getMatiere());
        }

        int index = 0;
        for (String matiere : matieres) {
            classesCouleursMatiere.put(matiere, classesCouleurs[index % classesCouleurs.length]);
            index++;
        }
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
        initialiserCouleurs();
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

        mettreAJourStyleBoutons();
    }

    private void mettreAJourStyleBoutons() {
        boutonModeJour.getStyleClass().removeAll("button-active", "button-normal");
        boutonModeSemaine.getStyleClass().removeAll("button-active", "button-normal");
        boutonModeMois.getStyleClass().removeAll("button-active", "button-normal");

        boutonModeJour.getStyleClass().add(modeAffichage.equals("jour") ? "button-active" : "button-normal");
        boutonModeSemaine.getStyleClass().add(modeAffichage.equals("semaine") ? "button-active" : "button-normal");
        boutonModeMois.getStyleClass().add(modeAffichage.equals("mois") ? "button-active" : "button-normal");
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

    private void afficherSemaine(LocalDate debutSemaine) {
        configurerGrilleSemaine();
        afficherEnTetesSemaine(debutSemaine);

        List<Cours> cours = csvService.CoursEnseignant(idEnseignant);
        LocalDate finSemaine = debutSemaine.plusDays(6);

        Map<String, List<Cours>> coursParJourEtHeure = new HashMap<>();

        cours.stream()
                .filter(c -> estDansPeriode(c, debutSemaine, finSemaine))
                .forEach(c -> {
                    String cle = c.getDate() + "_" + c.getHeure_debut();
                    coursParJourEtHeure.computeIfAbsent(cle, k -> new ArrayList<>()).add(c);
                });

        for (List<Cours> coursGroupe : coursParJourEtHeure.values()) {
            for (int i = 0; i < coursGroupe.size(); i++) {
                afficherCoursSemaine(coursGroupe.get(i), i, coursGroupe.size());
            }
        }
    }

    private void configurerGrilleSemaine() {
        grilleCalendrier.getChildren().clear();
        grilleCalendrier.getColumnConstraints().clear();
        grilleCalendrier.getRowConstraints().clear();

        grilleCalendrier.getStyleClass().add("calendar-grid");
        grilleCalendrier.setGridLinesVisible(true);

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

    private void afficherEnTetesSemaine(LocalDate debutSemaine) {
        for (int i = 0; i < 7; i++) {
            LocalDate date = debutSemaine.plusDays(i);
            String jourTexte = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.FRENCH) + " " +
                    date.format(DateTimeFormatter.ofPattern("dd/MM"));

            Label label = new Label(jourTexte);
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            label.getStyleClass().add("calendar-header");
            grilleCalendrier.add(label, i + 1, 0);
        }

        LocalTime heure = heureDebut;
        int row = 1;
        while (!heure.isAfter(heureFin.minusMinutes(intervalMinutes))) {
            Label label = new Label(heure.format(DateTimeFormatter.ofPattern("HH:mm")));
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            label.getStyleClass().add("time-header");
            grilleCalendrier.add(label, 0, row);
            heure = heure.plusMinutes(intervalMinutes);
            row++;
        }
    }

    private void afficherCoursSemaine(Cours cours, int indexChevauchement, int nbChevauchements) {
        try {
            LocalDate date = LocalDate.parse(cours.getDate(), DATE_FORMAT);
            LocalTime debut = parseHeure(cours.getHeure_debut());
            LocalTime fin = parseHeure(cours.getHeure_fin());

            int col = date.getDayOfWeek().getValue();
            int row = ((int) Duration.between(heureDebut, debut).toMinutes() / intervalMinutes) + 1;
            int span = Math.max(1, (int) Duration.between(debut, fin).toMinutes() / intervalMinutes);

            int maxRow = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
            if (row + span > maxRow + 1) {
                span = maxRow + 1 - row;
            }

            VBox box = creerVBoxCours(cours);

            if (nbChevauchements > 1) {
                HBox container = new HBox();
                container.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                for (int i = 0; i < indexChevauchement; i++) {
                    Region spacer = new Region();
                    spacer.setPrefWidth(20);
                    container.getChildren().add(spacer);
                }

                container.getChildren().add(box);

                GridPane.setConstraints(container, col, row, 1, span);
                GridPane.setFillHeight(container, true);
                GridPane.setFillWidth(container, true);
                grilleCalendrier.getChildren().add(container);
            } else {
                GridPane.setConstraints(box, col, row, 1, span);
                GridPane.setFillHeight(box, true);
                GridPane.setFillWidth(box, true);
                grilleCalendrier.getChildren().add(box);
            }

        } catch (Exception e) {
            System.err.println("[ERREUR] Impossible d'afficher le cours: " + cours.getMatiere());
            e.printStackTrace();
        }
    }

    private VBox creerVBoxCours(Cours cours) {
        VBox box = new VBox(2);
        box.getStyleClass().addAll("course-box", classesCouleursMatiere.getOrDefault(cours.getMatiere(), "course-color-1"));
        box.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        box.setMinSize(0, 0);

        Label lblMatiere = new Label(cours.getMatiere());
        lblMatiere.getStyleClass().add("course-title");
        lblMatiere.setWrapText(true);

        Label lblSalle = new Label("Salle: " + cours.getId_salle());
        lblSalle.getStyleClass().add("course-detail");
        lblSalle.setWrapText(true);

        Label lblHoraire = new Label(parseHeure(cours.getHeure_debut()).format(DateTimeFormatter.ofPattern("HH:mm")) +
                "-" + parseHeure(cours.getHeure_fin()).format(DateTimeFormatter.ofPattern("HH:mm")));
        lblHoraire.getStyleClass().add("course-detail");

        Label lblClasse = new Label(cours.getClasse());
        lblClasse.getStyleClass().add("course-detail");
        lblClasse.setWrapText(true);

        box.getChildren().addAll(lblMatiere, lblSalle, lblHoraire, lblClasse);
        box.setFillWidth(true);

        return box;
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

        grilleCalendrier.getStyleClass().add("calendar-grid");
        grilleCalendrier.setGridLinesVisible(true);

        ColumnConstraints colHeure = new ColumnConstraints(100);
        grilleCalendrier.getColumnConstraints().add(colHeure);

        ColumnConstraints colPrincipale = new ColumnConstraints();
        colPrincipale.setHgrow(Priority.ALWAYS);
        grilleCalendrier.getColumnConstraints().add(colPrincipale);

        int nbCreneaux = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
        for (int i = 0; i < nbCreneaux; i++) {
            RowConstraints row = new RowConstraints(100);
            row.setVgrow(Priority.ALWAYS);
            grilleCalendrier.getRowConstraints().add(row);
        }

        LocalTime heure = heureDebut;
        int row = 0;
        while (!heure.isAfter(heureFin.minusMinutes(intervalMinutes))) {
            Label label = new Label(heure.format(TIME_FORMAT_H));
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            label.getStyleClass().add("time-header");
            grilleCalendrier.add(label, 0, row);

            Region celluleVide = new Region();
            celluleVide.getStyleClass().add("calendar-cell");
            celluleVide.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            grilleCalendrier.add(celluleVide, 1, row);

            heure = heure.plusMinutes(intervalMinutes);
            row++;
        }
    }

    private void afficherCoursJour(Cours cours) {
        try {
            LocalTime debut = parseHeure(cours.getHeure_debut());
            LocalTime fin = parseHeure(cours.getHeure_fin());

            int row = ((int) Duration.between(heureDebut, debut).toMinutes() / intervalMinutes);
            int span = Math.max(1, (int) Duration.between(debut, fin).toMinutes() / intervalMinutes);
            int maxRow = (int) Duration.between(heureDebut, heureFin).toMinutes() / intervalMinutes;
            if (row + span > maxRow) {
                span = maxRow - row;
            }

            VBox box = creerVBoxCours(cours);

            GridPane.setConstraints(box, 1, row, 1, span);
            GridPane.setFillHeight(box, true);
            GridPane.setFillWidth(box, true);
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
        cours.stream().filter(c -> estDansMois(c, debutMois)).forEach(this::afficherCoursMois);
    }

    private void configurerGrilleMois() {
        grilleCalendrier.getColumnConstraints().clear();
        grilleCalendrier.getRowConstraints().clear();
        grilleCalendrier.getStyleClass().add("calendar-grid");
        grilleCalendrier.setGridLinesVisible(true);

        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setMinWidth(100);
            grilleCalendrier.getColumnConstraints().add(col);
        }

        RowConstraints rowHeader = new RowConstraints(30);
        grilleCalendrier.getRowConstraints().add(rowHeader);

        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints(80);
            row.setVgrow(Priority.ALWAYS);
            grilleCalendrier.getRowConstraints().add(row);
        }

        String[] joursAbrev = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        for (int i = 0; i < 7; i++) {
            Label label = new Label(joursAbrev[i]);
            label.getStyleClass().add("calendar-header");
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            grilleCalendrier.add(label, i, 0);
        }
    }

    private void afficherJoursMois(LocalDate debutMois) {
        LocalDate date = debutMois.withDayOfMonth(1);
        while (date.getDayOfWeek() != DayOfWeek.MONDAY) {
            date = date.minusDays(1);
        }

        int row = 1;
        while (row <= 6) {
            for (int col = 0; col < 7; col++) {
                VBox cellule = new VBox();
                cellule.getStyleClass().add("month-day");
                cellule.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                Label numeroJour = new Label(String.valueOf(date.getDayOfMonth()));
                if (date.getMonth() == debutMois.getMonth()) {
                    numeroJour.getStyleClass().add("month-day-number");
                } else {
                    numeroJour.getStyleClass().add("month-day-number-other-month");
                }

                cellule.getChildren().add(numeroJour);
                grilleCalendrier.add(cellule, col, row);

                date = date.plusDays(1);
            }
            row++;
            if (date.getMonth() != debutMois.getMonth() && row > 2) {
                break;
            }
        }
    }

    private void afficherCoursMois(Cours cours) {
        try {
            LocalDate date = LocalDate.parse(cours.getDate(), DATE_FORMAT);
            LocalDate premierDuMois = dateCourante.withDayOfMonth(1);
            LocalDate debutCalendrier = premierDuMois;
            while (debutCalendrier.getDayOfWeek() != DayOfWeek.MONDAY) {
                debutCalendrier = debutCalendrier.minusDays(1);
            }

            long joursDepuisDebut = Duration.between(debutCalendrier.atStartOfDay(), date.atStartOfDay()).toDays();
            int col = (int) (joursDepuisDebut % 7);
            int row = (int) (joursDepuisDebut / 7) + 1;

            if (row <= 6) {
                Label labelCours = new Label(cours.getMatiere());
                labelCours.getStyleClass().addAll("month-course",
                        classesCouleursMatiere.getOrDefault(cours.getMatiere(), "course-color-1"));

                grilleCalendrier.getChildren().stream()
                        .filter(node -> GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
                        .findFirst()
                        .ifPresent(node -> {
                            if (node instanceof VBox) {
                                ((VBox) node).getChildren().add(labelCours);
                            }
                        });
            }
        } catch (Exception e) {
            System.err.println("[ERREUR] Impossible d'afficher le cours en mode mois: " + cours.getMatiere());
            e.printStackTrace();
        }
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