package org.example.projet_java;

import java.util.HashMap;

public class Statistiques {
    HashMap<Salle, Double> utilisationSalle;
    HashMap<Enseignant, Double> utilisationEnseignant;

    public Statistiques() {
        this.utilisationSalle = new HashMap<Salle, Double>();
        this.utilisationEnseignant = new HashMap<Enseignant, Double>();
    }
}
