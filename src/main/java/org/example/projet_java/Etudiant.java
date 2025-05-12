package org.example.projet_java;

import java.util.ArrayList;

public class Etudiant extends Utilisateur {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected ArrayList<Cours> cours;
    protected Salle salle;

    public Etudiant(int id, String nom, String prenom, String mail, String mdp) {
        super(id, nom, prenom, mail, mdp);
        this.cours = new ArrayList<>();
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
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

    // Méthode consulter
    public EmploiDuTemps consulter(EmploiDuTemps emploi) {
        return emploi.getEleve(this.id);
    }

    // Méthode getSalle
    public Salle getSalle(int id_cours) {
        for (Cours cour : cours) {
            // Vérification si l'id du cours correspond
            if (cour.getId_cours() == id_cours) {
                return cour.getSalle();
            }
        }
        // Retourne null si le cours avec cet id n'est pas trouvé
        return null;
    }

    // Méthode notification
    public String notification(String date, String message) {
        return "Date : " + date + "\n" + "Message : " + message;
    }

    public void ajouterCours(Cours cours) {
        this.cours.add(cours);
    }

}
