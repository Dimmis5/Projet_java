package org.example.projet_java.service;

import org.example.projet_java.model.*;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvService {
    private static final String CSV_ADMINISTRATEUR = "CSV_Java/Administrateur.csv";
    private static final String CSV_COURS = "CSV_Java/Cours.csv";
    private static final String CSV_EDT = "CSV_Java/edt.csv";
    private static final String CSV_ENSEIGNANT = "CSV_Java/Enseignant.csv";
    private static final String CSV_ETUDIANT = "CSV_Java/Etudiant.csv";
    private static final String CSV_NOTIFICATION = "CSV_Java/Notification.csv";
    private static final String CSV_SALLE = "CSV_Java/Salle.csv";
    private static final String CSV_CLASSE = "CSV_Java/Classe.csv";

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
                if (values.length >= 6) {
                    Etudiant etudiant = new Etudiant(values[0], values[1], values[2], values[3], values[4], values[5]);
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
            br.readLine();
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

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_EDT))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2 && values[0].trim().equals(id_etudiant.trim())) {
                    String id_cours = values[1].trim();

                    try (BufferedReader br2 = new BufferedReader(new FileReader(CSV_COURS))) {
                        br2.readLine();
                        String line2;
                        while ((line2 = br2.readLine()) != null) {
                            String[] values2 = line2.split(",");
                            if (values2.length >= 8 && values2[0].trim().equals(id_cours)) {
                                boolean estAnnule = false;
                                if (values2.length >= 9) {
                                    String statutStr = values2[8].trim();
                                    estAnnule = Boolean.parseBoolean(statutStr);
                                }
                                Cours c = new Cours(
                                        values2[0].trim(),
                                        values2[1].trim(),
                                        values2[2].trim(),
                                        values2[3].trim(),
                                        values2[4].trim(),
                                        values2[5].trim(),
                                        values2[6].trim(),
                                        values2[7].trim(),
                                        estAnnule);
                                cours.add(c);
                                break;
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
                String[] values = line.split(",");
                if (values.length >= 8 && values[6].equals(id_enseignant)) {
                    boolean estAnnule = values.length >= 9 && Boolean.parseBoolean(values[8].trim());

                    Cours c = new Cours(values[0].trim(), values[1].trim(), values[2].trim(), values[3].trim(), values[4].trim(), values[5].trim(), values[6].trim(), values[7].trim(), estAnnule);
                    cours.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cours;
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
                    found = true;
                    continue;
                }
                bw.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (!file.delete() || !tempFile.renameTo(file)) {
            return false;
        }

        return true;
    }

    public boolean ajouterCours(Cours nouveauCours) {
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

        List<String> lignesEdt = new ArrayList<>();
        for (Etudiant etudiant : getEtudiantsParClasse(nouveauCours.getClasse())) {
            lignesEdt.add(etudiant.getId() + "," + nouveauCours.getId_cours());
        }

        try {
            ecrireLigne(CSV_COURS, ligneCours, "id_cours,id_salle,matiere,date,heure_debut,heure_fin,id_enseignant,classe");

            for (String ligne : lignesEdt) {
                ecrireLigne(CSV_EDT, ligne, "id_etudiant,id_cours");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<Etudiant> getEtudiantsParClasse(String classe) {
        List<Etudiant> result = new ArrayList<>();
        for (Etudiant etudiant : Etudiants()) {
            if (etudiant.getClasse().equals(classe)) {
                result.add(etudiant);
            }
        }
        return result;
    }

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

    public List<String> getEnseignantIds() {
        List<String> ids = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_ENSEIGNANT))) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ids;
    }

    public List<Cours> lireTousLesCours() {
        List<Cours> cours = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_COURS))) {
            String ligne;
            boolean premiereLigne = true;
            while ((ligne = br.readLine()) != null) {
                if (premiereLigne) {
                    premiereLigne = false;
                    continue;
                }
                String[] valeurs = ligne.split(",");
                if (valeurs.length >= 8) {
                    boolean estAnnule = false;
                    if (valeurs.length >= 9) {
                        estAnnule = Boolean.parseBoolean(valeurs[8]);
                    }

                    Cours c = new Cours(valeurs[0], valeurs[1], valeurs[2], valeurs[3], valeurs[4], valeurs[5], valeurs[6], valeurs[7], estAnnule);
                    cours.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cours;
    }

    public boolean reecrireTousLesCours(List<Cours> cours) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_COURS))) {
            bw.write("id_cours,id_salle,matiere,date,heure_debut,heure_fin,id_enseignant,classe,estAnnule");
            bw.newLine();

            for (Cours c : cours) {
                String ligne = String.join(",",
                        c.getId_cours(),
                        c.getId_salle(),
                        c.getMatiere(),
                        c.getDate(),
                        c.getHeure_debut(),
                        c.getHeure_fin(),
                        c.getId_enseignant(),
                        c.getClasse(),
                        String.valueOf(c.isAnnulation())
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
        List<Cours> tousLesCours = lireTousLesCours();

        int index = -1;
        for (int i = 0; i < tousLesCours.size(); i++) {
            if (tousLesCours.get(i).getId_cours().equals(coursModifie.getId_cours())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return false;
        }

        tousLesCours.set(index, coursModifie);

        return reecrireTousLesCours(tousLesCours);
    }

    public boolean isSalleDisponible(String idSalle, String date, String heureDebut, String heureFin, String idCoursExclu) {
        List<Cours> tousLesCours = lireTousLesCours();

        for (Cours cours : tousLesCours) {
            if (idCoursExclu != null && cours.getId_cours().equals(idCoursExclu)) {
                continue;
            }

            if (cours.getId_salle().equals(idSalle) && cours.getDate().equals(date)) {
                if (plagesHorairesSeChevauchent(cours.getHeure_debut(), cours.getHeure_fin(), heureDebut, heureFin)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSalleDisponible(String idSalle, String date, String heureDebut, String heureFin) {
        return isSalleDisponible(idSalle, date, heureDebut, heureFin, null);
    }

    private boolean plagesHorairesSeChevauchent(String debut1, String fin1, String debut2, String fin2) {
        try {
            debut1 = debut1.replace("h", ":");
            fin1 = fin1.replace("h", ":");
            debut2 = debut2.replace("h", ":");
            fin2 = fin2.replace("h", ":");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
            LocalTime d1 = LocalTime.parse(debut1, formatter);
            LocalTime f1 = LocalTime.parse(fin1, formatter);
            LocalTime d2 = LocalTime.parse(debut2, formatter);
            LocalTime f2 = LocalTime.parse(fin2, formatter);

            return !(f1.isBefore(d2) || f2.isBefore(d1));
        } catch (Exception e) {
            System.err.println("Erreur lors de la comparaison des horaires: " + e.getMessage());
            return true;
        }
    }


    public boolean modifierStatutAnnulationCours(String idCours, boolean nouvelEtat) {
        List<String> lignes = new ArrayList<>();
        boolean trouve = false;

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_COURS))) {
            String ligne = br.readLine();
            if (ligne != null) {
                lignes.add(ligne);
                System.out.println("Header: " + ligne);
            }

            while ((ligne = br.readLine()) != null) {
                String[] values = ligne.split(",");

                if (values.length >= 8 && values[0].trim().equals(idCours.trim())) {
                    trouve = true;

                    StringBuilder nouvelleLigne = new StringBuilder();

                    for (int i = 0; i < 8; i++) {
                        if (i > 0) nouvelleLigne.append(",");
                        nouvelleLigne.append(values[i].trim());
                    }

                    nouvelleLigne.append(",").append(nouvelEtat);

                    lignes.add(nouvelleLigne.toString());
                    System.out.println("Ancienne ligne: " + ligne);
                    System.out.println("Nouvelle ligne: " + nouvelleLigne.toString());
                } else {
                    lignes.add(ligne);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lecture: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        if (!trouve) {
            try (BufferedReader br = new BufferedReader(new FileReader(CSV_COURS))) {
                br.readLine();
                String ligne;
                while ((ligne = br.readLine()) != null) {
                    String[] values = ligne.split(",");
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture des IDs: " + e.getMessage());
            }

            return false;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_COURS))) {
            for (String ligne : lignes) {
                pw.println(ligne);
            }
            pw.flush();
        } catch (IOException e) {
            System.err.println("Erreur écriture: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_COURS))) {
            br.readLine(); // skip header
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] values = ligne.split(",");
                if (values.length >= 1 && values[0].trim().equals(idCours.trim())) {
                    if (values.length >= 9) {
                        boolean statutFinal = Boolean.parseBoolean(values[8].trim());
                        return statutFinal == nouvelEtat;
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur vérification: " + e.getMessage());
        }
        return true;
    }

    public List<Classe> Classes() {
        List<Classe> classes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_CLASSE))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2) {
                    Classe classe = new Classe(values[0].trim(), values[1].trim());
                    classes.add(classe);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    public List<Salle> Salles() {
        List<Salle> salles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_SALLE))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    String id_salle = values[0];
                    String localisation = values[1];
                    int capacite = Integer.parseInt(values[2]);

                    Salle salle = new Salle(id_salle, localisation, capacite);

                    salles.add(salle);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return salles;
    }



    public int getEffectifClasse(String nomClasse) {
        int effectif = 0;
        System.out.println("Recherche de l'effectif pour la classe: '" + nomClasse + "'");

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_CLASSE))) {
            br.readLine();
            String ligne;
            int numeroLigne = 1;

            while ((ligne = br.readLine()) != null) {
                numeroLigne++;

                String[] valeurs = ligne.split(",");
                if (valeurs.length >= 2) {
                    String classe = valeurs[0].trim();

                    if (classe.equalsIgnoreCase(nomClasse)) {
                        try {
                            effectif = Integer.parseInt(valeurs[1].trim());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Erreur de conversion pour l'effectif: " + valeurs[1]);
                        }
                    }
                } else {
                    System.out.println("Ligne ignorée (pas assez de colonnes): " + ligne);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return effectif;
    }
}
