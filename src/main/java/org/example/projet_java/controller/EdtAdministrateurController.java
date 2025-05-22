package org.example.projet_java.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.scene.Scene;
import java.util.Comparator;

import javafx.stage.Stage;
import org.example.projet_java.model.*;
import org.example.projet_java.service.CsvService;
import org.w3c.dom.Text;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EdtAdministrateurController implements Initializable {

    // UI Elements
    @FXML private ScrollPane scrollPane;
    @FXML private BorderPane mainContainer;
    @FXML private VBox infoContainer;
    @FXML private Label titreLabel;
    @FXML private VBox detailsContainer;
    @FXML private VBox contentContainer;
    @FXML private Label edtLabel;

    @FXML private HBox etudiantSection;
    @FXML private ComboBox<String> etudiantComboBox;
    @FXML private Button btnListeEtudiants;
    @FXML private VBox etudiantInfoContainer;

    @FXML private HBox enseignantSection;
    @FXML private ComboBox<String> enseignantComboBox;
    @FXML private Button btnListeEnseignants;
    @FXML private VBox enseignantInfoContainer;

    @FXML private VBox edtContainer;
    @FXML private VBox edtEnseignantContainer;

    private Administrateur administrateur;
    private List<Etudiant> listeEtudiants = new ArrayList<>();
    private List<Enseignant> listeEnseignants = new ArrayList<>();
    private final CsvService csvService = CsvService.getInstance();

    private static final String STYLE_BOLD_LABEL = "-fx-font-weight: bold;";
    private static final String STYLE_POPUP = "-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);";
    private static final String STYLE_BUTTON_PRIMARY = "-fx-background-color: #4a87e8; -fx-text-fill: white;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listeEtudiants = csvService.Etudiants();
        listeEnseignants = csvService.Enseignants();

        initializeEtudiantComboBox();
        initializeEnseignantComboBox();

        btnListeEtudiants.setOnAction(e -> ouvrirListeEtudiants());
        btnListeEnseignants.setOnAction(e -> ouvrirListeEnseignants());
    }

    private void initializeEtudiantComboBox() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Etudiant e : listeEtudiants) {
            items.add(e.getNom() + " " + e.getPrenom() + " (" + e.getId() + ")");
        }
        etudiantComboBox.setItems(items);

        etudiantComboBox.setOnAction(e -> {
            String selected = etudiantComboBox.getValue();
            if (selected != null) {
                String id = selected.substring(selected.lastIndexOf("(") + 1, selected.lastIndexOf(")"));
                Etudiant etudiant = csvService.getEtudiantById(id);
                if (etudiant != null) afficherDetailsEtudiant(etudiant);
            }
        });
    }

    private void initializeEnseignantComboBox() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Enseignant e : listeEnseignants) {
            items.add(e.getNom() + " " + e.getPrenom() + " (" + e.getId() + ")");
        }
        enseignantComboBox.setItems(items);

        enseignantComboBox.setOnAction(e -> {
            String selected = enseignantComboBox.getValue();
            if (selected != null) {
                String id = selected.substring(selected.lastIndexOf("(") + 1, selected.lastIndexOf(")"));
                Enseignant enseignant = csvService.getEnseignantById(id);
                if (enseignant != null) afficherDetailsEnseignant(enseignant);
            }
        });
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
        afficherInformationsAdministrateur();
    }

    private void afficherInformationsAdministrateur() {
        if (administrateur == null) return;

        titreLabel.setText("Informations de l'administrateur");
        detailsContainer.getChildren().setAll(
                createInfoLine("Identifiant", administrateur.getId()),
                createInfoLine("Nom", administrateur.getNom()),
                createInfoLine("Prénom", administrateur.getPrenom()),
                createInfoLine("Email", administrateur.getMail())
        );
    }

    private HBox createInfoLine(String libelle, String valeur) {
        Label libelleLabel = new Label(libelle + " :");
        libelleLabel.setStyle(STYLE_BOLD_LABEL);
        libelleLabel.setPrefWidth(100);

        Label valeurLabel = new Label(valeur);
        HBox.setHgrow(valeurLabel, Priority.ALWAYS);

        HBox hbox = new HBox(10, libelleLabel, valeurLabel);
        hbox.setAlignment(Pos.CENTER_LEFT);
        return hbox;
    }

    private void afficherDetailsEtudiant(Etudiant e) {
        etudiantInfoContainer.getChildren().clear();
        edtContainer.getChildren().clear();

        etudiantInfoContainer.getChildren().addAll(
                createInfoLine("ID", e.getId()),
                createInfoLine("Nom", e.getNom()),
                createInfoLine("Prénom", e.getPrenom()),
                createInfoLine("Email", e.getMail()),
                createInfoLine("Classe", e.getClasse())
        );

        Button btnVoirEmploiDuTemps = new Button("Voir l'emploi du temps");
        btnVoirEmploiDuTemps.setStyle(STYLE_BUTTON_PRIMARY);
        btnVoirEmploiDuTemps.setOnAction(event -> afficherEmploiDuTempsEtudiant(e));

        etudiantInfoContainer.getChildren().add(btnVoirEmploiDuTemps);
    }


    private void afficherDetailsEnseignant(Enseignant e) {
        enseignantInfoContainer.getChildren().clear();
        edtEnseignantContainer.getChildren().clear();

        enseignantInfoContainer.getChildren().addAll(
                createInfoLine("ID", e.getId()),
                createInfoLine("Nom", e.getNom()),
                createInfoLine("Prénom", e.getPrenom()),
                createInfoLine("Email", e.getMail())
        );

        Button btnVoirEmploiDuTemps = new Button("Voir l'emploi du temps");
        btnVoirEmploiDuTemps.setStyle(STYLE_BUTTON_PRIMARY);
        btnVoirEmploiDuTemps.setOnAction(event -> afficherEmploiDuTempsEnseignant(e));

        enseignantInfoContainer.getChildren().add(btnVoirEmploiDuTemps);
    }

    private void afficherEmploiDuTempsEtudiant(Etudiant etudiant) {
        List<Cours> coursEtudiant = csvService.CoursEtudiant(etudiant.getId());
        edtContainer.getChildren().clear();

        VBox container = new VBox(10);
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #cccccc; -fx-border-radius: 5;");

        Label titreLabel = new Label("Emploi du temps de " + etudiant.getPrenom() + " " + etudiant.getNom());
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Création de la ComboBox de tri
        ComboBox<String> triComboBox = new ComboBox<>();
        triComboBox.getItems().addAll("Matière", "Date", "Horaires", "Salle", "Enseignant");
        triComboBox.setValue("Date");
        triComboBox.setPrefWidth(150);
        triComboBox.setStyle("-fx-font-size: 14px;");

        HBox triContainer = new HBox(10);
        triContainer.setAlignment(Pos.CENTER_LEFT);
        triContainer.getChildren().addAll(new Label("Trier par:"), triComboBox);
        triContainer.setPadding(new Insets(0, 0, 10, 0));

        Button ajoutBtn = new Button("Ajouter un cours");
        ajoutBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        ajoutBtn.setOnAction(e -> ouvrirPopupAjoutCours(etudiant));

        VBox coursContainer = new VBox(5);
        coursContainer.setPadding(new Insets(10, 0, 0, 0));

        // Fonction pour afficher/mettre à jour les cours
        Runnable afficherCours = () -> {
            coursContainer.getChildren().clear();

            if (coursEtudiant.isEmpty()) {
                Label emptyLabel = new Label("Aucun cours trouvé pour cet étudiant.");
                emptyLabel.setStyle("-fx-font-style: italic;");
                container.getChildren().addAll(titreLabel, triContainer, emptyLabel, ajoutBtn);
                return;
            }

            // Création de l'en-tête
            HBox header = new HBox(10);
            header.setPadding(new Insets(5));
            header.setStyle("-fx-background-color: #e0e0e0; -fx-font-weight: bold;");

            Label matiereHeader = new Label("Matière");
            Label dateHeader = new Label("Date");
            Label heureHeader = new Label("Horaires");
            Label salleHeader = new Label("Salle");
            Label enseignantHeader = new Label("Enseignant");
            Label statutHeader = new Label("Statut");
            Label actionsHeader = new Label("Actions");

            matiereHeader.setPrefWidth(150);
            dateHeader.setPrefWidth(100);
            heureHeader.setPrefWidth(100);
            salleHeader.setPrefWidth(80);
            enseignantHeader.setPrefWidth(150);
            statutHeader.setPrefWidth(80);
            actionsHeader.setPrefWidth(180);

            header.getChildren().addAll(matiereHeader, dateHeader, heureHeader, salleHeader, enseignantHeader, statutHeader, actionsHeader);
            coursContainer.getChildren().add(header);

            // Application du tri
            String critereTri = triComboBox.getValue();
            switch (critereTri) {
                case "Matière":
                    coursEtudiant.sort(Comparator.comparing(Cours::getMatiere));
                    break;
                case "Date":
                    coursEtudiant.sort(Comparator.comparing(Cours::getDate));
                    break;
                case "Horaires":
                    coursEtudiant.sort(Comparator.comparing(Cours::getHeure_debut));
                    break;
                case "Salle":
                    coursEtudiant.sort(Comparator.comparing(Cours::getId_salle));
                    break;
                case "Enseignant":
                    coursEtudiant.sort(Comparator.comparing(c -> {
                        Enseignant ens = csvService.getEnseignantById(c.getId_enseignant());
                        return ens != null ? ens.getNom() + " " + ens.getPrenom() : "";
                    }));
                    break;
            }

            // Affichage des cours
            for (Cours cours : coursEtudiant) {
                HBox coursLine = new HBox(10);
                coursLine.setPadding(new Insets(8, 5, 8, 5));
                coursLine.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");

                Label matiere = new Label(cours.getMatiere());
                Label date = new Label(cours.getDate());
                Label horaires = new Label(cours.getHeure_debut() + " - " + cours.getHeure_fin());
                Label salle = new Label(cours.getId_salle());

                String enseignantNom = cours.getId_enseignant();
                try {
                    Enseignant enseignant = csvService.getEnseignantById(cours.getId_enseignant());
                    if (enseignant != null) {
                        enseignantNom = enseignant.getNom() + " " + enseignant.getPrenom();
                    }
                } catch (Exception e) {
                    System.err.println("Erreur lors de la récupération de l'enseignant: " + e.getMessage());
                }

                Label enseignantLabel = new Label(enseignantNom);
                Label statutLabel = new Label(cours.isAnnulation() ? "Annulé" : "Prévu");
                statutLabel.setStyle(cours.isAnnulation() ? "-fx-text-fill: red;" : "-fx-text-fill: green;");

                Button modifierBtn = new Button("Modifier");
                modifierBtn.setStyle("-fx-background-color: #ffbb33; -fx-text-fill: white;");
                modifierBtn.setOnAction(event -> ouvrirPopupModificationCours(cours, etudiant));

                Button supprimerBtn = new Button("Supprimer");
                supprimerBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                supprimerBtn.setOnAction(event -> confirmerSuppressionCours(cours, etudiant));

                Button annulerBtn = new Button(cours.isAnnulation() ? "Rétablir" : "Annuler");
                annulerBtn.setStyle(cours.isAnnulation() ? "-fx-background-color: #33cc33; -fx-text-fill: white;"
                        : "-fx-background-color: #ff9900; -fx-text-fill: white;");
                annulerBtn.setOnAction(event -> changerStatutAnnulation(cours, etudiant));

                HBox boutonsContainer = new HBox(5, modifierBtn, supprimerBtn, annulerBtn);
                boutonsContainer.setAlignment(Pos.CENTER);

                matiere.setPrefWidth(150);
                date.setPrefWidth(100);
                horaires.setPrefWidth(100);
                salle.setPrefWidth(80);
                enseignantLabel.setPrefWidth(150);
                statutLabel.setPrefWidth(80);
                boutonsContainer.setPrefWidth(200);

                coursLine.getChildren().addAll(matiere, date, horaires, salle, enseignantLabel, statutLabel, boutonsContainer);
                coursContainer.getChildren().add(coursLine);
            }
        };

        // Gestion du changement de tri
        triComboBox.setOnAction(e -> afficherCours.run());

        // Affichage initial
        afficherCours.run();

        // Organisation des éléments dans le conteneur principal
        container.getChildren().addAll(titreLabel, triContainer, coursContainer, ajoutBtn);
        edtContainer.getChildren().add(container);
    }

    // Méthodes auxiliaires extraites pour plus de clarté
    private void confirmerSuppressionCours(Cours cours, Etudiant etudiant) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Supprimer le cours");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce cours de " + cours.getMatiere() + " ?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean suppressionReussie = csvService.supprimerCours(cours.getId_cours());

            if (suppressionReussie) {
                Platform.runLater(() -> afficherEmploiDuTempsEtudiant(etudiant));
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Le cours a été supprimé avec succès.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la suppression du cours.");
            }
        }
    }

    private void changerStatutAnnulation(Cours cours, Etudiant etudiant) {
        boolean nouvelEtat = !cours.isAnnulation();
        boolean modificationReussie = csvService.modifierStatutAnnulationCours(cours.getId_cours(), nouvelEtat);

        if (modificationReussie) {
            afficherEmploiDuTempsEtudiant(etudiant);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le statut du cours a été modifié avec succès.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la modification du statut du cours.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void afficherEmploiDuTempsEnseignant(Enseignant enseignant) {
        List<Cours> coursEnseignant = csvService.CoursEnseignant(enseignant.getId());

        edtEnseignantContainer.getChildren().clear();

        VBox container = new VBox(10);
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #cccccc; -fx-border-radius: 5;");

        Label titreLabel = new Label("Emploi du temps de " + enseignant.getPrenom() + " " + enseignant.getNom());
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button ajoutBtn = new Button("Ajouter un cours");
        ajoutBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        ajoutBtn.setOnAction(e -> ouvrirPopupAjoutCoursEnseignant(enseignant));

        if (coursEnseignant.isEmpty()) {
            Label emptyLabel = new Label("Aucun cours trouvé pour cet enseignant.");
            emptyLabel.setStyle("-fx-font-style: italic;");
            container.getChildren().addAll(titreLabel, emptyLabel, ajoutBtn);
        } else {
            VBox coursContainer = new VBox(5);
            coursContainer.setPadding(new Insets(10, 0, 0, 0));

            HBox header = new HBox(10);
            header.setPadding(new Insets(5));
            header.setStyle("-fx-background-color: #e0e0e0; -fx-font-weight: bold;");

            Label matiereHeader = new Label("Matière");
            Label dateHeader = new Label("Date");
            Label heureHeader = new Label("Horaires");
            Label salleHeader = new Label("Salle");
            Label classeHeader = new Label("Classe");
            Label statutHeader = new Label("Statut");
            Label actionsHeader = new Label("Actions");

            matiereHeader.setPrefWidth(150);
            dateHeader.setPrefWidth(100);
            heureHeader.setPrefWidth(100);
            salleHeader.setPrefWidth(80);
            classeHeader.setPrefWidth(150);
            statutHeader.setPrefWidth(80);
            actionsHeader.setPrefWidth(150);

            header.getChildren().addAll(matiereHeader, dateHeader, heureHeader, salleHeader, classeHeader, statutHeader, actionsHeader);
            coursContainer.getChildren().add(header);

            for (Cours cours : coursEnseignant) {
                HBox coursLine = new HBox(10);
                coursLine.setPadding(new Insets(8, 5, 8, 5));
                coursLine.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");

                Label matiere = new Label(cours.getMatiere());
                Label date = new Label(cours.getDate());
                Label horaires = new Label(cours.getHeure_debut() + " - " + cours.getHeure_fin());
                Label salle = new Label(cours.getId_salle());
                Label classe = new Label(cours.getClasse());
                Label statutLabel = new Label(cours.isAnnulation() ? "Annulé" : "Prévu");
                statutLabel.setStyle(cours.isAnnulation() ? "-fx-text-fill: red;" : "-fx-text-fill: green;");

                Button modifierBtn = new Button("Modifier");
                modifierBtn.setStyle("-fx-background-color: #ffbb33; -fx-text-fill: white;");
                modifierBtn.setOnAction(event -> ouvrirPopupModificationCoursEnseignant(cours, enseignant));

                Button supprimerBtn = new Button("Supprimer");
                supprimerBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                supprimerBtn.setOnAction(event -> {
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setTitle("Confirmation de suppression");
                    confirmation.setHeaderText("Supprimer le cours");
                    confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce cours de " + cours.getMatiere() + " ?");

                    Optional<ButtonType> result = confirmation.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        boolean suppressionReussie = csvService.supprimerCours(cours.getId_cours());

                        if (suppressionReussie) {
                            afficherEmploiDuTempsEnseignant(enseignant);
                            Alert success = new Alert(Alert.AlertType.INFORMATION);
                            success.setTitle("Succès");
                            success.setHeaderText(null);
                            success.setContentText("Le cours a été supprimé avec succès.");
                            success.showAndWait();
                        } else {
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setTitle("Erreur");
                            error.setHeaderText(null);
                            error.setContentText("Une erreur est survenue lors de la suppression du cours.");
                            error.showAndWait();
                        }
                    }
                });

                Button annulerBtn = new Button(cours.isAnnulation() ? "Rétablir" : "Annuler");
                annulerBtn.setStyle(cours.isAnnulation() ? "-fx-background-color: #33cc33; -fx-text-fill: white;" : "-fx-background-color: #ff9900; -fx-text-fill: white;");
                annulerBtn.setOnAction(event -> {
                    boolean nouvelEtat = !cours.isAnnulation();
                    boolean modificationReussie = csvService.modifierStatutAnnulationCours(cours.getId_cours(), nouvelEtat);

                    if (modificationReussie) {
                        afficherEmploiDuTempsEnseignant(enseignant);
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Succès");
                        success.setHeaderText(null);
                        success.setContentText("Le statut du cours a été modifié avec succès.");
                        success.showAndWait();
                    } else {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Erreur");
                        error.setHeaderText(null);
                        error.setContentText("Une erreur est survenue lors de la modification du statut du cours.");
                        error.showAndWait();
                    }
                });

                HBox boutonsContainer = new HBox(5, modifierBtn, supprimerBtn, annulerBtn);
                boutonsContainer.setAlignment(Pos.CENTER);

                matiere.setPrefWidth(150);
                date.setPrefWidth(100);
                horaires.setPrefWidth(100);
                salle.setPrefWidth(80);
                classe.setPrefWidth(150);
                statutLabel.setPrefWidth(80);
                boutonsContainer.setPrefWidth(200);

                coursLine.getChildren().addAll(matiere, date, horaires, salle, classe, statutLabel, boutonsContainer);
                coursContainer.getChildren().add(coursLine);
            }

            container.getChildren().addAll(titreLabel, coursContainer, ajoutBtn);
        }

        edtEnseignantContainer.getChildren().add(container);
    }

    private void confirmerSuppressionCours(Cours cours, Enseignant enseignant) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Supprimer le cours");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce cours de " + cours.getMatiere() + " ?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean suppressionReussie = csvService.supprimerCours(cours.getId_cours());

            if (suppressionReussie) {
                Platform.runLater(() -> afficherEmploiDuTempsEnseignant(enseignant));
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Le cours a été supprimé avec succès.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la suppression du cours.");
            }
        }
    }

    private void changerStatutAnnulation(Cours cours, Enseignant enseignant) {
        boolean nouvelEtat = !cours.isAnnulation();
        boolean modificationReussie = csvService.modifierStatutAnnulationCours(cours.getId_cours(), nouvelEtat);

        if (modificationReussie) {
            afficherEmploiDuTempsEnseignant(enseignant);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le statut du cours a été modifié avec succès.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la modification du statut du cours.");
        }
    }

    private void ouvrirListeEtudiants() {
        Popup popup = creerPopup("Liste des étudiants");
        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();

        for (Etudiant e : listeEtudiants) {
            items.add(e.getNom() + " " + e.getPrenom() + " - " + e.getClasse() + " (" + e.getId() + ")");
        }

        listView.setItems(items);
        VBox.setVgrow(listView, Priority.ALWAYS);

        Button btnSelectionner = new Button("Sélectionner");
        btnSelectionner.setStyle(STYLE_BUTTON_PRIMARY);
        btnSelectionner.setOnAction(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String id = selected.substring(selected.lastIndexOf("(") + 1, selected.lastIndexOf(")"));
                Etudiant etu = csvService.getEtudiantById(id);
                if (etu != null) afficherDetailsEtudiant(etu);
                popup.hide();
            }
        });

        Button btnFermer = new Button("Fermer");
        btnFermer.setOnAction(e -> popup.hide());

        VBox popupContent = (VBox) popup.getContent().get(0);
        popupContent.getChildren().addAll(listView, new HBox(10, btnSelectionner, btnFermer));
        popup.show(scrollPane.getScene().getWindow(), scrollPane.getScene().getWindow().getX() + 100, scrollPane.getScene().getWindow().getY() + 100);
    }

    private void ouvrirListeEnseignants() {
        Popup popup = creerPopup("Liste des enseignants");
        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();

        for (Enseignant e : listeEnseignants) {
            items.add(e.getNom() + " " + e.getPrenom() + " (" + e.getId() + ")");
        }

        listView.setItems(items);
        VBox.setVgrow(listView, Priority.ALWAYS);

        Button btnSelectionner = new Button("Sélectionner");
        btnSelectionner.setStyle(STYLE_BUTTON_PRIMARY);
        btnSelectionner.setOnAction(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String id = selected.substring(selected.lastIndexOf("(") + 1, selected.lastIndexOf(")"));
                Enseignant ens = csvService.getEnseignantById(id);
                if (ens != null) afficherDetailsEnseignant(ens);
                popup.hide();
            }
        });

        Button btnFermer = new Button("Fermer");
        btnFermer.setOnAction(e -> popup.hide());

        VBox popupContent = (VBox) popup.getContent().get(0);
        popupContent.getChildren().addAll(listView, new HBox(10, btnSelectionner, btnFermer));
        popup.show(scrollPane.getScene().getWindow(), scrollPane.getScene().getWindow().getX() + 100, scrollPane.getScene().getWindow().getY() + 100);
    }

    private Popup creerPopup(String titre) {
        Popup popup = new Popup();
        popup.setAutoHide(true);

        VBox popupContent = new VBox(10);
        popupContent.setPadding(new Insets(15));
        popupContent.setStyle(STYLE_POPUP);
        popupContent.setMinWidth(400);
        popupContent.setMaxHeight(500);

        Label titleLabel = new Label(titre);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        popupContent.getChildren().add(titleLabel);
        popup.getContent().add(popupContent);
        return popup;
    }

    private void ouvrirPopupAjoutCours(Etudiant etudiant) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Ajouter un nouveau cours");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField coursField = new TextField();
        TextField salleField = new TextField();
        TextField matiereField = new TextField();
        TextField dateField = new TextField();
        TextField heureDebutField = new TextField();
        TextField heureFinField = new TextField();

        // ComboBox pour les enseignants
        ComboBox<String> enseignantCombo = new ComboBox<>();
        enseignantCombo.setItems(FXCollections.observableArrayList(
                csvService.Enseignants().stream()
                        .map(e -> e.getId() + " - " + e.getNom() + " " + e.getPrenom())
                        .collect(Collectors.toList())
        ));

        // Ajout des champs au formulaire
        grid.add(new Label("ID Cours:"), 0, 0);
        grid.add(coursField, 1, 0);
        grid.add(new Label("Matière:"), 0, 1);
        grid.add(matiereField, 1, 1);
        grid.add(new Label("Date:"), 0, 2);
        grid.add(dateField, 1, 2);
        grid.add(new Label("Heure début:"), 0, 3);
        grid.add(heureDebutField, 1, 3);
        grid.add(new Label("Heure fin:"), 0, 4);
        grid.add(heureFinField, 1, 4);
        grid.add(new Label("Salle:"), 0, 5);
        grid.add(salleField, 1, 5);
        grid.add(new Label("Enseignant:"), 0, 6);
        grid.add(enseignantCombo, 1, 6);

        Button validerBtn = new Button("Valider");
        validerBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        validerBtn.setOnAction(e -> {
            if (validerEtModifierCours(etudiant, coursField.getText(), matiereField.getText(),
                    dateField.getText(), heureDebutField.getText(), heureFinField.getText(),
                    salleField.getText(), enseignantCombo.getValue())) {
                popup.close();
                afficherEmploiDuTempsEtudiant(etudiant);
            }
        });

        Button annulerBtn = new Button("Annuler");
        annulerBtn.setOnAction(e -> popup.close());

        HBox boutonsBox = new HBox(10, validerBtn, annulerBtn);
        boutonsBox.setAlignment(Pos.CENTER_RIGHT);

        VBox popupContent = new VBox(10, grid, boutonsBox);
        popupContent.setPadding(new Insets(10));

        Scene scene = new Scene(popupContent, 400, 400);
        popup.setScene(scene);
        popup.showAndWait();
    }

    private boolean validerEtModifierCours(Etudiant etudiant, String idCours, String matiere,
                                           String date, String heureDebut, String heureFin,
                                           String salle, String enseignantSelectionne) {
        if (idCours == null || idCours.isEmpty() ||
                matiere == null || matiere.isEmpty() ||
                date == null || date.isEmpty() ||
                heureDebut == null || heureDebut.isEmpty() ||
                heureFin == null || heureFin.isEmpty() ||
                salle == null || salle.isEmpty() ||
                enseignantSelectionne == null || enseignantSelectionne.isEmpty()) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis");
            return false;
        }

        if (!csvService.isSalleDisponible(salle, date, heureDebut, heureFin)) {
            afficherAlerte("Erreur", "La salle " + salle + " est déjà occupée à cette date et heure");
            return false;
        }

        String idEnseignant = enseignantSelectionne.split(" - ")[0];

        // Créer un nouvel objet Cours
        Cours nouveauCours = new Cours(
                idCours,
                salle,
                matiere,
                date,
                heureDebut,
                heureFin,
                idEnseignant,
                etudiant.getClasse(),
                false);

        try {
            boolean succes = csvService.ajouterCours(nouveauCours);
            if (succes) {
                afficherAlerte("Succès", "Le cours a été ajouté avec succès");
                return true;
            } else {
                afficherAlerte("Erreur", "Échec de l'ajout du cours");
                return false;
            }
        } catch (Exception e) {
            afficherAlerte("Erreur", "Une erreur est survenue: " + e.getMessage());
            return false;
        }
    }

    private void ouvrirPopupModificationCours(Cours cours, Etudiant etudiant) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Modifier le cours");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Champs pré-remplis avec les valeurs actuelles du cours
        TextField matiereField = new TextField(cours.getMatiere());
        TextField dateField = new TextField(cours.getDate());
        TextField heureDebutField = new TextField(cours.getHeure_debut());
        TextField heureFinField = new TextField(cours.getHeure_fin());
        TextField salleField = new TextField(cours.getId_salle());

        // ComboBox pour les enseignants
        ComboBox<String> enseignantCombo = new ComboBox<>();
        enseignantCombo.setItems(FXCollections.observableArrayList(
                csvService.Enseignants().stream()
                        .map(e -> e.getId() + " - " + e.getNom() + " " + e.getPrenom())
                        .collect(Collectors.toList())
        ))
        ;

        // Sélectionner l'enseignant actuel
        String enseignantActuel = cours.getId_enseignant();
        try {
            Enseignant enseignant = csvService.getEnseignantById(enseignantActuel);
            if (enseignant != null) {
                enseignantCombo.setValue(enseignant.getId() + " - " + enseignant.getNom() + " " + enseignant.getPrenom());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'enseignant: " + e.getMessage());
        }

        // ComboBox pour le statut annulé
        ComboBox<Boolean> annuleCombo = new ComboBox<>();
        annuleCombo.setItems(FXCollections.observableArrayList(true, false));
        annuleCombo.setValue(cours.isAnnulation());

        grid.add(new Label("Matière:"), 0, 0);
        grid.add(matiereField, 1, 0);
        grid.add(new Label("Date:"), 0, 1);
        grid.add(dateField, 1, 1);
        grid.add(new Label("Heure début (HH:MM):"), 0, 2);
        grid.add(heureDebutField, 1, 2);
        grid.add(new Label("Heure fin (HH:MM):"), 0, 3);
        grid.add(heureFinField, 1, 3);
        grid.add(new Label("Salle:"), 0, 4);
        grid.add(salleField, 1, 4);
        grid.add(new Label("Enseignant:"), 0, 5);
        grid.add(enseignantCombo, 1, 5);
        grid.add(new Label("Statut:"), 0, 6);
        grid.add(annuleCombo, 1, 6);

        Button validerBtn = new Button("Valider");
        validerBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        validerBtn.setOnAction(e -> {
            if (validerEtModifierCours(cours, etudiant, matiereField.getText(),
                    dateField.getText(), heureDebutField.getText(),
                    heureFinField.getText(), salleField.getText(),
                    enseignantCombo.getValue(), annuleCombo.getValue())) {
                popup.close();
                afficherEmploiDuTempsEtudiant(etudiant);
            }
        });

        Button annulerBtn = new Button("Annuler");
        annulerBtn.setOnAction(e -> popup.close());

        HBox boutonsBox = new HBox(10, validerBtn, annulerBtn);
        boutonsBox.setAlignment(Pos.CENTER_RIGHT);

        VBox popupContent = new VBox(10, grid, boutonsBox);
        popupContent.setPadding(new Insets(10));

        Scene scene = new Scene(popupContent, 400, 450); // Augmenté la hauteur pour la nouvelle ComboBox
        popup.setScene(scene);
        popup.showAndWait();
    }

    private boolean validerEtModifierCours(Cours cours, Etudiant etudiant, String matiere, String date, String heureDebut, String heureFin, String salle, String enseignantSelectionne, Boolean estAnnule) {
        if (matiere == null || matiere.isEmpty() ||
                date == null || date.isEmpty() ||
                heureDebut == null || heureDebut.isEmpty() ||
                heureFin == null || heureFin.isEmpty() ||
                salle == null || salle.isEmpty() ||
                enseignantSelectionne == null || enseignantSelectionne.isEmpty() ||
                estAnnule == null) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis");
            return false;
        }

        String idEnseignant = enseignantSelectionne.split(" - ")[0];

        // Créer un nouvel objet Cours avec les modifications
        Cours coursModifie = new Cours(
                cours.getId_cours(),
                salle,
                matiere,
                date,
                heureDebut,
                heureFin,
                idEnseignant,
                etudiant.getClasse(),
                estAnnule);

        try {
            boolean succes = csvService.modifierCours(coursModifie);
            if (succes) {
                afficherAlerte("Succès", "Le cours a été modifié avec succès");
                return true;
            } else {
                afficherAlerte("Erreur", "Échec de la modification du cours");
                return false;
            }
        } catch (Exception e) {
            afficherAlerte("Erreur", "Une erreur est survenue: " + e.getMessage());
            return false;
        }
    }


    private void ouvrirPopupAjoutCoursEnseignant(Enseignant enseignant) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Ajouter un nouveau cours");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField coursField = new TextField();
        TextField salleField = new TextField();
        TextField matiereField = new TextField();
        TextField dateField = new TextField();
        TextField heureDebutField = new TextField();
        TextField heureFinField = new TextField();
        TextField classeField = new TextField();

        grid.add(new Label("ID Cours:"), 0, 0);
        grid.add(coursField, 1, 0);
        grid.add(new Label("Matière:"), 0, 1);
        grid.add(matiereField, 1, 1);
        grid.add(new Label("Date:"), 0, 2);
        grid.add(dateField, 1, 2);
        grid.add(new Label("Heure début:"), 0, 3);
        grid.add(heureDebutField, 1, 3);
        grid.add(new Label("Heure fin:"), 0, 4);
        grid.add(heureFinField, 1, 4);
        grid.add(new Label("Salle:"), 0, 5);
        grid.add(salleField, 1, 5);
        grid.add(new Label("Classe:"), 0, 6);
        grid.add(classeField, 1, 6);

        Button validerBtn = new Button("Valider");
        validerBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        validerBtn.setOnAction(e -> {
            if (validerEtAjouterCoursEnseignant(enseignant, coursField.getText(), matiereField.getText(),
                    dateField.getText(), heureDebutField.getText(), heureFinField.getText(),
                    salleField.getText(), classeField.getText())) {
                popup.close();
                afficherEmploiDuTempsEnseignant(enseignant);
            }
        });

        Button annulerBtn = new Button("Annuler");
        annulerBtn.setOnAction(e -> popup.close());

        HBox boutonsBox = new HBox(10, validerBtn, annulerBtn);
        boutonsBox.setAlignment(Pos.CENTER_RIGHT);

        VBox popupContent = new VBox(10, grid, boutonsBox);
        popupContent.setPadding(new Insets(10));

        Scene scene = new Scene(popupContent, 400, 400);
        popup.setScene(scene);
        popup.showAndWait();
    }

    private boolean validerEtAjouterCoursEnseignant(Enseignant enseignant, String idCours, String matiere,
                                                    String date, String heureDebut, String heureFin,
                                                    String salle, String classe) {
        if (idCours == null || idCours.isEmpty() ||
                matiere == null || matiere.isEmpty() ||
                date == null || date.isEmpty() ||
                heureDebut == null || heureDebut.isEmpty() ||
                heureFin == null || heureFin.isEmpty() ||
                salle == null || salle.isEmpty() ||
                classe == null || classe.isEmpty()) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis");
            return false;
        }

        if (!csvService.isSalleDisponible(salle, date, heureDebut, heureFin)) {
            afficherAlerte("Erreur", "La salle " + salle + " est déjà occupée à cette date et heure");
            return false;
        }

        // Créer un nouvel objet Cours
        Cours nouveauCours = new Cours(
                idCours,
                salle,
                matiere,
                date,
                heureDebut,
                heureFin,
                enseignant.getId(),
                classe,
                false);

        try {
            boolean succes = csvService.ajouterCours(nouveauCours);
            if (succes) {
                afficherAlerte("Succès", "Le cours a été ajouté avec succès");
                return true;
            } else {
                afficherAlerte("Erreur", "Échec de l'ajout du cours");
                return false;
            }
        } catch (Exception e) {
            afficherAlerte("Erreur", "Une erreur est survenue: " + e.getMessage());
            return false;
        }
    }

    private void ouvrirPopupModificationCoursEnseignant(Cours cours, Enseignant enseignant) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Modifier le cours");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField matiereField = new TextField(cours.getMatiere());
        TextField dateField = new TextField(cours.getDate());
        TextField heureDebutField = new TextField(cours.getHeure_debut());
        TextField heureFinField = new TextField(cours.getHeure_fin());
        TextField salleField = new TextField(cours.getId_salle());
        TextField classeField = new TextField(cours.getClasse());

        ComboBox<Boolean> annuleCombo = new ComboBox<>();
        annuleCombo.setItems(FXCollections.observableArrayList(true, false));
        annuleCombo.setValue(cours.isAnnulation());

        grid.add(new Label("Matière:"), 0, 0);
        grid.add(matiereField, 1, 0);
        grid.add(new Label("Date:"), 0, 1);
        grid.add(dateField, 1, 1);
        grid.add(new Label("Heure début:"), 0, 2);
        grid.add(heureDebutField, 1, 2);
        grid.add(new Label("Heure fin:"), 0, 3);
        grid.add(heureFinField, 1, 3);
        grid.add(new Label("Salle:"), 0, 4);
        grid.add(salleField, 1, 4);
        grid.add(new Label("Classe:"), 0, 5);
        grid.add(classeField, 1, 5);
        grid.add(new Label("Statut:"), 0, 6);
        grid.add(annuleCombo, 1, 6);

        Button validerBtn = new Button("Valider");
        validerBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        validerBtn.setOnAction(e -> {
            if (validerEtModifierCoursEnseignant(cours, enseignant, matiereField.getText(),
                    dateField.getText(), heureDebutField.getText(),
                    heureFinField.getText(), salleField.getText(),
                    classeField.getText(), annuleCombo.getValue())) {
                popup.close();
                afficherEmploiDuTempsEnseignant(enseignant);
            }
        });

        Button annulerBtn = new Button("Annuler");
        annulerBtn.setOnAction(e -> popup.close());

        HBox boutonsBox = new HBox(10, validerBtn, annulerBtn);
        boutonsBox.setAlignment(Pos.CENTER_RIGHT);

        VBox popupContent = new VBox(10, grid, boutonsBox);
        popupContent.setPadding(new Insets(10));

        Scene scene = new Scene(popupContent, 400, 450);
        popup.setScene(scene);
        popup.showAndWait();
    }

    private boolean validerEtModifierCoursEnseignant(Cours cours, Enseignant enseignant, String matiere,
                                                     String date, String heureDebut, String heureFin,
                                                     String salle, String classe, Boolean estAnnule) {
        if (matiere == null || matiere.isEmpty() ||
                date == null || date.isEmpty() ||
                heureDebut == null || heureDebut.isEmpty() ||
                heureFin == null || heureFin.isEmpty() ||
                salle == null || salle.isEmpty() ||
                classe == null || classe.isEmpty() ||
                estAnnule == null) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis");
            return false;
        }

        // Créer un nouvel objet Cours avec les modifications
        Cours coursModifie = new Cours(
                cours.getId_cours(),
                salle,
                matiere,
                date,
                heureDebut,
                heureFin,
                enseignant.getId(),
                classe,
                estAnnule);

        try {
            boolean succes = csvService.modifierCours(coursModifie);
            if (succes) {
                afficherAlerte("Succès", "Le cours a été modifié avec succès");
                return true;
            } else {
                afficherAlerte("Erreur", "Échec de la modification du cours");
                return false;
            }
        } catch (Exception e) {
            afficherAlerte("Erreur", "Une erreur est survenue: " + e.getMessage());
            return false;
        }
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean plagesHorairesSeChevauchent(String debut1, String fin1, String debut2, String fin2) {
        try {
            // Normaliser les formats d'heure (supprimer 'h' si présent et ajouter : si nécessaire)
            debut1 = debut1.replace("h", ":").replace("H", ":");
            fin1 = fin1.replace("h", ":").replace("H", ":");
            debut2 = debut2.replace("h", ":").replace("H", ":");
            fin2 = fin2.replace("h", ":").replace("H", ":");

            // S'assurer qu'il y a deux chiffres pour les minutes
            debut1 = normaliserHeure(debut1);
            fin1 = normaliserHeure(fin1);
            debut2 = normaliserHeure(debut2);
            fin2 = normaliserHeure(fin2);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
            LocalTime d1 = LocalTime.parse(debut1, formatter);
            LocalTime f1 = LocalTime.parse(fin1, formatter);
            LocalTime d2 = LocalTime.parse(debut2, formatter);
            LocalTime f2 = LocalTime.parse(fin2, formatter);

            // Vérifier le chevauchement
            return !(f1.isBefore(d2) || f2.isBefore(d1));
        } catch (Exception e) {
            System.err.println("Erreur lors de la comparaison des horaires: " + e.getMessage());
            return true; // En cas d'erreur, on considère qu'il y a chevauchement par sécurité
        }
    }

    private String normaliserHeure(String heure) {
        if (heure.length() == 4 && heure.contains(":")) { // format H:mm
            return heure;
        }
        if (heure.length() == 1) { // format H
            return heure + ":00";
        }
        if (heure.length() == 2) {
            if (heure.contains(":")) { // format H:
                return heure + "00";
            } else { // format HH
                return heure + ":00";
            }
        }
        if (heure.length() == 3) {
            if (heure.contains(":")) { // format H:mm ou HH:m
                String[] parts = heure.split(":");
                if (parts[1].length() == 1) {
                    return parts[0] + ":0" + parts[1];
                } else {
                    return heure;
                }
            } else { // format HHmm
                return heure.substring(0, 2) + ":" + heure.substring(2);
            }
        }
        return heure;
    }

    public boolean modifierStatutAnnulationCours(String idCours, boolean nouvelEtat) {
        try {
            List<Cours> tousLesCours = CsvService.getInstance().lireTousLesCours();
            boolean trouve = false;

            for (Cours cours : tousLesCours) {
                if (cours.getId_cours().equals(idCours)) {
                    cours.setAnnulation(nouvelEtat);
                    trouve = true;
                    break;
                }
            }

            if (trouve) {
                CsvService.getInstance().reecrireTousLesCours(tousLesCours);
                return true;
            } else {
                System.err.println("Cours avec l'ID " + idCours + " non trouvé.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la modification du statut d'annulation : " + e.getMessage());
            return false;
        }
    }

    private void afficherCoursTriés(String critereTri, List<Cours> coursEtudiant, Etudiant etudiant, VBox container) {
        coursEtudiant.sort(switch (critereTri) {
            case "Matière" -> Comparator.comparing(Cours::getMatiere);
            case "Date" -> Comparator.comparing(Cours::getDate);
            case "Horaires" -> Comparator.comparing(Cours::getHeure_debut);
            case "Salle" -> Comparator.comparing(Cours::getId_salle);
            case "Enseignant" -> Comparator.comparing(c -> {
                Enseignant ens = csvService.getEnseignantById(c.getId_enseignant());
                return ens != null ? ens.getNom() : "";
            });
            default -> Comparator.comparing(Cours::getDate); // fallback
        });
    }


}
