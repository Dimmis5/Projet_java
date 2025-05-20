package org.example.projet_java.model;

public class Salle {
    private String id;
    private String nom;      // Ajout du champ nom
    private String batiment;
    private String etage;
    private int capacite;
    private boolean equipeProjecteur;
    private boolean equipeLaboratoire;

    public Salle() {
    }

    public Salle(String id, String nom, String batiment, String etage, int capacite, boolean equipeProjecteur, boolean equipeLaboratoire) {
        this.id = id;
        this.nom = nom;
        this.batiment = batiment;
        this.etage = etage;
        this.capacite = capacite;
        this.equipeProjecteur = equipeProjecteur;
        this.equipeLaboratoire = equipeLaboratoire;
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

    public String getBatiment() {
        return batiment;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    public String getEtage() {
        return etage;
    }

    public void setEtage(String etage) {
        this.etage = etage;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public boolean isEquipeProjecteur() {
        return equipeProjecteur;
    }

    public void setEquipeProjecteur(boolean equipeProjecteur) {
        this.equipeProjecteur = equipeProjecteur;
    }

    public boolean isEquipeLaboratoire() {
        return equipeLaboratoire;
    }

    public void setEquipeLaboratoire(boolean equipeLaboratoire) {
        this.equipeLaboratoire = equipeLaboratoire;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", batiment='" + batiment + '\'' +
                ", etage='" + etage + '\'' +
                ", capacite=" + capacite +
                ", equipeProjecteur=" + equipeProjecteur +
                ", equipeLaboratoire=" + equipeLaboratoire +
                '}';
    }
    
    // Méthode utilitaire pour obtenir une représentation concise de la salle
    public String getDescription() {
        return nom + " (" + batiment + ", " + etage + ")";
    }
}