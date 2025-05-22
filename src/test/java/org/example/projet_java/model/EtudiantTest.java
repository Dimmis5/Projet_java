package org.example.projet_java.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class EtudiantTest {

    @Test
    public void testConstructeur() {
        Etudiant etudiant = new Etudiant("10000", "Dupont", "Marie", "marie.dupont@gmail.com", "md123", "G4A");
        assertEquals("10000", etudiant.getId());
        assertEquals("Dupont", etudiant.getNom());
        assertEquals("Marie", etudiant.getPrenom());
        assertEquals("marie.dupont@gmail.com", etudiant.getMail());
        assertEquals("md123", etudiant.getMdp());
        assertEquals("G4A", etudiant.getClasse());
        assertNotNull(etudiant);
    }

    @Test
    public void testGetters() {
        Etudiant etudiant = new Etudiant("10000", "Dupont", "Marie", "marie.dupont@gmail.com", "md123", "G4A");

        assertEquals("10000", etudiant.getId());
        assertEquals("Dupont", etudiant.getNom());
        assertEquals("Marie", etudiant.getPrenom());
        assertEquals("marie.dupont@gmail.com", etudiant.getMail());
        assertEquals("md123", etudiant.getMdp());
        assertEquals("G4A", etudiant.getClasse());

        assertNotNull(etudiant.getCours());
        assertTrue(etudiant.getCours().isEmpty());
    }

    @Test
    public void testSetters() {
        Etudiant etudiant = new Etudiant("10000", "Dupont", "Marie", "marie.dupont@gmail.com", "md123", "G4A");

        etudiant.setId("10001");
        etudiant.setNom("Martin");
        etudiant.setPrenom("Paul");
        etudiant.setMail("paul.martin@gmail.com");
        etudiant.setClasse("G4B");

        assertEquals("10001", etudiant.getId());
        assertEquals("Martin", etudiant.getNom());
        assertEquals("Paul", etudiant.getPrenom());
        assertEquals("paul.martin@gmail.com", etudiant.getMail());
        assertEquals("G4B", etudiant.getClasse());
    }

    @Test
    public void testSetCours() {
        Etudiant etudiant = new Etudiant("10000", "Dupont", "Marie", "marie.dupont@gmail.com", "md123", "G4A");

        ArrayList<Cours> listeCours = new ArrayList<>();

        etudiant.setCours(listeCours);
        assertEquals(listeCours, etudiant.getCours());
    }
}
