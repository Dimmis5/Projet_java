package org.example.projet_java.service;

import org.example.projet_java.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvService {
    private static final String CSV_ADMINISTRATEUR = "CSV_Java/Administrateur.csv";
    private static final String CSV_ANOMALIE = "CSV_Java/Anomalie.csv";
    private static final String CSV_COURS = "CSV_Java/Cours.csv";
    private static final String CSV_EDT = "CSV_Java/edt.csv";
    private static final String CSV_ENSEIGNANT = "CSV_Java/Enseignant.csv";
    private static final String CSV_ETUDIANT = "CSV_Java/Etudiant.csv";
    private static final String CSV_NOTIFICATION = "CSV_Java/Notification.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H'h'mm");

    private static CsvService instance;

    private CsvService() {}

    public static CsvService getInstance() {
        if (instance == null) {
            instance = new CsvService();
        }
        return instance;
    }

    public List<Etudiant> Etudiants() {
        List<Etudiant> etudiants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_ETUDIANT))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String id_etudiant = values[0];
                String nom = values[1];
                String prenom = values[2];
                String email = values[3];
                String motDePasse = values[4];
                String classe = values[5];

                Etudiant etudiant = new Etudiant(id_etudiant, nom, prenom, email, motDePasse, classe);
                etudiants.add(etudiant);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return etudiants;
    }

    public List<Enseignant> Enseignants() {
        List<Enseignant> enseignants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_ENSEIGNANT))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                String id_enseignant = values[0];
                String nom = values[1];
                String prenom = values[2];
                String email = values[3];
                String motDePasse = values[4];

                Enseignant enseignant = new Enseignant(id_enseignant, nom, prenom, email, motDePasse);
                enseignants.add(enseignant);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return enseignants;
    }

    public List<Administrateur> Administrateurs() {
        List<Administrateur> administrateurs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_ADMINISTRATEUR))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                String id_administrateur = values[0];
                String nom = values[1];
                String prenom = values[2];
                String email = values[3];
                String motDePasse = values[4];

                Administrateur administrateur = new Administrateur(id_administrateur, nom, prenom, email, motDePasse);
                administrateurs.add(administrateur);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return administrateurs;
    }

    public List<Cours> CoursEtudiant(String id_etudiant) {
        List<Cours> cours = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_EDT))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values[0].equals(id_etudiant)) {
                    String id_cours = values[1];
                    try (BufferedReader br2 = new BufferedReader(new FileReader(CSV_COURS))) {
                        String line2;
                        br2.readLine();
                        while ((line2 = br.readLine()) != null) {
                            String[] values2 = line.split(";");
                            if (values[0].equals(id_etudiant)) {
                                String id_salle = values2[1];
                                String matiere = values2[2];
                                String date = LocalDate.parse(values2[3], DATE_FORMATTER).toString();
                                String heureDebut = LocalTime.parse(values2[4], TIME_FORMATTER).toString();
                                String heureFin = LocalTime.parse(values2[5], TIME_FORMATTER).toString();
                                String id_enseignant = values2[6];
                                String classe = values2[7];
                                Cours c = new Cours(id_cours,id_salle, matiere, date, heureDebut, heureFin, id_enseignant, classe, false);
                                cours.add(c);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cours;
    }

    public List<Cours> CoursEnseignant(String id_enseignant) {
        List<Cours> cours = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_COURS))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values[6].equals(id_enseignant)) {
                    String id_cours = values[0];
                    String id_salle = values[1];
                    String matiere = values[2];
                    String date = LocalDate.parse(values[3], DATE_FORMATTER).toString();
                    String heureDebut = LocalTime.parse(values[4], TIME_FORMATTER).toString();
                    String heureFin = LocalTime.parse(values[5], TIME_FORMATTER).toString();
                    String classe = values[7];
                    Cours c = new Cours(id_cours, id_salle, matiere, date, heureDebut, heureFin, id_enseignant, classe, false);
                    cours.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cours;
    }

    public boolean addEtudiant(Etudiant etudiant) {
        String id = String.valueOf(System.currentTimeMillis());
        etudiant.setId(id);

        String newLine = id + ";" + etudiant.getNom() + ";" + etudiant.getPrenom() + ";"
                + etudiant.getMail() + ";" + etudiant.getMdp();

        try (FileWriter writer = new FileWriter(CSV_ETUDIANT, true)) {
            writer.write(newLine + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}