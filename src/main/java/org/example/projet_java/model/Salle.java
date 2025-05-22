package org.example.projet_java.model;

import java.util.ArrayList;

public class Salle {
    protected String id_salle;
    protected String localisation;
    protected int capacite;
    protected ArrayList<String> equipement;

    public Salle(String id_salle, String localisation, int capacite) {
        this.id_salle = id_salle;
        this.localisation = localisation;
        this.capacite = capacite;
        this.equipement = new ArrayList<>();
    }

    public String getId_salle() {
        return id_salle;
    }

    public void setId_salle(String id_salle) {
        this.id_salle = id_salle;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public ArrayList<String> getEquipement() {
        return equipement;
    }

    public void setEquipement(ArrayList<String> equipement) {
        this.equipement = equipement;
    }
}
