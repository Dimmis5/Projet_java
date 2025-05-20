package org.example.projet_java.model;

import java.util.HashMap;

public class Administrateur extends Utilisateur {
    protected String id;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected String mdp;

    public Administrateur(String id, String nom, String prenom, String mail, String mdp) {
        super(id,nom,prenom,mail,mdp);
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
        return mail;
    }

    public void setMdp(String mail) {
        this.mail = mail;
    }
}
