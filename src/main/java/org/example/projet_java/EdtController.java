package org.example.projet_java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        LocalDate dateActuelle = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateFormatee = dateActuelle.format(formatter);
        System.out.println(dateFormatee);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);

        HBox semainesSection = new HBox(10);
        semainesSection.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 30; i++) {
            final int semaineNumber = i;
            Button semaineButton = new Button(String.valueOf(i));
            // Ajouter action bouton
            semainesSection.getChildren().add(semaineButton);
        }

        layout.getChildren().add(semainesSection);

        GridPane agendaGrid = new GridPane();
        agendaGrid.setHgap(20);
        agendaGrid.setVgap(20);

        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        for (int i = 0; i < jours.length; i++) {
            Label jourLabel = new Label(jours[i]);
            agendaGrid.add(jourLabel, i + 1, 0);
        }

        for (int i = 8; i <= 20; i++) {
            Label heureLabel = new Label(i + ":00");
            agendaGrid.add(heureLabel, 0, i - 7);

            for (int j = 0; j < jours.length; j++) {
                Button cours = new Button("");
                cours.setPrefWidth(100);
                agendaGrid.add(cours, j + 1, i - 7);
            }
        }

        layout.getChildren().add(agendaGrid);

        Scene scene = new Scene(layout, 400, 400);
        Stage stage = new Stage();
        stage.setTitle("Emploi du Temps");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void edtEtudiant(String id_etudiant) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));

        Calendar calendrier = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        calendrier.set(Calendar.DAY_OF_WEEK, calendrier.MONDAY);
        Date debutSemaine = calendrier.getTime();
        calendrier.add(Calendar.DAY_OF_YEAR, 6);
        Date finSemaine = calendrier.getTime();

        Label titreSemaine = new Label("Semaine du " + sdf.format(debutSemaine) + " au " + sdf.format(finSemaine));
        layout.getChildren().add(titreSemaine);

        GridPane agendaGrid = new GridPane();
        agendaGrid.setHgap(10);
        agendaGrid.setVgap(10);
        agendaGrid.setPadding(new Insets(10));

        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        for (int i = 0; i < jours.length; i++) {
            Label jourLabel = new Label(jours[i]);
            agendaGrid.add(jourLabel, i + 1, 0);
        }

        List<String[]> coursList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] cours = line.split(";");
                if (cours.length >= 15 && cours[0].equals(id_etudiant)) {
                    coursList.add(cours);
                    System.out.println("Cours trouvé: " + cours[11] + " le " + cours[12] + " à " + cours[13]);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier CSV: " + e.getMessage());
            e.printStackTrace();
        }

        for (int heure = 8; heure <= 20; heure++) {
            Label heureLabel = new Label(heure + ":00");
            agendaGrid.add(heureLabel, 0, heure - 7);

            for (int jour = 0; jour < jours.length; jour++) {
                Button coursButton = new Button();
                coursButton.setPrefWidth(100);

                for (String[] cours : coursList) {
                    try {
                        Date dateCours = sdf.parse(cours[12]);
                        System.out.println(dateCours);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(dateCours);

                        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                        int ajustedDayIndex = (dayOfWeek + 5) % 7;

                        int heureDebut = Integer.parseInt(cours[13].replace("h", ""));
                        if (ajustedDayIndex == jour && heureDebut == heure) {
                            coursButton.setText(cours[11]);
                            System.out.println("Affichage cours: " + cours[11] + " à " + heure + "h, jour " + jours[jour]);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                agendaGrid.add(coursButton, jour + 1, heure - 7);
            }
        }

        layout.getChildren().add(agendaGrid);

        Scene scene = new Scene(layout, 400, 400);
        Stage stage = new Stage();
        stage.setTitle("Emploi du Temps");
        stage.setScene(scene);
        stage.show();
    }

        @FXML
    public void connexionadministrateur(ActionEvent event){
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
                        edtEtudiant(id_administrateur);
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
    public void edtAdmi(String id_administrateur){

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);

        HBox semainesSection = new HBox(10);
        semainesSection.setAlignment(Pos.CENTER);

        ComboBox<String> etudiantsComboBox = new ComboBox<>();
        etudiantsComboBox.getItems().addAll(
                "Étudiant 1", "Étudiant 2", "Étudiant 3", "Étudiant 4", "Étudiant 5",
                "Étudiant 6", "Étudiant 7", "Étudiant 8", "Étudiant 9", "Étudiant 10"
        );
        etudiantsComboBox.setPromptText("Choisir un étudiant");




        for (int i = 1; i <= 30; i++) {
            final int semaineNumber = i;
            Button semaineButton = new Button(String.valueOf(i));
            // Ajouter action bouton
            semainesSection.getChildren().add(semaineButton);
        }

        layout.getChildren().add(semainesSection);

        GridPane agendaGrid = new GridPane();
        agendaGrid.setHgap(20);
        agendaGrid.setVgap(20);

        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        for (int i = 0; i < jours.length; i++) {
            Label jourLabel = new Label(jours[i]);
            agendaGrid.add(jourLabel, i + 1, 0);
        }

        for (int i = 8; i <= 20; i++) {
            Label heureLabel = new Label(i + ":00");
            agendaGrid.add(heureLabel, 0, i - 7);

            for (int j = 0; j < jours.length; j++) {
                Button cours = new Button("");
                cours.setPrefWidth(100);
                agendaGrid.add(cours, j + 1, i - 7);
            }
        }

        layout.getChildren().add(agendaGrid);
        layout.getChildren().add(etudiantsComboBox);

        Scene scene = new Scene(layout, 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Emploi du Temps");
        stage.setScene(scene);
        stage.show();

    }
}
