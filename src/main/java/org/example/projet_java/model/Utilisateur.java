package org.example.projet_java.model;

public class Utilisateur {
    protected String id;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected String mdp;

    public Utilisateur(String id, String nom, String prenom, String mail, String mdp) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp(){
        return mdp;
    }

    public void setMdp(){
        this.mdp = mdp;
    }

    public EmploiDuTemps consulter(EmploiDuTemps emploi){
        return emploi;
    }
}
