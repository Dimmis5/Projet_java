package org.example.projet_java;

import java.util.ArrayList;

public class Enseignant extends Utilisateur{
    protected int id;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected String mdp;
    protected ArrayList<Cours> cours;

    public Enseignant(int id, String nom, String prenom, String mail, String mdp) {
        super(id,nom,prenom,mail,mdp);
        this.cours = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
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

    public ArrayList<Cours> getCours() {
        return cours;
    }

    public void setCours(ArrayList<Cours> cours) {
        this.cours = cours;
    }

    // Methode consulter
    @Override
    public EmploiDuTemps consulter(EmploiDuTemps emploi) {
        return emploi.getEnseignant(this.id);
    }

    // Methode set anomalie
    public void setAnomalie(String type, String description) {
        Anomalie anomalie = new Anomalie(type, description);
    }

}
