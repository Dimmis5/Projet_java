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

    public EmploiDuTemps getEleve(int id) {
        EmploiDuTemps emploi = new EmploiDuTemps(id);

        Enseignant enseignant1 = new Enseignant(1, "Durand", "Melanie", "melaniedurand@gmail.com", "123");
        Salle salle1 = new Salle(101, "Salle 101", 15, false);
        Horaire horaire1 = new Horaire("09/05/2025", "10:00", "11:00");
        Cours cours1 = new Cours(1, salle1, "Mathematiques", horaire1, enseignant1);

        Enseignant enseignant2 = new Enseignant(2, "Dupont", "Henri", "henridupont@gmail.com", "123");
        Salle salle2 = new Salle(102, "Salle 102",20, true);
        Horaire horaire2 = new Horaire("09/05/2025", "10:00", "11:00");
        Cours cours2 = new Cours(2, salle2, "Anglais", horaire2, enseignant2);

        emploi.getCours().add(cours1);
        emploi.getCours().add(cours2);

        return emploi;
    }

    public EmploiDuTemps getEnseignant(int id) {
        EmploiDuTemps emploi = new EmploiDuTemps(id);

        Salle salle1 = new Salle(101, "Salle 101", 30, false);
        Horaire horaire1 = new Horaire("09/05/2025", "10:00", "11:00");
        Cours cours1 = new Cours(1, salle1, "Mathematiques", horaire1, new Enseignant(id, "Durand", "Melanie", "melaniedurand@gmail.com", "123"));

        Salle salle2 = new Salle(102, "Salle 102", 40, false);
        Horaire horaire2 = new Horaire("09/05/2025", "10:00", "11:00");
        Cours cours2 = new Cours(2, salle2, "Anglais", horaire2, new Enseignant(id, "Dupont", "Henri", "henridupont@gmail.com", "123"));

        emploi.getCours().add(cours1);
        emploi.getCours().add(cours2);

        return emploi;
    }
}
