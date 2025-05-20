package org.example.projet_java.model;

import java.util.ArrayList;

public class Cours {
    protected String id_cours;
    protected String id_salle;
    protected String matiere;
    protected String date;
    protected String heure_debut;
    protected String heure_fin;
    protected String id_enseignant;
    protected String classe;
    protected ArrayList<Etudiant> etudiants;
    protected boolean annulation = false;

    public Cours(String id_cours, String id_salle, String matiere, String date, String heure_debut, String heure_fin, String id_enseignant, String classe, Boolean annulation) {
        this.id_cours = id_cours;
        this.id_salle = id_salle;
        this.matiere = matiere;
        this.date = date;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.id_enseignant = id_enseignant;
        this.classe = classe;
        this.etudiants = new ArrayList<>();
    }

    public String getHeure_debut(){
        return heure_debut;
    }

    public void setHeure_debut(String heureDebut){
        this.heure_debut = heureDebut;
    }

    public String getHeure_fin(){
        return heure_fin;
    }

    public void setHeure_fin(String heureFin){
        this.heure_fin = heureFin;
    }

    public String getId_cours() {
        return id_cours;
    }

    public void setId_cours(String id_cours) {
        this.id_cours = id_cours;
    }

    public String getId_salle() {
        return id_salle;
    }

    public void setId_salle(String id_salle) {
        this.id_salle = id_salle;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_enseignant() {
        return id_enseignant;
    }

    public void setEnseignant(String id_enseignant) {
        this.id_enseignant = id_enseignant;
    }

    public String getClasse() { return classe;}

    public void setClasse(String classe) { this.classe = classe;}

    public ArrayList<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(ArrayList<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public boolean isAnnulation() {
        return annulation;
    }

    public void setAnnulation(boolean annulation) {
        this.annulation = annulation;
    }

}
