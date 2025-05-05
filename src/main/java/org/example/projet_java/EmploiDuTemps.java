package org.example.projet_java;

import java.util.ArrayList;

public class EmploiDuTemps {
    protected int id;
    protected ArrayList<Cours> cours;

    public EmploiDuTemps(int id) {
        this.id = id;
        this.cours = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Cours> getCours() {
        return cours;
    }

    public void setCours(ArrayList<Cours> cours) {
        this.cours = cours;
    }
}
