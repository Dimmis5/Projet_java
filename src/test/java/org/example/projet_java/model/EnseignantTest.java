package org.example.projet_java.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class EnseignantTest {

    @Test
    public void testConstructeur() {
        Enseignant enseignant = new Enseignant("20000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");

        assertEquals("20000", enseignant.getId());
        assertEquals("Dupont", enseignant.getNom());
        assertEquals("Jean", enseignant.getPrenom());
        assertEquals("jean.dupont@gmail.com", enseignant.getMail());
        assertEquals("dj123", enseignant.getMdp());
        assertNotNull(enseignant);
    }

    @Test
    public void testGetters() {
        Enseignant enseignant = new Enseignant("20000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");

        assertEquals("20000", enseignant.getId());
        assertEquals("Dupont", enseignant.getNom());
        assertEquals("Jean", enseignant.getPrenom());
        assertEquals("jean.dupont@gmail.com", enseignant.getMail());
        assertEquals("dj123", enseignant.getMdp());

        assertNotNull(enseignant.getCours());
        assertTrue(enseignant.getCours().isEmpty());
    }

    @Test
    public void testSetters() {
        Enseignant enseignant = new Enseignant("20000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");

        enseignant.setId("20001");
        enseignant.setNom("Martin");
        enseignant.setPrenom("Paul");
        enseignant.setMail("paul.martin@gmail.com");
        enseignant.setMdp("pm123");

        assertEquals("20001", enseignant.getId());
        assertEquals("Martin", enseignant.getNom());
        assertEquals("Paul", enseignant.getPrenom());
        assertEquals("paul.martin@gmail.com", enseignant.getMail());
        assertEquals("pm123", enseignant.getMdp());
    }

    @Test
    public void testSetCours() {
        Enseignant enseignant = new Enseignant("20000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        ArrayList<Cours> listeCours = new ArrayList<>();
        enseignant.setCours(listeCours);
        assertEquals(listeCours, enseignant.getCours());
    }
}
