package org.example.projet_java;

import java.util.ArrayList;

public class Cours {
    protected int id_cours;
    protected Salle salle;
    protected String matiere;
    protected Horaire horaire;
    protected Enseignant enseignant;
    protected ArrayList<Etudiant> etudiants;

    public Cours(int id_cours, Salle salle, String matiere, Horaire horaire, Enseignant enseignant) {
        this.id_cours = id_cours;
        this.salle = salle;
        this.matiere = matiere;
        this.horaire = horaire;
        this.enseignant = enseignant;
        this.etudiants = new ArrayList<>();
    }

}
