package org.example.projet_java;

import java.util.ArrayList;

public class Etudiant extends Utilisateur {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected String mdp;
    protected ArrayList<Cours> cours;

    public Etudiant(int id, String nom, String prenom, String mail, String mdp) {
        super(id,nom,prenom,mail,mdp);
        this.cours = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public ArrayList<Cours> getCours() {
        return cours;
    }

    public void setCours(ArrayList<Cours> cours) {
        this.cours = cours;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String getMdp() {
        return mail;
    }

    @Override
    public void setMdp(String mail) {
        this.mail = mail;
    }

    // Méthode consulter
    @Override
    public EmploiDuTemps consulter(EmploiDuTemps emploi) {
        return emploi.getEleve(this.id);
    }

    // Methode getSalle
    public Salle getSalle(int id_cours) {
        for (Cours cour : cours) {
            if (cour.getId_cours() == id_cours) {
                return cour.getSalle();
            }
        }
        return null;
    }

    // Méthode notification
    public String notification(String date, String message) {
        return "Date : " + date + "\n" + "Message : " + message;
    }
}
