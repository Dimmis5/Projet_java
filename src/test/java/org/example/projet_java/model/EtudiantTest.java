package org.example.projet_java.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class EtudiantTest {

    @Test
    public void testConstructeur() {
        Etudiant etudiant = new Etudiant("id1", "Dupont", "Marie", "marie.dupont@mail.com", "pass123", "ClasseA");
        assertNotNull(etudiant);
    }

    @Test
    public void testGetters() {
        Etudiant etudiant = new Etudiant("id1", "Dupont", "Marie", "marie.dupont@mail.com", "pass123", "ClasseA");

        assertEquals("id1", etudiant.getId());
        assertEquals("Dupont", etudiant.getNom());
        assertEquals("Marie", etudiant.getPrenom());
        assertEquals("marie.dupont@mail.com", etudiant.getMail());
        assertEquals("ClasseA", etudiant.getClasse());

        assertNotNull(etudiant.getCours());
        assertTrue(etudiant.getCours().isEmpty());
    }

    @Test
    public void testSetters() {
        Etudiant etudiant = new Etudiant("id1", "Dupont", "Marie", "marie.dupont@mail.com", "pass123", "ClasseA");

        etudiant.setId("id2");
        etudiant.setNom("Martin");
        etudiant.setPrenom("Paul");
        etudiant.setMail("paul.martin@mail.com");
        etudiant.setClasse("ClasseB");

        assertEquals("id2", etudiant.getId());
        assertEquals("Martin", etudiant.getNom());
        assertEquals("Paul", etudiant.getPrenom());
        assertEquals("paul.martin@mail.com", etudiant.getMail());
        assertEquals("ClasseB", etudiant.getClasse());
    }

    @Test
    public void testSetCours() {
        Etudiant etudiant = new Etudiant("id1", "Dupont", "Marie", "marie.dupont@mail.com", "pass123", "ClasseA");

        ArrayList<Cours> listeCours = new ArrayList<>();

        etudiant.setCours(listeCours);
        assertEquals(listeCours, etudiant.getCours());
    }

    @Test
    public void testAjouterCoursEtGetSalle() {
        Etudiant etudiant = new Etudiant("id1", "Dupont", "Marie", "marie.dupont@mail.com", "pass123", "ClasseA");

        Cours cours1 = new Cours("1", "2", "mathematique", "15/05/2025", "10h00", "11h00", "3", "GR4", false);
        etudiant.ajouterCours(cours1);

        assertEquals(1, etudiant.getCours().size());
        assertEquals("2", etudiant.getSalle("1"));
        assertNull(etudiant.getSalle("cours2"));
    }

    @Test
    public void testNotification() {
        Etudiant etudiant = new Etudiant("id1", "Dupont", "Marie", "marie.dupont@mail.com", "pass123", "ClasseA");
        String result = etudiant.notification("cours1", "Devoir à rendre lundi");
        String expected = "Id cours : cours1\nNotification : Devoir à rendre lundi";
        assertEquals(expected, result);
    }
}
