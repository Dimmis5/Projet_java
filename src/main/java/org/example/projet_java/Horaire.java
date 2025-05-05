package org.example.projet_java;

public class Horaire {
    protected String jour;
    protected String heure_debut;
    protected String heure_fin;

    public Horaire(String jour, String heure_debut, String heure_fin) {
        this.jour = jour;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(String heure_fin) {
        this.heure_fin = heure_fin;
    }

    public String getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(String heure_debut) {
        this.heure_debut = heure_debut;
    }
}
