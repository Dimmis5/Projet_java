package org.example.projet_java;

import java.util.ArrayList;

public class Etudiant extends Utilisateur {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected ArrayList<Cours> cours;

    public Etudiant(int id, String nom, String prenom, String mail) {
        super(id,nom,prenom,mail);
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

    // Méthode consulter
    public String consulter() {
        for (Cours cour : cours) {
            return "Salle : " + cour.salle + "\n"
                    + "Matiere : " + cour.matiere + "\n"
                    + "Horaire : " + cour.horaire + "\n"
                    + "Enseignant : " + cour.enseignant;
        }
        return null;
    }

    public Salle getSalle(int id_cours) {
        for (Cours cour : cours) {
            if (cour.getId_cours() == id_cours) {
                return cour.getSalle();
            }
        }
        return null;
    }

    // Méthode notification
}
