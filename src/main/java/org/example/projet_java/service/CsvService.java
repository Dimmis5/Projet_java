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


    public Enseignant getEnseignantById(String id) {
        for (Enseignant e : Enseignants()) {
            if (e.getId().trim().equals(id.trim())) {
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
                            values[0].trim(),
                            values[1].trim(),
                            values[2].trim(),
                            values[3].trim(),
                            values[4].trim());
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
                            values[0].trim(),
                            values[1].trim(),
                            values[2].trim(),
                            values[3].trim(), // On garde la date en string
                            values[4].trim(), // heure_debut
                            values[5].trim(), // heure_fin
                            values[6].trim(),
                            values[7].trim(),
                            false);
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

    public boolean supprimerCours(String idCours) {
        boolean coursSupprime = supprimerLignesDansFichier(CSV_COURS, 0, idCours);
        boolean edtMisAJour = supprimerLignesDansFichier(CSV_EDT, 1, idCours);

        return coursSupprime && edtMisAJour;
    }

    private boolean supprimerLignesDansFichier(String fichier, int colonneId, String idCours) {
        File file = new File(fichier);
        File tempFile = new File(fichier + ".tmp");

        try (BufferedReader br = new BufferedReader(new FileReader(file));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean headerProcessed = false;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                if (!headerProcessed) {
                    bw.write(line + "\n");
                    headerProcessed = true;
                    continue;
                }

                String[] values = line.split(",");
                if (values.length > colonneId && values[colonneId].trim().equals(idCours.trim())) {
                    found = true; // On a trouvé la ligne à supprimer
                    continue; // On ne l'écrit pas dans le fichier temporaire
                }
                bw.write(line + "\n");
            }

            if (!found) {
                System.err.println("Aucune correspondance trouvée pour l'ID " + idCours + " dans " + fichier);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (!file.delete() || !tempFile.renameTo(file)) {
            System.err.println("Erreur lors du remplacement du fichier " + fichier);
            return false;
        }

        return true;
    }

    public boolean ajouterCours(Cours nouveauCours) {
        // Formatage de la ligne pour CSV_COURS
        String ligneCours = String.join(",",
                nouveauCours.getId_cours(),
                nouveauCours.getId_salle(),
                nouveauCours.getMatiere(),
                nouveauCours.getDate(),
                nouveauCours.getHeure_debut(),
                nouveauCours.getHeure_fin(),
                nouveauCours.getId_enseignant(),
                nouveauCours.getClasse()
        );

        // Formatage de la ligne pour CSV_EDT (à faire pour chaque étudiant de la classe)
        List<String> lignesEdt = new ArrayList<>();
        for (Etudiant etudiant : getEtudiantsParClasse(nouveauCours.getClasse())) {
            lignesEdt.add(etudiant.getId() + "," + nouveauCours.getId_cours());
        }

        // Écriture dans les fichiers
        try {
            // Écrire dans CSV_COURS
            ecrireLigne(CSV_COURS, ligneCours,
                    "id_cours,id_salle,matiere,date,heure_debut,heure_fin,id_enseignant,classe");

            // Écrire dans CSV_EDT
            for (String ligne : lignesEdt) {
                ecrireLigne(CSV_EDT, ligne, "id_etudiant,id_cours");
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère tous les étudiants d'une classe donnée
     */
    private List<Etudiant> getEtudiantsParClasse(String classe) {
        List<Etudiant> result = new ArrayList<>();
        for (Etudiant etudiant : Etudiants()) {
            if (etudiant.getClasse().equals(classe)) {
                result.add(etudiant);
            }
        }
        return result;
    }

    /**
     * Méthode utilitaire pour écrire une ligne dans un fichier CSV
     */
    private void ecrireLigne(String fichier, String ligne, String entete) throws IOException {
        File file = new File(fichier);
        boolean fichierExiste = file.exists() && file.length() > 0;

        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            if (!fichierExiste) {
                bw.write(entete);
                bw.newLine();
            }
            bw.newLine();
            bw.write(ligne);
            bw.newLine();
        }
    }

    /**
     * Récupère la liste des IDs des enseignants à partir du fichier CSV.
     *
     * @return Une liste contenant tous les IDs des enseignants disponibles
     */
    public List<String> getEnseignantIds() {
        List<String> ids = new ArrayList<>();
        String csvFile = "CSV_Java/Enseignant.csv"; // Ajustez le chemin selon votre structure

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 1 && data[0] != null && !data[0].trim().isEmpty()) {
                    ids.add(data[0].trim());
                }
            }

            System.out.println("[INFO] " + ids.size() + " IDs d'enseignants trouvés dans le CSV");
        } catch (IOException e) {
            System.err.println("[ERREUR] Impossible de lire le fichier CSV des enseignants: " + e.getMessage());
            e.printStackTrace();
        }

        return ids;
    }

    private List<Cours> lireTousLesCours() {
        List<Cours> cours = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_COURS))) {
            String ligne;
            boolean premiereLigne = true;
            while ((ligne = br.readLine()) != null) {
                if (premiereLigne) {
                    premiereLigne = false;
                    continue; // Saute l'en-tête
                }
                String[] valeurs = ligne.split(",");
                if (valeurs.length >= 8) {
                    Cours c = new Cours(
                            valeurs[0], // id_cours
                            valeurs[1], // salle
                            valeurs[2], // matière
                            valeurs[3], // date
                            valeurs[4], // heure_debut
                            valeurs[5], // heure_fin
                            valeurs[6], // id_enseignant
                            valeurs[7], // classe
                            Boolean.parseBoolean(valeurs[8]) // estAnnule
                    );
                    cours.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cours;
    }

    private boolean reecrireTousLesCours(List<Cours> cours) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_COURS))) {
            // Écrire l'en-tête
            bw.write("id_cours,id_salle,matiere,date,heure_debut,heure_fin,id_enseignant,classe,estAnnule");
            bw.newLine();

            // Écrire chaque cours
            for (Cours c : cours) {
                String ligne = String.join(",",
                        c.getId_cours(),
                        c.getId_salle(),
                        c.getMatiere(),
                        c.getDate(),
                        c.getHeure_debut(),
                        c.getHeure_fin(),
                        c.getId_enseignant(),
                        c.getClasse()
                );
                bw.write(ligne);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modifierCours(Cours coursModifie) {
        List<Cours> tousLesCours = lireTousLesCours(); // Méthode existante qui lit tous les cours

        // Trouver l'index du cours à modifier
        int index = -1;
        for (int i = 0; i < tousLesCours.size(); i++) {
            if (tousLesCours.get(i).getId_cours().equals(coursModifie.getId_cours())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return false; // Cours non trouvé
        }

        // Remplacer l'ancien cours par le cours modifié
        tousLesCours.set(index, coursModifie);

        // Réécrire tous les cours dans le fichier
        return reecrireTousLesCours(tousLesCours); // Méthode qui écrit la liste complète
    }
}
