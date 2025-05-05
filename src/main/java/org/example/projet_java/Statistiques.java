package org.example.projet_java;

import java.util.HashMap;

public class Statistiques {
    HashMap<Salle, Double> utilisationSalle;
    HashMap<Enseignant,Double> utilisationEnseignant;

    public Statistiques() {
        this.utilisationSalle = new HashMap<Salle,Double>();
        this.utilisationEnseignant = new HashMap<Enseignant, Double>();
    }

    public HashMap<Salle, Double> getUtilisationSalle() {
        return utilisationSalle;
    }

    public void setUtilisationSalle(HashMap<Salle, Double> utilisationSalle) {
        this.utilisationSalle = utilisationSalle;
    }

    public HashMap<Enseignant, Double> getUtilisationEnseignant() {
        return utilisationEnseignant;
    }

    public void setUtilisationEnseignant(HashMap<Enseignant, Double> utilisationEnseignant) {
        this.utilisationEnseignant = utilisationEnseignant;
    }
}
