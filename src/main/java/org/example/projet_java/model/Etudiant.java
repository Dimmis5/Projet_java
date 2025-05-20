package org.example.projet_java.model;

import java.util.ArrayList;
import java.util.List;

public class Etudiant extends Utilisateur {
    private String filiere;
    private String niveau;
    private String groupe;
    private List<Cours> coursInscrits;
    private List<Notification> notifications;

    public Etudiant() {
        super();
        this.coursInscrits = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public Etudiant(String id, String nom, String prenom, String email, String motDePasse,
                    String filiere, String niveau, String groupe) {
        super(id, nom, prenom, email, motDePasse);
        this.filiere = filiere;
        this.niveau = niveau;
        this.groupe = groupe;
        this.coursInscrits = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public List<Cours> getCoursInscrits() {
        return coursInscrits;
    }

    public void setCoursInscrits(List<Cours> coursInscrits) {
        this.coursInscrits = coursInscrits;
    }

    public void inscrireAuCours(Cours cours) {
        this.coursInscrits.add(cours);
    }
    
    public List<Notification> getNotifications() {
        return notifications;
    }
    
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
    
    public void ajouterNotification(Notification notification) {
        this.notifications.add(notification);
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id='" + getId() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", filiere='" + filiere + '\'' +
                ", niveau='" + niveau + '\'' +
                ", groupe='" + groupe + '\'' +
                '}';
    }
}