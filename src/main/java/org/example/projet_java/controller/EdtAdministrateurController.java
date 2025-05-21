package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import org.example.projet_java.model.Administrateur;
import org.example.projet_java.model.Enseignant;
import org.example.projet_java.model.Etudiant;
import org.example.projet_java.model.Cours;
import org.example.projet_java.service.CsvService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EdtAdministrateurController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    private Administrateur administrateur;

    private List<Etudiant> listeEtudiants = new ArrayList<>();

    private List<Enseignant> listeEnseignants = new ArrayList<>();

    private CsvService csvService = CsvService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BorderPane mainContainer = new BorderPane();
        mainContainer.setPadding(new Insets(20));

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        listeEtudiants = csvService.Etudiants();
        listeEnseignants = csvService.Enseignants();
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
        afficherInformationsAdministrateur();
    }

    private void afficherInformationsAdministrateur() {
        if (administrateur == null) return;
        VBox infoContainer = new VBox(10);
        infoContainer.setPadding(new Insets(20));
        infoContainer.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #cccccc; -fx-border-radius: 5;");

        Label titreLabel = new Label("Informations de l'administrateur");
        titreLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox detailsContainer = new VBox(8);
        detailsContainer.setPadding(new Insets(10, 0, 0, 0));

        detailsContainer.getChildren().addAll(
                createInfoLine("Identifiant", administrateur.getId()),
                createInfoLine("Nom", administrateur.getNom()),
                createInfoLine("Prénom", administrateur.getPrenom()),
                createInfoLine("Email", administrateur.getMail())
        );

        infoContainer.getChildren().addAll(titreLabel, detailsContainer);

        BorderPane mainContainer = new BorderPane();
        mainContainer.setTop(infoContainer);
        mainContainer.setPadding(new Insets(20));

        VBox contentContainer = new VBox(20);
        contentContainer.setPadding(new Insets(20, 0, 0, 0));

        Label edtLabel = new Label("Gestion de l'emploi du temps");
        edtLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        contentContainer.getChildren().add(edtLabel);

        HBox etudiantSection = createEtudiantsSelector();
        contentContainer.getChildren().add(etudiantSection);

        VBox etudiantInfoContainer = new VBox(10);
        etudiantInfoContainer.setId("etudiantInfoContainer");
        etudiantInfoContainer.setPadding(new Insets(20, 0, 0, 0));
        contentContainer.getChildren().add(etudiantInfoContainer);

        HBox enseignantSection = createEnseignantsSelector();
        contentContainer.getChildren().add(enseignantSection);

        VBox enseignantInfoContainer = new VBox(10);
        enseignantInfoContainer.setId("enseignantInfoContainer");
        enseignantInfoContainer.setPadding(new Insets(20, 0, 0, 0));
        contentContainer.getChildren().add(enseignantInfoContainer);

        mainContainer.setCenter(contentContainer);

        scrollPane.setContent(mainContainer);
    }

    private HBox createInfoLine(String libelle, String valeur) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);

        Label libelleLabel = new Label(libelle + " :");
        libelleLabel.setStyle("-fx-font-weight: bold;");
        libelleLabel.setPrefWidth(100);

        Label valeurLabel = new Label(valeur);
        HBox.setHgrow(valeurLabel, Priority.ALWAYS);

        hbox.getChildren().addAll(libelleLabel, valeurLabel);
        return hbox;
    }

    private HBox createEtudiantsSelector() {
        HBox container = new HBox(15);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(10));
        container.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #dddddd; -fx-border-radius: 5;");

        Label label = new Label("Sélectionner un étudiant:");
        label.setStyle("-fx-font-weight: bold;");

        ComboBox<String> etudiantComboBox = new ComboBox<>();
        ObservableList<String> observableEtudiants = FXCollections.observableArrayList();

        for (Etudiant etudiant : listeEtudiants) {
            observableEtudiants.add(etudiant.getNom() + " " + etudiant.getPrenom() + " (" + etudiant.getId() + ")");
        }

        etudiantComboBox.setItems(observableEtudiants);
        etudiantComboBox.setPromptText("Choisir un étudiant");

        etudiantComboBox.setOnAction(event -> {
            String etudiantSelectionne = etudiantComboBox.getValue();
            if (etudiantSelectionne != null) {
                String id = etudiantSelectionne.substring(
                        etudiantSelectionne.lastIndexOf("(") + 1,
                        etudiantSelectionne.lastIndexOf(")")
                );
                Etudiant etudiant = csvService.getEtudiantById(id);
                if (etudiant != null) {
                    afficherDetailsEtudiant(etudiant);
                }
            }
        });

        Button btnListeEtudiants = new Button("Liste complète des étudiants");
        btnListeEtudiants.setStyle("-fx-background-color: #4a87e8; -fx-text-fill: white;");

        btnListeEtudiants.setOnAction(event -> {
            ouvrirListeEtudiants();
        });

        container.getChildren().addAll(label, etudiantComboBox, btnListeEtudiants);
        return container;
    }

    private HBox createEnseignantsSelector() {
        HBox container = new HBox(15);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(10));
        container.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #dddddd; -fx-border-radius: 5;");

        Label label = new Label("Sélectionner un enseignant:");
        label.setStyle("-fx-font-weight: bold;");

        ComboBox<String> enseignantComboBox = new ComboBox<>();
        ObservableList<String> observableEnseignant = FXCollections.observableArrayList();

        for (Enseignant enseignant : listeEnseignants) {
            observableEnseignant.add(enseignant.getNom() + " " + enseignant.getPrenom() + " (" + enseignant.getId() + ")");
        }

        enseignantComboBox.setItems(observableEnseignant);
        enseignantComboBox.setPromptText("Choisir un enseignant");

        enseignantComboBox.setOnAction(event -> {
            String enseignantSelectionne = enseignantComboBox.getValue();
            if (enseignantSelectionne != null) {
                String id = enseignantSelectionne.substring(
                        enseignantSelectionne.lastIndexOf("(") + 1,
                        enseignantSelectionne.lastIndexOf(")")
                );
                Enseignant enseignant = csvService.getEnseignantById(id);
                if (enseignant != null) {
                    System.out.println("Enseignant sélectionné: " + enseignant.getNom() + " " + enseignant.getPrenom());
                    afficherDetailsEnseignant(enseignant);
                }
            }
        });

        Button btnListeEnseignants = new Button("Liste complète des enseignants");
        btnListeEnseignants.setStyle("-fx-background-color: #4a87e8; -fx-text-fill: white;");

        btnListeEnseignants.setOnAction(event -> {
            ouvrirListeEtudiants();
        });

        container.getChildren().addAll(label, enseignantComboBox, btnListeEnseignants);
        return container;
    }

    private void afficherDetailsEtudiant(Etudiant etudiant) {
        VBox contentContainer = (VBox) scrollPane.getContent().lookup("#etudiantInfoContainer");
        if (contentContainer == null) return;

        contentContainer.getChildren().clear();

        VBox infoContainer = new VBox(10);
        infoContainer.setPadding(new Insets(15));
        infoContainer.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #4a87e8; -fx-border-radius: 5;");

        Label titreLabel = new Label("Informations de l'étudiant");
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox detailsContainer = new VBox(8);
        detailsContainer.setPadding(new Insets(10, 0, 0, 0));

        detailsContainer.getChildren().addAll(
                createInfoLine("Identifiant", etudiant.getId()),
                createInfoLine("Nom", etudiant.getNom()),
                createInfoLine("Prénom", etudiant.getPrenom()),
                createInfoLine("Email", etudiant.getMail()),
                createInfoLine("Classe", etudiant.getClasse())
        );

        Button btnEdtEtudiant = new Button("Voir l'emploi du temps");
        btnEdtEtudiant.setStyle("-fx-background-color: #4a87e8; -fx-text-fill: white;");
        btnEdtEtudiant.setPrefWidth(200);

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        buttonContainer.getChildren().add(btnEdtEtudiant);

        btnEdtEtudiant.setOnAction(e -> {
            afficherEmploiDuTempsEtudiant(etudiant);
        });

        infoContainer.getChildren().addAll(titreLabel, detailsContainer, buttonContainer);
        contentContainer.getChildren().add(infoContainer);

        VBox edtContainer = new VBox(10);
        edtContainer.setId("edtContainer");
        edtContainer.setPadding(new Insets(15, 0, 0, 0));
        contentContainer.getChildren().add(edtContainer);
    }

    private void afficherDetailsEnseignant(Enseignant enseignant) {
        VBox contentContainer = (VBox) scrollPane.getContent().lookup("#enseignantInfoContainer");
        if (contentContainer == null) return;

        contentContainer.getChildren().clear();

        VBox infoContainer = new VBox(10);
        infoContainer.setPadding(new Insets(15));
        infoContainer.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #4a87e8; -fx-border-radius: 5;");

        Label titreLabel = new Label("Informations de l'enseignants");
        titreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox detailsContainer = new VBox(8);
        detailsContainer.setPadding(new Insets(10, 0, 0, 0));

        detailsContainer.getChildren().addAll(
                createInfoLine("Identifiant", enseignant.getId()),
                createInfoLine("Nom", enseignant.getNom()),
                createInfoLine("Prénom", enseignant.getPrenom()),
                createInfoLine("Email", enseignant.getMail())
        );

        Button btnEdtEnseignant = new Button("Voir l'emploi du temps");
        btnEdtEnseignant.setStyle("-fx-background-color: #4a87e8; -fx-text-fill: white;");
        btnEdtEnseignant.setPrefWidth(200);

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        buttonContainer.getChildren().add(btnEdtEnseignant);

        btnEdtEnseignant.setOnAction(e -> {
            afficherEmploiDuTempsEnseignant(enseignant);
        });

        infoContainer.getChildren().addAll(titreLabel, detailsContainer, buttonContainer);
        contentContainer.getChildren().add(infoContainer);

        VBox edtContainer = new VBox(10);
        edtContainer.setId("edtContainer");
        edtContainer.setPadding(new Insets(15, 0, 0, 0));
        contentContainer.getChildren().add(edtContainer);
    }

    private void afficherEmploiDuTempsEtudiant(Etudiant etudiant) {
        List<Cours> coursEtudiant = csvService.CoursEtudiant(etudiant.getId());

        VBox edtContainer = (VBox) scrollPane.getContent().lookup("#edtContainer");
        if (edtContainer == null) return;

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

        VBox edtContainer = (VBox) scrollPane.getContent().lookup("#edtContainer");
        if (edtContainer == null) return;

        edtContainer.getChildren().clear();

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

        edtContainer.getChildren().add(container);
    }

    private void ouvrirListeEtudiants() {
        // Créer une fenêtre popup
        Popup popup = new Popup();
        popup.setAutoHide(true);

        VBox popupContent = new VBox(10);
        popupContent.setPadding(new Insets(15));
        popupContent.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        popupContent.setMinWidth(400);
        popupContent.setMaxHeight(500);

        Label titleLabel = new Label("Liste des étudiants");
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Créer une liste avec les noms complets des étudiants
        ListView<String> listView = new ListView<>();
        ObservableList<String> observableItems = FXCollections.observableArrayList();

        for (Etudiant etudiant : listeEtudiants) {
            observableItems.add(etudiant.getNom() + " " + etudiant.getPrenom() + " - " + etudiant.getClasse() + " (" + etudiant.getId() + ")");
        }

        listView.setItems(observableItems);
        VBox.setVgrow(listView, Priority.ALWAYS);

        Button btnSelectionner = new Button("Sélectionner");
        btnSelectionner.setStyle("-fx-background-color: #4a87e8; -fx-text-fill: white;");

        btnSelectionner.setOnAction(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                // Extraire l'ID de l'étudiant sélectionné
                String id = selected.substring(selected.lastIndexOf("(") + 1, selected.lastIndexOf(")"));
                Etudiant etudiant = csvService.getEtudiantById(id);

                if (etudiant != null) {
                    System.out.println("Étudiant sélectionné depuis la liste: " + etudiant.getNom() + " " + etudiant.getPrenom());
                    afficherDetailsEtudiant(etudiant);
                }
                popup.hide();
            }
        });

        Button btnFermer = new Button("Fermer");
        btnFermer.setOnAction(e -> popup.hide());

        HBox buttonsBox = new HBox(10, btnSelectionner, btnFermer);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        popupContent.getChildren().addAll(titleLabel, listView, buttonsBox);

        popup.getContent().add(popupContent);

        // Afficher la popup à côté du bouton
        Window window = scrollPane.getScene().getWindow();
        popup.show(window, window.getX() + 100, window.getY() + 100);
    }

    private void ouvrirListeEnseignants() {
        Popup popup = new Popup();
        popup.setAutoHide(true);

        VBox popupContent = new VBox(10);
        popupContent.setPadding(new Insets(15));
        popupContent.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        popupContent.setMinWidth(400);
        popupContent.setMaxHeight(500);

        Label titleLabel = new Label("Liste des enseignants");
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ListView<String> listView = new ListView<>();
        ObservableList<String> observableItems = FXCollections.observableArrayList();

        for (Enseignant enseignant : listeEnseignants) {
            observableItems.add(enseignant.getNom() + " " + enseignant.getPrenom() + " (" + enseignant.getId() + ")");
        }

        listView.setItems(observableItems);
        VBox.setVgrow(listView, Priority.ALWAYS);

        Button btnSelectionner = new Button("Sélectionner");
        btnSelectionner.setStyle("-fx-background-color: #4a87e8; -fx-text-fill: white;");

        btnSelectionner.setOnAction(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String id = selected.substring(selected.lastIndexOf("(") + 1, selected.lastIndexOf(")"));
                Enseignant enseignant = csvService.getEnseignantById(id);

                if (enseignant != null) {
                    afficherDetailsEnseignant(enseignant);
                }
                popup.hide();
            }
        });

        Button btnFermer = new Button("Fermer");
        btnFermer.setOnAction(e -> popup.hide());

        HBox buttonsBox = new HBox(10, btnSelectionner, btnFermer);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        popupContent.getChildren().addAll(titleLabel, listView, buttonsBox);

        popup.getContent().add(popupContent);

        Window window = scrollPane.getScene().getWindow();
        popup.show(window, window.getX() + 100, window.getY() + 100);
    }
}