package org.example.projet_java.model;

public class Administrateur extends Utilisateur {
    private String departement;
    private String fonction;

    public Administrateur() {
        super();
    }

    public Administrateur(String id, String nom, String prenom, String email, String motDePasse, 
                          String departement, String fonction) {
        super(id, nom, prenom, email, motDePasse);
        this.departement = departement;
        this.fonction = fonction;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    @Override
    public String toString() {
        return "Administrateur{" +
                "id='" + getId() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", departement='" + departement + '\'' +
                ", fonction='" + fonction + '\'' +
                '}';
    }
}