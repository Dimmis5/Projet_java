package org.example.projet_java.service;

import javafx.application.Application;
import org.example.projet_java.model.Administrateur;
import org.example.projet_java.model.Enseignant;
import org.example.projet_java.model.Etudiant;
import org.example.projet_java.model.Utilisateur;

import java.util.List;

public class AuthentificationService {
    private static AuthentificationService instance;
    private CsvService csvService;
    private Utilisateur currentUser;

    private AuthentificationService() {
        csvService = CsvService.getInstance();
    }

    public static AuthentificationService getInstance() {
        if (instance == null) {
            instance = new AuthentificationService();
        }
        return instance;
    }

    public boolean loginEtudiant(String identifiant, String motDePasse) {
        List<Etudiant> etudiants = csvService.Etudiants();

        for (Etudiant etudiant : etudiants) {
            if (etudiant.getId().equals(identifiant) && etudiant.getMdp().equals(motDePasse)) {
                this.currentUser = etudiant;
                return true;
            }
        }
        return false;
    }

    public boolean loginEnseignant(String identifiant, String motDePasse) {
        List<Enseignant> enseignants = csvService.Enseignants();

        for (Enseignant enseignant : enseignants) {
            if (enseignant.getId().equals(identifiant) && enseignant.getMdp().equals(motDePasse)) {
                this.currentUser = enseignant;
                return true;
            }
        }
        return false;
    }

    public boolean loginAdministrateur(String identifiant, String motDePasse) {
        List<Administrateur> administrateurs = csvService.Administrateurs();

        for (Administrateur administrateur : administrateurs) {
            if (identifiant.equals(administrateur.getId()) && motDePasse.equals(administrateur.getMdp())) {
                this.currentUser = administrateur;
                return true;
            }
        }

        return false;
    }

    public void logout() {
        this.currentUser = null;
    }

    public Utilisateur getCurrentUser() {
        return currentUser;
    }
}