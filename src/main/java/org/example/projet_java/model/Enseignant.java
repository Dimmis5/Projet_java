package org.example.projet_java.model;

import java.util.ArrayList;
import java.util.List;

public class Enseignant extends Utilisateur {
    private String departement;
    private String specialite;
    private List<Cours> coursEnseignes;

    public Enseignant() {
        super();
        this.coursEnseignes = new ArrayList<>();
    }

    public Enseignant(String id, String nom, String prenom, String email, String motDePasse,
                      String departement, String specialite) {
        super(id, nom, prenom, email, motDePasse);
        this.departement = departement;
        this.specialite = specialite;
        this.coursEnseignes = new ArrayList<>();
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public List<Cours> getCoursEnseignes() {
        return coursEnseignes;
    }

    public void setCoursEnseignes(List<Cours> coursEnseignes) {
        this.coursEnseignes = coursEnseignes;
    }

    public void ajouterCours(Cours cours) {
        this.coursEnseignes.add(cours);
    }

    @Override
    public String toString() {
        return "Enseignant{" +
                "id='" + getId() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", departement='" + departement + '\'' +
                ", specialite='" + specialite + '\'' +
                '}';
    }
}