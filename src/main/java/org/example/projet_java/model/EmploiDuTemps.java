package org.example.projet_java.model;

public class EmploiDuTemps {
    protected String id_etudiant;
    protected String id_cours;

    public EmploiDuTemps(String id_etudiant, String id_cours) {
        this.id_etudiant = id_etudiant;
        this.id_cours = id_cours;
    }

    public String getId_etudiant() {
        return id_etudiant;
    }

    public void setId_etudiant(String id_etudiant) {
        this.id_etudiant = id_etudiant;
    }

    public String getId_cours() {
        return id_cours;
    }

    public void setId_cours(String id_cours) {
        this.id_cours = id_cours;
    }
}
