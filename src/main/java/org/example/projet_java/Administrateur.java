package org.example.projet_java;

public class Administrateur extends Utilisateur {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String mail;

    public Administrateur(int id, String nom, String prenom, String mail) {
        super(id,nom,prenom,mail);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getPrenom() {
        return prenom;
    }

    @Override
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public void setMail(String mail) {
        this.mail = mail;
    }

    // Methode setEmploi

    // Methode setEnseignant

    // Methode getSalle

    // Methode getAlerte

    // Methode getStatistiques
}
