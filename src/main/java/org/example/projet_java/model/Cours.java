package org.example.projet_java.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cours {
    private String id;
    private String nom;
    private String description;
    private Enseignant enseignant;
    private Salle salle;
    private Horaire horaire;
    private String semestre;
    private int capaciteMax;
    private List<Etudiant> etudiantsInscrits;

    public Cours() {
        this.etudiantsInscrits = new ArrayList<>();
    }

    public Cours(String id, String nom, String description, Enseignant enseignant, 
                 Salle salle, Horaire horaire, String semestre, int capaciteMax) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.enseignant = enseignant;
        this.salle = salle;
        this.horaire = horaire;
        this.semestre = semestre;
        this.capaciteMax = capaciteMax;
        this.etudiantsInscrits = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Horaire getHoraire() {
        return horaire;
    }

    public void setHoraire(Horaire horaire) {
        this.horaire = horaire;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public List<Etudiant> getEtudiantsInscrits() {
        return etudiantsInscrits;
    }

    public void setEtudiantsInscrits(List<Etudiant> etudiantsInscrits) {
        this.etudiantsInscrits = etudiantsInscrits;
    }
    
    // Méthode d'alias pour être compatible avec d'autres parties du code
    public List<Etudiant> getEtudiants() {
        return etudiantsInscrits;
    }
    
    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiantsInscrits = etudiants;
    }

    public boolean inscrireEtudiant(Etudiant etudiant) {
        if (etudiantsInscrits.size() < capaciteMax) {
            etudiantsInscrits.add(etudiant);
            return true;
        }
        return false;
    }

    public boolean chavauchementAvec(Cours autreCours) {
        return this.horaire.chavauchementAvec(autreCours.horaire) &&
               this.salle.getId().equals(autreCours.salle.getId());
    }
    
    // Retourne la durée du cours en heures
    public int getDuree() {
        if (horaire == null || horaire.getHeureDebut() == null || horaire.getHeureFin() == null) {
            return 0;
        }
        return horaire.getHeureFin().getHour() - horaire.getHeureDebut().getHour();
    }
    
    // Méthode utilitaire pour accéder directement à la date du cours
    public LocalDate getDate() {
        if (horaire == null) {
            return null;
        }
        return horaire.getDate();
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", enseignant=" + (enseignant != null ? enseignant.getNom() : "non assigné") +
                ", salle=" + (salle != null ? salle.getId() : "non assignée") +
                ", horaire=" + horaire +
                ", semestre='" + semestre + '\'' +
                ", nbEtudiants=" + etudiantsInscrits.size() + "/" + capaciteMax +
                '}';
    }
}