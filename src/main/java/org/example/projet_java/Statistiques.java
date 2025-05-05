package org.example.projet_java;

import java.util.HashMap;

public class Statistiques {
    HashMap<Salle, Double> utilisationSalle;
    HashMap<Enseignant, Double> utilisationEnseignant;

    public Statistiques() {
        this.utilisationSalle = new HashMap<Salle, Double>();
        this.utilisationEnseignant = new HashMap<Enseignant, Double>();
    }

    public HashMap<Salle, double> getUtilisationSalle() {
        return utilisationSalle;
    }

    public void setUtilisationSalle(HashMap<Salle, double> utilisationSalle) {
        this.utilisationSalle = utilisationSalle;
    }

    public HashMap<Enseignant, double> getUtilisationEnseignant() {
        return utilisationEnseignant;
    }

    public void setUtilisationEnseignant(HashMap<Enseignant, double> utilisationEnseignant) {
        this.utilisationEnseignant = utilisationEnseignant;
    }
}
