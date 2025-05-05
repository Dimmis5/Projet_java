package org.example.projet_java;

import java.util.ArrayList;

public class EmploiDuTemps {
    protected int id;
    protected ArrayList<Cours> cours;

    public EmploiDuTemps(int id) {
        this.id = id;
        this.cours = new ArrayList<>();
    }
}
