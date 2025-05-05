package org.example.projet_java;

import java.util.HashMap;

public class Statistiques {
    HashMap<Salle,double> utilisationSalle;
    HashMap<Enseignant,double> utilisationEnseignant;

    public Statistiques() {
        this.utilisationSalle = new HashMap<Salle,double>();
        this.utilisationEnseignant = = new HashMap<Enseignant, double>();
    }
}
