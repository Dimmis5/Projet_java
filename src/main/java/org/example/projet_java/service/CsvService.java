package org.example.projet_java.service;

import org.example.projet_java.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvService {
    private static final String CSV_FILE_PATH = "src/main/resources/org/example/projet_java/data/edt.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H'h'mm");

    // Singleton instance
    private static CsvService instance;

    private CsvService() {}

    public static CsvService getInstance() {
        if (instance == null) {
            instance = new CsvService();
        }
        return instance;
    }

    /**
     * Charge tous les étudiants depuis le fichier CSV
     */
    public List<Etudiant> loadEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                // Vérifier que les valeurs existent aux positions attendues
                if (values.length >= 5) {
                    String id = values[0];
                    String nom = values[1];
                    String prenom = values[2];
                    String email = values[3];
                    String motDePasse = values[4];

                    Etudiant etudiant = new Etudiant(id, nom, prenom, email, motDePasse);
                    etudiants.add(etudiant);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return etudiants;
    }

    /**
     * Charge tous les enseignants depuis le fichier CSV
     */
    public List<Enseignant> loadEnseignants() {
        List<Enseignant> enseignants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                // Vérifier que les valeurs existent aux positions attendues pour les enseignants
                if (values.length >= 21) {
                    String id = values[16];
                    String nom = values[17];
                    String prenom = values[18];
                    String email = values[19];
                    String motDePasse = values[20];

                    Enseignant enseignant = new Enseignant(id, nom, prenom, email, motDePasse);
                    enseignants.add(enseignant);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return enseignants;
    }

    /**
     * Charge tous les administrateurs depuis le fichier CSV
     */
    public List<Administrateur> loadAdministrateurs() {
        List<Administrateur> administrateurs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                // Vérifier que les valeurs existent aux positions attendues pour les administrateurs
                if (values.length >= 30) {
                    String id = values[25];
                    String nom = values[26];
                    String prenom = values[27];
                    String email = values[28];
                    String motDePasse = values[29];

                    Administrateur administrateur = new Administrateur(id, nom, prenom, email, motDePasse);
                    administrateurs.add(administrateur);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return administrateurs;
    }

    /**
     * Charge les cours pour un étudiant spécifique
     */
    public List<Cours> loadCoursForEtudiant(String etudiantId) {
        List<Cours> cours = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                // Vérifier si la ligne concerne l'étudiant spécifié
                if (values.length > 16 && values[0].equals(etudiantId)) {
                    String coursId = values[5];
                    String salle = values[6];
                    String matiere = values[11];

                    // Parser la date et les heures
                    LocalDate date = LocalDate.parse(values[12], DATE_FORMATTER);
                    LocalTime heureDebut = LocalTime.parse(values[13], TIME_FORMATTER);
                    LocalTime heureFin = LocalTime.parse(values[14], TIME_FORMATTER);

                    // Créer un enseignant pour ce cours
                    String enseignantId = values[16];
                    String nomEnseignant = values[17];
                    String prenomEnseignant = values[18];
                    Enseignant enseignant = new Enseignant(enseignantId, nomEnseignant, prenomEnseignant, "", "");

                    String classe = values[25];

                    Cours c = new Cours(coursId, matiere, salle, date, heureDebut, heureFin, enseignant, classe);

                    // Ajouter la notification si elle existe
                    /*if (values.length > 23) {
                        c.setNotification(values[23]);
                    }*/

                    cours.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cours;
    }

    /**
     * Charge les cours pour un enseignant spécifique
     */
    public List<Cours> loadCoursForEnseignant(String enseignantId) {
        List<Cours> cours = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                // Vérifier si la ligne concerne l'enseignant spécifié
                if (values.length > 16 && values[16].equals(enseignantId)) {
                    String coursId = values[5];
                    String salle = values[6];
                    String matiere = values[11];

                    // Parser la date et les heures
                    LocalDate date = LocalDate.parse(values[12], DATE_FORMATTER);
                    LocalTime heureDebut = LocalTime.parse(values[13], TIME_FORMATTER);
                    LocalTime heureFin = LocalTime.parse(values[14], TIME_FORMATTER);

                    // Créer un enseignant pour ce cours
                    String nomEnseignant = values[17];
                    String prenomEnseignant = values[18];
                    Enseignant enseignant = new Enseignant(enseignantId, nomEnseignant, prenomEnseignant, "", "");

                    String classe = values[25];

                    Cours c = new Cours(coursId, matiere, salle, date, heureDebut, heureFin, enseignant, classe);

                    // Ajouter la notification si elle existe
                    /*if (values.length > 23) {
                        c.setNotification(values[23]);
                    }*/

                    cours.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cours;
    }

    /**
     * Ajouter un nouvel étudiant
     */
    public boolean addEtudiant(Etudiant etudiant) {
        String id = String.valueOf(System.currentTimeMillis());
        etudiant.setId(id);

        String newLine = id + ";" + etudiant.getNom() + ";" + etudiant.getPrenom() + ";"
                + etudiant.getMail() + ";" + etudiant.getMdp();

        try (FileWriter writer = new FileWriter(CSV_FILE_PATH, true)) {
            writer.write(newLine + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mettre à jour la notification d'un cours
     */
    public boolean updateCoursNotification(String coursId, String notification) {
        List<String> fileContent = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String header = br.readLine();
            fileContent.add(header);

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values.length > 5 && values[5].equals(coursId)) {
                    values[23] = notification;
                    line = String.join(";", values);
                }
                fileContent.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            for (String line : fileContent) {
                bw.write(line);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}