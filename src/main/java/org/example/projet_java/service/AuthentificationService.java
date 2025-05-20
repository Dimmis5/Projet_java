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

    /**
     * Authentifie un étudiant avec email et mot de passe
     */
    public boolean loginEtudiant(String email, String motDePasse) {
        List<Etudiant> etudiants = csvService.loadEtudiants();

        for (Etudiant etudiant : etudiants) {
            if (etudiant.getMail().equals(email) && etudiant.getMdp().equals(motDePasse)) {
                this.currentUser = etudiant;
                return true;
            }
        }

        return false;
    }

    /**
     * Authentifie un enseignant avec email et mot de passe
     */
    public boolean loginEnseignant(String email, String motDePasse) {
        List<Enseignant> enseignants = csvService.loadEnseignants();

        for (Enseignant enseignant : enseignants) {
            if (enseignant.getMail().equals(email) && enseignant.getMdp().equals(motDePasse)) {
                this.currentUser = enseignant;
                return true;
            }
        }

        return false;
    }

    /**
     * Authentifie un administrateur avec email et mot de passe
     */
    public boolean loginAdministrateur(String email, String motDePasse) {
        List<Administrateur> administrateurs = csvService.loadAdministrateurs();

        for (Administrateur administrateur : administrateurs) {
            if (administrateur.getMail().equals(email) && administrateur.getMdp().equals(motDePasse)) {
                this.currentUser = administrateur;
                return true;
            }
        }

        return false;
    }

    /**
     * Déconnecte l'utilisateur courant
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * Récupère l'utilisateur actuellement connecté
     */
    public Utilisateur getCurrentUser() {
        return currentUser;
    }
}