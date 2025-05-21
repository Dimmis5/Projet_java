package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.projet_java.model.Cours;
import org.example.projet_java.service.CsvService;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EdtEnseignantController {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMAT_H = DateTimeFormatter.ofPattern("H'h'mm");
    private static final DateTimeFormatter TIME_FORMAT_COLON = DateTimeFormatter.ofPattern("H:mm");
    private static final DateTimeFormatter HEADER_DATE_FORMAT = DateTimeFormatter.ofPattern("E dd/MM");

    private final LocalTime heureDebut = LocalTime.of(8, 0);
    private final LocalTime heureFin = LocalTime.of(18, 0);
    private final int intervalMinutes = 60;

    private LocalDate dateDebutSemaine;
    private final CsvService csvService = CsvService.getInstance();
    private String idEnseignant;

    @FXML private GridPane grilleCalendrier;
    @FXML private Label etiquetteMoisAnnee;
    @FXML private Button boutonSemainePrecedente;
    @FXML private Button boutonSemaineSuivante;
    @FXML private Button boutonSemaineCourante;

    @FXML
    public void initialize() {
        System.out.println("[DEBUG] Initialisation du contrôleur EDT Enseignant");
        dateDebutSemaine = LocalDate.now().with(DayOfWeek.MONDAY);
        configurerBoutons();

        // Solution temporaire pour test - À remplacer par l'appel depuis votre code principal
        if (this.idEnseignant == null) {
            this.idEnseignant = "20000";
            System.out.println("[DEBUG] ID enseignant défini en dur pour test: " + this.idEnseignant);
        }

        afficherSemaine(dateDebutSemaine);
    }

    public void setIdEnseignant(String id) {
        if (id == null || id.isEmpty()) {
            System.err.println("[ERREUR] ID enseignant non valide");
            return;
        }

        this.idEnseignant = id.trim();
        System.out.println("[DEBUG] ID enseignant défini: " + this.idEnseignant);

        if (grilleCalendrier != null) {
            afficherSemaine(dateDebutSemaine);
        }
    }

    private void configurerBoutons() {
        boutonSemainePrecedente.setOnAction(e -> {
            System.out.println("[DEBUG] Clic sur Semaine précédente");
            changerSemaine(-1);
        });

        boutonSemaineSuivante.setOnAction(e -> {
            System.out.println("[DEBUG] Clic sur Semaine suivante");
            changerSemaine(1);
        });

        boutonSemaineCourante.setOnAction(e -> {
            System.out.println("[DEBUG] Clic sur Semaine actuelle");
            dateDebutSemaine = LocalDate.now().with(DayOfWeek.MONDAY);
            afficherSemaine(dateDebutSemaine);
        });
    }

    private void changerSemaine(int delta) {
        dateDebutSemaine = dateDebutSemaine.plusWeeks(delta);
        afficherSemaine(dateDebutSemaine);
    }

    private void afficherSemaine(LocalDate debutSemaine) {
        System.out.println("[DEBUG] Affichage semaine du " + debutSemaine.format(DATE_FORMAT));

        if (idEnseignant == null || idEnseignant.isEmpty()) {
            System.err.println("[ERREUR] Aucun ID enseignant défini !");
            showAlert("Erreur", "Aucun enseignant sélectionné", "Veuillez vous connecter");
            return;
        }

        grilleCalendrier.getChildren().clear();
        configurerGrille();
        afficherEnTetes(debutSemaine);

        List<Cours> cours = csvService.CoursEnseignant(idEnseignant);
        System.out.println("[DEBUG] Nombre de cours trouvés: " + cours.size());

        if (cours.isEmpty()) {
            System.out.println("[DEBUG] Aucun cours trouvé pour cet enseignant");
        }

        LocalDate finSemaine = debutSemaine.plusDays(6);
        cours.stream()
                .filter(c -> estDansSemaine(c, debutSemaine, finSemaine))
                .forEach(this::afficherCours);

        etiquetteMoisAnnee.setText("Semaine du " + debutSemaine.format(DATE_FORMAT) +
                " au " + finSemaine.format(DATE_FORMAT));
    }

    private boolean estDansSemaine(Cours cours, LocalDate debut, LocalDate fin) {
        try {
            // Debug: Afficher la date originale
            System.out.println("[DEBUG] Date du cours à parser: " + cours.getDate());

            LocalDate dateCours = LocalDate.parse(cours.getDate(), DATE_FORMAT);
            boolean dansSemaine = !dateCours.isBefore(debut) && !dateCours.isAfter(fin);

            System.out.println("[DEBUG] Cours " + cours.getMatiere() + " le " + dateCours +
                    " - Dans semaine: " + dansSemaine);
            return dansSemaine;
        } catch (DateTimeParseException e) {
            System.err.println("[ERREUR] Format de date invalide pour le cours: " + cours.getMatiere());
            System.err.println("Date problématique: '" + cours.getDate() + "'");
            System.err.println("Format attendu: dd/MM/yyyy");
            e.printStackTrace();
            return false;
        }
    }

    private void configurerGrille() {
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

    private void afficherEnTetes(LocalDate debutSemaine) {
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

    private void afficherCours(Cours cours) {
        try {
            System.out.println("\n[DEBUG] Tentative d'affichage du cours: " + cours.getMatiere());
            System.out.println("[DEBUG] Date du cours à parser: " + cours.getDate());

            LocalDate date = LocalDate.parse(cours.getDate(), DATE_FORMAT);
            LocalTime debut = parseHeure(cours.getHeure_debut());
            LocalTime fin = parseHeure(cours.getHeure_fin());

            int col = date.getDayOfWeek().getValue();
            int row = ((int) Duration.between(heureDebut, debut).toMinutes() / intervalMinutes) + 1;
            int span = ((int) Duration.between(debut, fin).toMinutes() / intervalMinutes);

            System.out.printf("[DEBUG] Positionnement - Colonne: %d, Ligne: %d, Span: %d%n", col, row, span);

            VBox box = new VBox(3);
            box.setStyle("-fx-background-color: #e3f2fd; -fx-border-color: #bbdefb; -fx-border-radius: 3; -fx-padding: 5;");
            box.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            Label lblMatiere = new Label(cours.getMatiere());
            lblMatiere.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");

            Label lblSalle = new Label("Salle: " + cours.getId_salle());
            lblSalle.setStyle("-fx-font-size: 11;");

            Label lblClasse = new Label("Classe: " + cours.getClasse());
            lblClasse.setStyle("-fx-font-size: 11;");

            Label lblHoraire = new Label(debut.format(DateTimeFormatter.ofPattern("HH:mm")) + "-" + fin.format(DateTimeFormatter.ofPattern("HH:mm")));
            lblHoraire.setStyle("-fx-font-size: 10; -fx-text-fill: #555;");

            box.getChildren().addAll(lblMatiere, lblSalle, lblClasse, lblHoraire);
            GridPane.setConstraints(box, col, row, 1, span);
            grilleCalendrier.getChildren().add(box);

        } catch (Exception e) {
            System.err.println("[ERREUR CRITIQUE] Impossible d'afficher le cours: " + cours.getMatiere());
            System.err.println("Date: " + cours.getDate());
            System.err.println("Heure début: " + cours.getHeure_debut());
            System.err.println("Heure fin: " + cours.getHeure_fin());
            e.printStackTrace();
        }
    }

    private LocalTime parseHeure(String heureStr) throws DateTimeParseException {
        // Supprimer les espaces et normaliser le format
        String heureNettoyee = heureStr.trim();

        try {
            // Essayer d'abord le format avec deux-points (10:00)
            return LocalTime.parse(heureNettoyee, TIME_FORMAT_COLON);
        } catch (DateTimeParseException e1) {
            try {
                // Si échec, essayer le format avec h (10h00)
                return LocalTime.parse(heureNettoyee, TIME_FORMAT_H);
            } catch (DateTimeParseException e2) {
                // Si les deux échouent, essayer le format HHmm (1000)
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