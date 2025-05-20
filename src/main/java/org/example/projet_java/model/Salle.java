package org.example.projet_java.model;

import java.util.ArrayList;

public class Salle {
    protected String id_salle;
    protected String localisation;
    protected String capacite;
    protected ArrayList<String> equipement;
    protected boolean statut;

    public Salle(String id_salle, String localisation, String capacite, boolean statut) {
        this.id_salle = id_salle;
        this.localisation = localisation;
        this.capacite = capacite;
        this.equipement = new ArrayList<>();
        this.statut = statut;
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

    public String getCapacite() {
        return capacite;
    }

    public void setCapacite(String capacite) {
        this.capacite = capacite;
    }

    public boolean getStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public ArrayList<String> getEquipement() {
        return equipement;
    }

    public void setEquipement(ArrayList<String> equipement) {
        this.equipement = equipement;
    }
}
