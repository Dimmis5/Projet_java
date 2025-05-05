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
}
