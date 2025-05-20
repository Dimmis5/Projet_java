package org.example.projet_java.model;

import java.time.LocalDateTime;

public class Anomalie {
    private String id;
    private String type;
    private String description;
    private LocalDateTime dateDetection;
    private boolean resolue;
    private Cours cours1;
    private Cours cours2;

    public Anomalie() {
        this.dateDetection = LocalDateTime.now();
        this.resolue = false;
    }

    public Anomalie(String id, String type, String description, Cours cours1, Cours cours2) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.dateDetection = LocalDateTime.now();
        this.resolue = false;
        this.cours1 = cours1;
        this.cours2 = cours2;
    }

    public static Anomalie creerAnomalieConflit(Cours cours1, Cours cours2) {
        String description = "Conflit horaire entre " + cours1.getNom() + " et " + cours2.getNom() + 
                             " dans la salle " + (cours1.getSalle().getNom() != null ? cours1.getSalle().getNom() : cours1.getSalle().getId());
        return new Anomalie(
                cours1.getId() + "-" + cours2.getId(),
                "CONFLIT_HORAIRE",
                description,
                cours1,
                cours2
        );
    }

    public static Anomalie creerAnomalieCapacite(Cours cours) {
        String description = "Dépassement de capacité pour le cours " + cours.getNom() + 
                             " dans la salle " + (cours.getSalle().getNom() != null ? cours.getSalle().getNom() : cours.getSalle().getId()) + 
                             " (" + cours.getEtudiantsInscrits().size() + "/" + cours.getSalle().getCapacite() + ")";
        return new Anomalie(
                cours.getId() + "-CAPACITE",
                "DEPASSEMENT_CAPACITE",
                description,
                cours,
                null
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateDetection() {
        return dateDetection;
    }

    public void setDateDetection(LocalDateTime dateDetection) {
        this.dateDetection = dateDetection;
    }

    public boolean isResolue() {
        return resolue;
    }

    public void setResolue(boolean resolue) {
        this.resolue = resolue;
    }

    public Cours getCours1() {
        return cours1;
    }

    public void setCours1(Cours cours1) {
        this.cours1 = cours1;
    }

    public Cours getCours2() {
        return cours2;
    }

    public void setCours2(Cours cours2) {
        this.cours2 = cours2;
    }

    @Override
    public String toString() {
        return "Anomalie{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", dateDetection=" + dateDetection +
                ", resolue=" + resolue +
                '}';
    }
}