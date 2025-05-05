package org.example.projet_java;

import java.util.ArrayList;

public class Salle {
    protected int id_salle;
    protected String localisation;
    protected int capacite;
    protected ArrayList<String> equipement;
    protected boolean statut;

    public Salle(int id_salle, String localisation, int capacite, boolean statut) {
        this.id_salle = id_salle;
        this.localisation = localisation;
        this.capacite = capacite;
        this.equipement = new ArrayList<>();
        this.statut = statut;
    }
}
