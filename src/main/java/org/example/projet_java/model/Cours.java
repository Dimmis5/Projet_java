package org.example.projet_java.model;

import java.util.ArrayList;

public class Cours {
    protected String id_cours;
    protected Salle salle;
    protected String matiere;
    protected Horaire horaire;
    protected Enseignant enseignant;
    protected ArrayList<Etudiant> etudiants;
    protected boolean estAnnule = false;

    public Cours(String id_cours, Salle salle, String matiere, Horaire horaire, Enseignant enseignant) {
        this.id_cours = id_cours;
        this.salle = salle;
        this.matiere = matiere;
        this.horaire = horaire;
        this.enseignant = enseignant;
        this.etudiants = new ArrayList<>();
    }

    public String getId_cours() {
        return id_cours;
    }

    public void setId_cours(String id_cours) {
        this.id_cours = id_cours;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Horaire getHoraire() {
        return horaire;
    }

    public void setHoraire(Horaire horaire) {
        this.horaire = horaire;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public ArrayList<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(ArrayList<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public boolean isAnnule() {
        return estAnnule;
    }

    public void setAnnule(boolean estAnnule) {
        this.estAnnule = estAnnule;
    }

}
