package org.example.projet_java.service;

import org.example.projet_java.model.Administrateur;
import org.example.projet_java.model.Enseignant;
import org.example.projet_java.model.Etudiant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthentificationService {
    
    private static final String FICHIER_ADMINISTRATEURS = "CSV_Java/Administrateur.csv";
    private static final String FICHIER_ENSEIGNANTS = "CSV_Java/Enseignant.csv";
    private static final String FICHIER_ETUDIANTS = "CSV_Java/Etudiant.csv";
    
    private List<Administrateur> administrateurs;
    private List<Enseignant> enseignants;
    private List<Etudiant> etudiants;
    
    public AuthentificationService() {
        administrateurs = new ArrayList<>();
        enseignants = new ArrayList<>();
        etudiants = new ArrayList<>();
        
        chargerDonnees();
    }
    
    private void chargerDonnees() {
        chargerAdministrateurs();
        chargerEnseignants();
        chargerEtudiants();
    }
    
    private void chargerAdministrateurs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_ADMINISTRATEURS))) {
            String line;
            // Skip header line
            reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    Administrateur admin = new Administrateur();
                    admin.setId(data[0]); // Utiliser l'ID comme chaîne de caractères
                    admin.setNom(data[1]);
                    admin.setPrenom(data[2]);
                    admin.setMotDePasse(data[3]);
                    
                    administrateurs.add(admin);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des administrateurs: " + e.getMessage());
        }
    }
    
    private void chargerEnseignants() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_ENSEIGNANTS))) {
            String line;
            // Skip header line
            reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    Enseignant enseignant = new Enseignant();
                    enseignant.setId(data[0]); // Utiliser l'ID comme chaîne de caractères
                    enseignant.setNom(data[1]);
                    enseignant.setPrenom(data[2]);
                    enseignant.setMotDePasse(data[3]);
                    
                    enseignants.add(enseignant);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des enseignants: " + e.getMessage());
        }
    }
    
    private void chargerEtudiants() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_ETUDIANTS))) {
            String line;
            // Skip header line
            reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    Etudiant etudiant = new Etudiant();
                    etudiant.setId(data[0]); // Utiliser l'ID comme chaîne de caractères
                    etudiant.setNom(data[1]);
                    etudiant.setPrenom(data[2]);
                    etudiant.setMotDePasse(data[3]);
                    
                    etudiants.add(etudiant);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des étudiants: " + e.getMessage());
        }
    }
    
    public Administrateur authentifierAdministrateur(String identifiant, String motDePasse) {
        // L'identifiant est maintenant directement utilisé comme chaîne de caractères
        for (Administrateur admin : administrateurs) {
            if (admin.getId().equals(identifiant) && admin.getMotDePasse().equals(motDePasse)) {
                return admin;
            }
        }
        
        return null;
    }
    
    public Enseignant authentifierEnseignant(String identifiant, String motDePasse) {
        // L'identifiant est maintenant directement utilisé comme chaîne de caractères
        for (Enseignant enseignant : enseignants) {
            if (enseignant.getId().equals(identifiant) && enseignant.getMotDePasse().equals(motDePasse)) {
                return enseignant;
            }
        }
        
        return null;
    }
    
    public Etudiant authentifierEtudiant(String identifiant, String motDePasse) {
        // L'identifiant est maintenant directement utilisé comme chaîne de caractères
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getId().equals(identifiant) && etudiant.getMotDePasse().equals(motDePasse)) {
                return etudiant;
            }
        }
        
        return null;
    }
}