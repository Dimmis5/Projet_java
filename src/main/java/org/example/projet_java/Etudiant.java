package org.example.projet_java;

import java.util.ArrayList;

public class Etudiant {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected ArrayList<Cours> cours;

    public Etudiant(int id, String nom, String prenom, String mail) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.cours = new ArrayList<>();
    }

}
