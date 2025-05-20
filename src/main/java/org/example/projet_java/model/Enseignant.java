package org.example.projet_java.model;

import java.util.ArrayList;

public class Enseignant extends Utilisateur {
    protected String id;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected String mdp;
    protected ArrayList<Cours> cours;

    public Enseignant(String id, String nom, String prenom, String mail, String mdp) {
        super(id,nom,prenom,mail,mdp);
        this.cours = new ArrayList<>();
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getPrenom() {
        return prenom;
    }

    @Override
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mdp;
    }


    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public ArrayList<Cours> getCours() {
        return cours;
    }

    public void setCours(ArrayList<Cours> cours) {
        this.cours = cours;
    }

    // Methode set anomalie
    public void setAnomalie(String type, String description) {
        Anomalie anomalie = new Anomalie(type, description);
    }

}
