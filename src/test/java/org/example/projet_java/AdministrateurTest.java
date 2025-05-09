package org.example.projet_java;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdministrateurTest {

    @Test
    public void testConstructeur(){
        int id = 42;
        String nom = "Zhang";
        String prenom = "Dimeo";
        String mail = "zhangdimeo@gmail.com";
        String mdp = "Salut123";

        Administrateur admin = new Administrateur(id, nom, prenom, mail, mdp);
        assertEquals(id, 42);
        assertEquals(nom, "Zhang");
        assertEquals(prenom, "Dimeo");
        assertEquals(mail, "zhangdimeo@gmail.com");
        assertEquals(mdp, "Salut123");
    }

    @Test
    public void testSetEmploi(){
        Administrateur admin = new Administrateur(1,"Coutand", "Oriane", "CourtandOriane@gmail.com", "mdp123");

        Salle salle = new Salle(101,"JeNeSaisPAs",30,false);
        Horaire horaire = new Horaire("Vendredi", "10h30", "11h30");
        Enseignant enseignant = new Enseignant(1,"Février", "Nicolas", "fevriernico@gmail.com","ok");
        Cours cours = new Cours(1,salle,"Math", horaire, enseignant);
        EmploiDuTemps edt = new EmploiDuTemps(1);
        edt.setCours(edt,cours);

        assertTrue(edt.getCours().contains(cours));
    }

    @Test
    public void testSetEnseignant(){
        Administrateur admin = new Administrateur(1, "Ferrera", "Leanne", "ferreraleanne@gmail.com", "z123");

        Salle salle = new Salle(101, "Paris", 30, true);
        Horaire horaire = new Horaire("Jeudi", "11h30","12h00");
        Enseignant enseignant = new Enseignant(2, "Barbes", "Marin", "barbesmarin@gmail.com", "patate");
        Cours cours = new Cours(1, salle, "Francais", horaire, enseignant);
        EmploiDuTemps edt = new EmploiDuTemps(1);
        edt.setCours(new ArrayList<>());

        enseignant.setCours(new ArrayList<>());

        admin.setEnseignat(edt, cours, enseignant);

        assertEquals(enseignant, cours.getEnseignant());
        assertTrue(enseignant.getCours().contains(cours));
        assertTrue(edt.getCours().contains(cours));
    }

    @Test
    public void testGetSalle(){
        Administrateur admin = new Administrateur(1,"Ribero", "Noah", "riberonoah@gmail.com", "femme");

        Salle salle = new Salle(202,"Issy",20,true);

        Horaire horaire = new Horaire("Mercredi", "13h00", "14h00");
        Enseignant enseignant = new Enseignant(3,"Ducrot", "Altea", "ducrotalthea@gmail.com", "q132");
        Cours cours = new Cours(2, salle,"Physique", horaire, enseignant);

        Salle resultat = admin.getSalle(cours);
        assertEquals(salle, resultat);
    }

}