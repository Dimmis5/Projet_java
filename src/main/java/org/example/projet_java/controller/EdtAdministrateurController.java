package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Popup;

import org.example.projet_java.model.*;
import org.example.projet_java.service.CsvService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    // Styles réutilisables
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
        edtContainer.getChildren().clear(); // Nettoyer l'emploi du temps précédent

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

        if (coursEtudiant.isEmpty()) {
            Label emptyLabel = new Label("Aucun cours trouvé pour cet étudiant.");
            emptyLabel.setStyle("-fx-font-style: italic;");
            container.getChildren().addAll(titreLabel, emptyLabel);
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
            Label enseignantHeader = new Label("Enseignant");

            matiereHeader.setPrefWidth(150);
            dateHeader.setPrefWidth(100);
            heureHeader.setPrefWidth(100);
            salleHeader.setPrefWidth(80);
            enseignantHeader.setPrefWidth(150);

            header.getChildren().addAll(matiereHeader, dateHeader, heureHeader, salleHeader, enseignantHeader);
            coursContainer.getChildren().add(header);

            for (Cours cours : coursEtudiant) {
                HBox coursLine = new HBox(10);
                coursLine.setPadding(new Insets(8, 5, 8, 5));
                coursLine.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");

                Label matiere = new Label(cours.getMatiere());
                Label date = new Label(cours.getDate());
                Label horaires = new Label(cours.getHeure_debut() + " - " + cours.getHeure_fin());
                Label salle = new Label(cours.getId_salle());

                String enseignantId = cours.getId_enseignant();
                String enseignantNom = enseignantId;

                try {
                    Enseignant enseignant = csvService.getEnseignantById(enseignantId);
                    if (enseignant != null) {
                        enseignantNom = enseignant.getNom() + " " + enseignant.getPrenom();
                    }
                } catch (Exception e) {
                    System.err.println("Erreur lors de la récupération de l'enseignant: " + e.getMessage());
                }

                Label enseignantLabel = new Label(enseignantNom);

                matiere.setPrefWidth(150);
                date.setPrefWidth(100);
                horaires.setPrefWidth(100);
                salle.setPrefWidth(80);
                enseignantLabel.setPrefWidth(150);

                coursLine.getChildren().addAll(matiere, date, horaires, salle, enseignantLabel);
                coursContainer.getChildren().add(coursLine);
            }

            container.getChildren().addAll(titreLabel, coursContainer);
        }

        edtContainer.getChildren().add(container);
    }

    private void afficherEmploiDuTempsEnseignant(Enseignant enseignant) {

        List<Cours> coursEnseignant = csvService.CoursEnseignant(enseignant.getId());

        edtEnseignantContainer.getChildren().clear();

        VBox container = new VBox(10);
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #cccccc; -fx-border-radius: 5;");

        Label titreLabel = new Label("Emploi du temps de " + enseignant.getPrenom() + " " + enseignant.getNom());
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        if (coursEnseignant.isEmpty()) {
            Label emptyLabel = new Label("Aucun cours trouvé pour cet enseignant.");
            emptyLabel.setStyle("-fx-font-style: italic;");
            container.getChildren().addAll(titreLabel, emptyLabel);
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

            matiereHeader.setPrefWidth(150);
            dateHeader.setPrefWidth(100);
            heureHeader.setPrefWidth(100);
            salleHeader.setPrefWidth(80);
            classeHeader.setPrefWidth(150);

            header.getChildren().addAll(matiereHeader, dateHeader, heureHeader, salleHeader, classeHeader);
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

                matiere.setPrefWidth(150);
                date.setPrefWidth(100);
                horaires.setPrefWidth(100);
                salle.setPrefWidth(80);
                classe.setPrefWidth(150);

                coursLine.getChildren().addAll(matiere, date, horaires, salle, classe);
                coursContainer.getChildren().add(coursLine);
            }

            container.getChildren().addAll(titreLabel, coursContainer);
        }

        edtEnseignantContainer.getChildren().add(container);
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
}
