package org.example.projet_java;

import org.junit.jupiter.api.Test;

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


}