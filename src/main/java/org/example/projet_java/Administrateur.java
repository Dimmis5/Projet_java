package org.example.projet_java;

public class Administrateur {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String mail;

    public Administrateur(int id, String nom, String prenom, String mail) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
    }
}
