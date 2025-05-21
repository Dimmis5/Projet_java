/*
package org.example.projet_java.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class EnseignantTest {

    @Test
    public void testConstructeur() {
        Enseignant enseignant = new Enseignant("id1", "Dupont", "Jean", "jean.dupont@mail.com", "pass123");
        assertNotNull(enseignant);
    }

    @Test
    public void testGetters() {
        Enseignant enseignant = new Enseignant("id1", "Dupont", "Jean", "jean.dupont@mail.com", "pass123");

        assertEquals("id1", enseignant.getId());
        assertEquals("Dupont", enseignant.getNom());
        assertEquals("Jean", enseignant.getPrenom());
        assertEquals("jean.dupont@mail.com", enseignant.getMail());
        assertEquals("pass123", enseignant.getMdp());

        assertNotNull(enseignant.getCours());
        assertTrue(enseignant.getCours().isEmpty());
    }

    @Test
    public void testSetters() {
        Enseignant enseignant = new Enseignant("id1", "Dupont", "Jean", "jean.dupont@mail.com", "pass123");

        enseignant.setId("id2");
        enseignant.setNom("Martin");
        enseignant.setPrenom("Paul");
        enseignant.setMail("paul.martin@mail.com");
        enseignant.setMdp("newpass");

        assertEquals("id2", enseignant.getId());
        assertEquals("Martin", enseignant.getNom());
        assertEquals("Paul", enseignant.getPrenom());
        assertEquals("paul.martin@mail.com", enseignant.getMail());
        assertEquals("newpass", enseignant.getMdp());
    }

    @Test
    public void testSetCours() {
        Enseignant enseignant = new Enseignant("id1", "Dupont", "Jean", "jean.dupont@mail.com", "pass123");
        ArrayList<Cours> listeCours = new ArrayList<>();
        enseignant.setCours(listeCours);
        assertEquals(listeCours, enseignant.getCours());
    }

    @Test
    public void testSetAnomalie() {
        Enseignant enseignant = new Enseignant("id1", "Dupont", "Jean", "jean.dupont@mail.com", "pass123");

        enseignant.setAnomalie("typeTest", "descriptionTest");
    }
}*/
