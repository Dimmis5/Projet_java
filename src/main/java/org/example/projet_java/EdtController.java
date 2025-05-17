package org.example.projet_java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EdtController {
    private final String CSV = "C:\\Users\\Oriane\\OneDrive - ISEP\\ALGORITHMIQUE ET PROGRAMMATION\\csv.csv";

    @FXML
    public void connexionetudiant(ActionEvent event) {
        Label identifiant = new Label("Identifiant");
        TextField textidentifiant = new TextField();

        Label mdp = new Label("Mot de passe");
        PasswordField textmdp = new PasswordField();

        Button connexion = new Button("Se connecter");

        connexion.setOnAction(e -> {
            String id_etudiant = textidentifiant.getText();
            String mdp_etudiant = textmdp.getText();

            try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] valeurs = line.split(";");
                    if (valeurs[0].equals(id_etudiant) && valeurs[4].equals(mdp_etudiant)) {
                        edtEtudiant(id_etudiant);
                        break;
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });

        HBox hidentifiant = new HBox(20);
        hidentifiant.setAlignment(Pos.CENTER);
        hidentifiant.getChildren().addAll(identifiant, textidentifiant);

        HBox hmdp = new HBox(20);
        hmdp.setAlignment(Pos.CENTER);
        hmdp.getChildren().addAll(mdp, textmdp);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(hidentifiant, hmdp, connexion);

        Scene scene = new Scene(layout, 300, 200);
        Stage stage = new Stage();
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void connexionenseignant(ActionEvent event) {
        Label identifiant = new Label("Identifiant");
        TextField textidentifiant = new TextField();

        Label mdp = new Label("Mot de passe");
        PasswordField textmdp = new PasswordField();

        Button connexion = new Button("Se connecter");

        connexion.setOnAction(e -> {
            String id_enseignant = textidentifiant.getText();
            String mdp_enseignant = textmdp.getText();

            try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] valeurs = line.split(";");
                    if (valeurs[16].equals(id_enseignant) && valeurs[20].equals(mdp_enseignant)) {
                        edtEnseignant(id_enseignant);
                        break;
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });

        HBox hidentifiant = new HBox(20);
        hidentifiant.setAlignment(Pos.CENTER);
        hidentifiant.getChildren().addAll(identifiant, textidentifiant);

        HBox hmdp = new HBox(20);
        hmdp.setAlignment(Pos.CENTER);
        hmdp.getChildren().addAll(mdp, textmdp);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(hidentifiant, hmdp, connexion);

        Scene scene = new Scene(layout, 300, 200);
        Stage stage = new Stage();
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }


    @FXML
    public void edtEnseignant(String id_enseignant) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));

        LocalDate aujourdhui = LocalDate.now();
        LocalDate lundi = aujourdhui.with(DayOfWeek.MONDAY);
        List<String> semaine = new ArrayList<>();
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            semaine.add(lundi.plusDays(i).format(dateformatter));
        }

        String[] heures = {"8h00", "9h00", "10h00", "11h00", "12h00", "13h00", "14h00", "15h00", "16h00", "17h00", "18h00", "19h00", "20h00"};

        Label titreSemaine = new Label("Semaine du " + semaine.get(0) + " au " + semaine.get(6));
        layout.getChildren().add(titreSemaine);

        List<String[]> coursList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] cours = line.split(";");
                if (cours[16].equals(id_enseignant)) {
                    coursList.add(cours);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridPane agendaGrid = new GridPane();
        agendaGrid.setHgap(10);
        agendaGrid.setVgap(10);
        agendaGrid.setPadding(new Insets(10));

        for (int i = 0; i < 6; i++) {
            Label jourLabel = new Label(semaine.get(i));
            agendaGrid.add(jourLabel, i + 1, 0);
        }

        for (int heure = 0; heure <= 12; heure++) {
            Label heureLabel = new Label(heures[heure]);
            agendaGrid.add(heureLabel, 0, heure + 1);
        }

        Button[][] cellules = new Button[6][13];
        for (int jour = 0; jour < 6; jour++) {
            for (int heure = 0; heure <= 12; heure++) {
                Button celluleVide = new Button();
                celluleVide.setPrefWidth(100);
                celluleVide.setPrefHeight(30);
                agendaGrid.add(celluleVide, jour + 1, heure + 1);
                cellules[jour][heure] = celluleVide;
            }
        }

        for (String[] cours : coursList) {
            String dateCours = cours[12];
            String heureCours = cours[13];
            String heurefinCours = cours[14];
            String matiere = cours[11];
            String classe = cours[25];
            String salle = cours[6];

            int jourIndex = -1;
            for (int i = 0; i < semaine.size(); i++) {
                if (semaine.get(i).equals(dateCours)) {
                    jourIndex = i;
                    break;
                }
            }

            int heureIndex = -1;
            for (int i = 0; i < heures.length; i++) {
                if (heures[i].equals(heureCours)) {
                    heureIndex = i;
                    break;
                }
            }

            if (jourIndex >= 0 && jourIndex < 6 && heureIndex >= 0 && heureIndex <= 12) {
                Button coursButton = new Button(matiere);
                coursButton.setPrefWidth(100);
                coursButton.setPrefHeight(30);

                agendaGrid.getChildren().remove(cellules[jourIndex][heureIndex]);
                agendaGrid.add(coursButton, jourIndex + 1, heureIndex + 1);

                final String matiereInfo = matiere;
                coursButton.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Détails du cours");
                    alert.setHeaderText("Cours de " + matiereInfo);
                    alert.setContentText("Date : " + dateCours
                            + "\nHeure : " + heureCours + " - " + heurefinCours
                            + "\nClasse : " + classe
                            + "\nSalle : " + salle);
                    alert.showAndWait();
                });
            }
        }

        layout.getChildren().add(agendaGrid);

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 700, 600);
        Stage stage = new Stage();
        stage.setTitle("Emploi du Temps - " + id_enseignant);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void edtEtudiant(String id_etudiant) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));

        LocalDate aujourdhui = LocalDate.now();
        LocalDate lundi = aujourdhui.with(DayOfWeek.MONDAY);
        List<String> semaine = new ArrayList<>();
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            semaine.add(lundi.plusDays(i).format(dateformatter));
        }

        String[] heures = {"8h00", "9h00", "10h00", "11h00", "12h00", "13h00", "14h00", "15h00", "16h00", "17h00", "18h00", "19h00", "20h00"};

        Label titreSemaine = new Label("Semaine du " + semaine.get(0) + " au " + semaine.get(6));
        layout.getChildren().add(titreSemaine);

        List<String[]> coursList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] cours = line.split(";");
                if (cours[0].equals(id_etudiant)) {
                    coursList.add(cours);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridPane agendaGrid = new GridPane();
        agendaGrid.setHgap(10);
        agendaGrid.setVgap(10);
        agendaGrid.setPadding(new Insets(10));

        for (int i = 0; i < 6; i++) {
            Label jourLabel = new Label(semaine.get(i));
            agendaGrid.add(jourLabel, i + 1, 0);
        }

        for (int heure = 0; heure <= 12; heure++) {
            Label heureLabel = new Label(heures[heure]);
            agendaGrid.add(heureLabel, 0, heure + 1);
        }

        Button[][] cellules = new Button[6][13];
        for (int jour = 0; jour < 6; jour++) {
            for (int heure = 0; heure <= 12; heure++) {
                Button celluleVide = new Button();
                celluleVide.setPrefWidth(100);
                celluleVide.setPrefHeight(30);
                agendaGrid.add(celluleVide, jour + 1, heure + 1);
                cellules[jour][heure] = celluleVide;
            }
        }

        for (String[] cours : coursList) {
            String dateCours = cours[12];
            String heureCours = cours[13];
            String heurefinCours = cours[14];
            String matiere = cours[11];
            String enseignant_nom = cours[17];
            String enseignant_prenom = cours[18];
            String salle = cours[6];

            int jourIndex = -1;
            for (int i = 0; i < semaine.size(); i++) {
                if (semaine.get(i).equals(dateCours)) {
                    jourIndex = i;
                    break;
                }
            }

            int heureIndex = -1;
            for (int i = 0; i < heures.length; i++) {
                if (heures[i].equals(heureCours)) {
                    heureIndex = i;
                    break;
                }
            }

            if (jourIndex >= 0 && jourIndex < 6 && heureIndex >= 0 && heureIndex <= 12) {
                Button coursButton = new Button(matiere);
                coursButton.setPrefWidth(100);
                coursButton.setPrefHeight(30);

                agendaGrid.getChildren().remove(cellules[jourIndex][heureIndex]);
                agendaGrid.add(coursButton, jourIndex + 1, heureIndex + 1);

                final String matiereInfo = matiere;
                coursButton.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Détails du cours");
                    alert.setHeaderText("Cours de " + matiereInfo);
                    alert.setContentText("Date : " + dateCours
                            + "\nHeure : " + heureCours + " - " + heurefinCours
                            + "\nEnseignant : " + enseignant_nom + " " + enseignant_prenom
                            + "\nSalle : " + salle);

                    alert.showAndWait();
                });
            }
        }

        layout.getChildren().add(agendaGrid);

        Label date = new Label("Date : ");
        TextField text_date = new TextField();

        HBox date_notification = new HBox(10);
        date_notification.getChildren().addAll(date, text_date);

        Label message = new Label("Message : ");
        TextField text_message = new TextField();

        HBox message_notification = new HBox(10);
        message_notification.getChildren().addAll(message, text_message);

        VBox VNotification = new VBox(10);
        VNotification.getChildren().addAll(date_notification, message_notification);

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 700, 600);
        Stage stage = new Stage();
        stage.setTitle("Emploi du Temps - " + id_etudiant);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void connexionadministrateur(ActionEvent event) {
        Label identifiant = new Label("Identifiant");
        TextField textidentifiant = new TextField();

        Label mdp = new Label("Mot de passe");
        PasswordField textmdp = new PasswordField();

        Button connexion = new Button("Se connecter");

        connexion.setOnAction(e -> {
            String id_administrateur = textidentifiant.getText();
            String mdp_adm = textmdp.getText();

            try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] valeurs = line.split(";");
                    if (valeurs[25].equals(id_administrateur) && valeurs[29].equals(mdp_adm)) {
                        edtAdmi(id_administrateur);
                        break;
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });

        HBox hidentifiant = new HBox(20);
        hidentifiant.setAlignment(Pos.CENTER);
        hidentifiant.getChildren().addAll(identifiant, textidentifiant);

        HBox hmdp = new HBox(20);
        hmdp.setAlignment(Pos.CENTER);
        hmdp.getChildren().addAll(mdp, textmdp);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(hidentifiant, hmdp, connexion);

        Scene scene = new Scene(layout, 300, 200);
        Stage stage = new Stage();
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void edtAdmi(String id_administrateur) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));


        HBox semainesSection = new HBox(10);
        semainesSection.setAlignment(Pos.TOP_LEFT);

        ComboBox<String> etudiantsComboBox = new ComboBox<>();
        etudiantsComboBox.setPromptText("Choisir un étudiant");


        LocalDate aujourdhui = LocalDate.now();
        LocalDate lundi = aujourdhui.with(DayOfWeek.MONDAY);
        List<String> semaine = new ArrayList<>();
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            semaine.add(lundi.plusDays(i).format(dateformatter));
        }


        try (BufferedReader br = new BufferedReader(new FileReader("/home/dimmis/Téléchargements/csv (1).csv"))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] tokens = line.split(";");
                if (tokens.length >= 3) {
                    String nom = tokens[1].trim();
                    String prenom = tokens[2].trim();
                    etudiantsComboBox.getItems().add(prenom + " " + nom);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        semainesSection.getChildren().add(etudiantsComboBox);


        String[] heures = {"8h00", "9h00", "10h00", "11h00", "12h00", "13h00", "14h00", "15h00", "16h00", "17h00", "18h00", "19h00", "20h00"};

        Label titreSemaine = new Label("Semaine du " + semaine.get(0) + " au " + semaine.get(6));
        layout.getChildren().add(titreSemaine);

        List<String[]> coursList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] cours = line.split(";");
                if (cours[0].equals(id_administrateur)) {
                    coursList.add(cours);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridPane agendaGrid = new GridPane();
        agendaGrid.setHgap(10);
        agendaGrid.setVgap(10);
        agendaGrid.setPadding(new Insets(10));

        for (int i = 0; i < 6; i++) {
            Label jourLabel = new Label(semaine.get(i));
            agendaGrid.add(jourLabel, i + 1, 0);
        }

        for (int heure = 0; heure <= 12; heure++) {
            Label heureLabel = new Label(heures[heure]);
            agendaGrid.add(heureLabel, 0, heure + 1);
        }

        Button[][] cellules = new Button[6][13];
        for (int jour = 0; jour < 6; jour++) {
            for (int heure = 0; heure <= 12; heure++) {
                Button celluleVide = new Button();
                celluleVide.setPrefWidth(100);
                celluleVide.setPrefHeight(30);
                agendaGrid.add(celluleVide, jour + 1, heure + 1);
                cellules[jour][heure] = celluleVide;
            }
        }

        layout.getChildren().add(semainesSection);
        layout.getChildren().add(agendaGrid);

        Scene scene = new Scene(layout, 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Emploi du Temps");
        stage.setScene(scene);
        stage.show();
    }
}
