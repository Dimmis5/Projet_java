package org.example.projet_java;

import java.util.HashMap;

public class Administrateur extends Utilisateur {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected String mdp;

    public Administrateur(int id, String nom, String prenom, String mail, String mdp) {
        super(id,nom,prenom,mail,mdp);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getPrenom() {
        return prenom;
    }

    @Override
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mail;
    }

    public void setMdp(String mail) {
        this.mail = mail;
    }

    // Methode setEmploi
    @Override
    public void setEmploi(EmploiDuTemps emploiDuTemps, Cours cours) {
        emploiDuTemps.getCours().add(cours);
    }

    // Methode setEnseignant
    public void setEnseignat(EmploiDuTemps emploiDuTemps, Cours cours, Enseignant id_enseignant){
        cours.setEnseignant(id_enseignant);
        id_enseignant.getCours().add(cours);
        emploiDuTemps.getCours().add(cours);
    }

    @Override
    // Methode getSalle
    public Salle getSalle(Cours cours) {
        return cours.getSalle();
    }

    // Methode getAlerte
    public void getAlerte(Cours cours){
        if (cours.isAnnule()){
            System.out.println("Le cours de "+ cours.getMatiere()+" a été annulé");
        }else{
            System.out.println("Aucun cours annulé");
        }

    }


    // Methode getStatistiques
    public void getStatistique(EmploiDuTemps emploiDuTemps){
            HashMap<Integer, Integer> Statistiques = new HashMap<>();

            for (Cours cours : emploiDuTemps.getCours()){
                int idSalle =  cours.getSalle().getId_salle();
                Statistiques.put(idSalle, Statistiques.getOrDefault(idSalle, 0) + 1);
            }
            System.out.println("Statistiques d'utilisation des salles :");
            for (Integer idSalle : Statistiques.keySet()) {
            System.out.println("Salle " + idSalle + " utilisée " + Statistiques.get(idSalle) + " fois.");
        }
    }

}
