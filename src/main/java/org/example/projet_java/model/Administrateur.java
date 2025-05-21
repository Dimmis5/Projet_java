package org.example.projet_java.model;

import java.util.HashMap;

public class Administrateur extends Utilisateur {
    protected String id_administrateur;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected String mdp;

    public Administrateur(String id_administrateur, String nom, String prenom, String mail, String mdp) {
        super(id_administrateur,nom,prenom,mail,mdp);
        this.id_administrateur = id_administrateur;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
    }

    @Override
    public String getId() {
        return id_administrateur;
    }

    @Override
    public void setId(String id_administrateur) {
        this.id_administrateur = id_administrateur;
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

    @Override
    public String getMdp() {
        return mdp;
    }

    @Override
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
