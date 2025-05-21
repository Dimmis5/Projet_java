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
            br.readLine(); // ignore header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    Etudiant etudiant = new Etudiant(
                            values[0], values[1], values[2], values[3], values[4], values[5]);
                    etudiants.add(etudiant);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return etudiants;
    }

    public Etudiant getEtudiantById(String id) {
        for (Etudiant e : Etudiants()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public List<Enseignant> Enseignants() {
        List<Enseignant> enseignants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_ENSEIGNANT))) {
            String line;
            br.readLine(); // ignore header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 5) {
                    Enseignant enseignant = new Enseignant(
                            values[0], values[1], values[2], values[3], values[4]);
                    enseignants.add(enseignant);
                }
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
            br.readLine(); // ignore header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 5) {
                    Administrateur admin = new Administrateur(
                            values[0], values[1], values[2], values[3], values[4]);
                    administrateurs.add(admin);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return administrateurs;
    }

    public List<Cours> CoursEtudiant(String id_etudiant) {
        List<Cours> cours = new ArrayList<>();
        System.out.println("Recherche cours pour etudiant: " + id_etudiant); // Debug

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_EDT))) {
            String line;
            br.readLine(); // ignore header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2 && values[0].trim().equals(id_etudiant.trim())) {
                    String id_cours = values[1].trim();
                    System.out.println("Trouvé cours associé: " + id_cours); // Debug

                    try (BufferedReader br2 = new BufferedReader(new FileReader(CSV_COURS))) {
                        br2.readLine(); // ignore header
                        String line2;
                        while ((line2 = br2.readLine()) != null) {
                            String[] values2 = line2.split(",");
                            if (values2.length >= 8 && values2[0].trim().equals(id_cours)) {
                                Cours c = new Cours(
                                        values2[0].trim(),
                                        values2[1].trim(),
                                        values2[2].trim(),
                                        values2[3].trim(), // On garde la date en string
                                        values2[4].trim(), // heure_debut
                                        values2[5].trim(), // heure_fin
                                        values2[6].trim(),
                                        values2[7].trim(),
                                        false);
                                cours.add(c);
                                System.out.println("Cours ajouté: " + c.getMatiere()); // Debug
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Total cours trouvés: " + cours.size()); // Debug
        return cours;
    }

    public List<Cours> CoursEnseignant(String id_enseignant) {
        List<Cours> cours = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_COURS))) {
            String line;
            br.readLine(); // ignore header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8 && values[6].equals(id_enseignant)) {
                    Cours c = new Cours(
                            values[0], values[1], values[2],
                            LocalDate.parse(values[3], DATE_FORMATTER).toString(),
                            LocalTime.parse(values[4], TIME_FORMATTER).toString(),
                            LocalTime.parse(values[5], TIME_FORMATTER).toString(),
                            values[6], values[7], false);
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

        String newLine = id + "," + etudiant.getNom() + "," + etudiant.getPrenom() + ","
                + etudiant.getMail() + "," + etudiant.getMdp() + "," + etudiant.getClasse();

        try (FileWriter writer = new FileWriter(CSV_ETUDIANT, true)) {
            writer.write(newLine + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
