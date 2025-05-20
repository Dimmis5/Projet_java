package org.example.projet_java;

import org.example.projet_java.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantTest {

    @Test
    public void testContructeur(){
        Etudiant etudiant = new Etudiant(5, "Abdou", "Kamy", "abdoukamy@gmail.com", "ak123");

        assertEquals(5, etudiant.id);
        assertEquals("Abdou", etudiant.nom);
        assertEquals("Kamy", etudiant.prenom);
        assertEquals("abdoukamy@gmail.com", etudiant.mail);
        assertEquals("ak123", etudiant.mdp);
        assertTrue(etudiant.getCours().isEmpty());
    }

    @Test
    public void testSetCours(){
        Etudiant etudiant = new Etudiant(5, "Abdou", "Kamy", "abdoukamy@gmail.com", "ak123");

        Salle salle1 = new Salle(101,"Salle 101", 40, false);
        Horaire horaire1 = new Horaire("Lundi", "15h00", "16h00");
        Cours cours1 = new Cours(1, salle1, "Mathematique", horaire1, new Enseignant(1, "Zhang", "Kelly", "zhangkelly@gmail.com", "kz123"));

        Salle salle2 = new Salle(102, "Salle 102", 45, true);
        Horaire horaire2 = new Horaire("Mardi", "16h00", "17h00");
        Cours cours2 = new Cours(2, salle2, "Anglais", horaire2, new Enseignant(2,"Nguyen", "Minh", "NguMin@gmail.com", "nm123"));

        ArrayList<Cours> coursList =new ArrayList<>();
        coursList.add(cours1);
        coursList.add(cours2);
        etudiant.setCours(coursList);

        assertEquals(2, etudiant.getCours().size());
        assertEquals("Mathematique", etudiant.getCours().get(0).getMatiere());
        assertEquals("Anglais", etudiant.getCours().get(1).getMatiere());

    }

    @Test
    public void testConsulter(){
        Etudiant etudiant = new Etudiant(5, "Abdou", "Kamy", "abdoukamy@gmail.com", "ak123");
        EmploiDuTemps emploi = new EmploiDuTemps(0);

        EmploiDuTemps result = etudiant.consulter(emploi);

        assertNotNull(result);
        assertNotEquals(emploi, result);
    }

    @Test
    public void testGetSalle() {
        Salle salle = new Salle(5, "Salle 1", 30, true);
        Horaire horaire = new Horaire("Lundi", "14h00", "15h00");
        Enseignant enseignant = new Enseignant(15,"Talib", "Momo", "tm@gmail.com", "tm123");
        Cours cours = new Cours(1, salle,"Mathématique", horaire, enseignant);
        Etudiant etudiant = new Etudiant(15, "Gugas", "Lola", "gl@gmail.com", "gl123");

        etudiant.ajouterCours(cours);

        Salle result = etudiant.getSalle(1);
        assertNotNull(result);
        assertEquals("Salle 1", result.getLocalisation());
    }

    @Test
    public void testNotification(){
        Etudiant etudiant = new Etudiant(5, "Abdou", "Kamy", "abdoukamy@gmail.com", "ak123");
        String notification = etudiant.notification("09/05/2025", "Cours annulé");

        assertEquals("Date : 09/05/2025\nMessage : Cours annulé", notification);
    }
}