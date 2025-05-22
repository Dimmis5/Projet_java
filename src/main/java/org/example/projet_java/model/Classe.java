package org.example.projet_java.model;

public class Classe {
    protected String classe;
    protected String effectif;

    public Classe(String classe, String effectif) {
        this.classe = classe;
        this.effectif = effectif;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getEffectif() { return effectif; }

    public void setEffectif(String effectif) { this.effectif = effectif; }
}

