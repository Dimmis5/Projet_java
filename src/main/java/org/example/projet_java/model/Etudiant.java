package org.example.projet_java.model;

import java.util.ArrayList;

public class Etudiant extends Utilisateur {
    protected String id;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected String classe;
    protected ArrayList<Cours> cours;

    public Etudiant(String id, String nom, String prenom, String mail, String mdp, String classe) {
        super(id, nom, prenom, mail, mdp);
        this.cours = new ArrayList<>();
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
        this.classe = classe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getClasse() { return classe; }

    public void setClasse(String classe) { this.classe = classe; }

    // Méthode getSalle
    public String getSalle(String id_cours) {
        for (Cours cour : cours) {
            if (cour.getId_cours().equals(id_cours)) {
                return cour.getId_salle();
            }
        }
        return null;
    }

    public String notification(String id_cours, String notification) {
        return "Id cours : " + id_cours + "\n" + "Notification : " + notification;
    }

    public void ajouterCours(Cours cours) {
        this.cours.add(cours);
    }

}
