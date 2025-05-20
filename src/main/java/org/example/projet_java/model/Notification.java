package org.example.projet_java.model;

import java.time.LocalDateTime;

public class Notification {
    private String id;
    private String titre;
    private String message;
    private LocalDateTime dateCreation;
    private java.time.LocalDate date; // Ajout pour compatibilité avec le format CSV
    private boolean lue;
    private String destinataireId;
    private String typeDestinataire; // "ETUDIANT", "ENSEIGNANT", "ADMINISTRATEUR"
    private String expediteurId;
    private String typeExpediteur; // "ETUDIANT", "ENSEIGNANT", "ADMINISTRATEUR", "SYSTEME"

    public Notification() {
        this.dateCreation = LocalDateTime.now();
        this.lue = false;
    }

    public Notification(String id, String titre, String message, String destinataireId,
                        String typeDestinataire, String expediteurId, String typeExpediteur) {
        this.id = id;
        this.titre = titre;
        this.message = message;
        this.dateCreation = LocalDateTime.now();
        this.lue = false;
        this.destinataireId = destinataireId;
        this.typeDestinataire = typeDestinataire;
        this.expediteurId = expediteurId;
        this.typeExpediteur = typeExpediteur;
    }

    public static Notification creerNotificationAnomalie(Anomalie anomalie, String destinataireId) {
        return new Notification(
                "NOTIF-" + anomalie.getId() + "-" + destinataireId,
                "Anomalie détectée: " + anomalie.getType(),
                anomalie.getDescription(),
                destinataireId,
                "ADMINISTRATEUR",
                "SYSTEM",
                "SYSTEME"
        );
    }

    public static Notification creerNotificationChangementCours(Cours cours, String destinataireId, String typeDestinataire) {
        return new Notification(
                "NOTIF-" + cours.getId() + "-" + destinataireId,
                "Modification du cours: " + cours.getNom(),
                "Le cours " + cours.getNom() + " a été modifié. Veuillez consulter votre emploi du temps.",
                destinataireId,
                typeDestinataire,
                "SYSTEM",
                "SYSTEME"
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public java.time.LocalDate getDate() {
        return date;
    }
    
    public void setDate(java.time.LocalDate date) {
        this.date = date;
        // Mettre à jour dateCreation si nécessaire
        if (this.dateCreation == null && date != null) {
            this.dateCreation = date.atStartOfDay();
        }
    }

    public boolean isLue() {
        return lue;
    }

    public void setLue(boolean lue) {
        this.lue = lue;
    }

    public String getDestinataireId() {
        return destinataireId;
    }

    public void setDestinataireId(String destinataireId) {
        this.destinataireId = destinataireId;
    }

    public String getTypeDestinataire() {
        return typeDestinataire;
    }

    public void setTypeDestinataire(String typeDestinataire) {
        this.typeDestinataire = typeDestinataire;
    }

    public String getExpediteurId() {
        return expediteurId;
    }

    public void setExpediteurId(String expediteurId) {
        this.expediteurId = expediteurId;
    }

    public String getTypeExpediteur() {
        return typeExpediteur;
    }

    public void setTypeExpediteur(String typeExpediteur) {
        this.typeExpediteur = typeExpediteur;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", titre='" + titre + '\'' +
                ", message='" + message + '\'' +
                ", dateCreation=" + dateCreation +
                ", lue=" + lue +
                ", destinataireId='" + destinataireId + '\'' +
                '}';
    }
}