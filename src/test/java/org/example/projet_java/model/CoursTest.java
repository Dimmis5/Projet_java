package org.example.projet_java.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CoursTest {

    @Test
    public void testConstructeur() {
        Cours cours = new Cours("1", "101", "Mathématiques", "21/05/2025", "08h00", "10h00", "20000", "G4A", false);

        assertEquals("1", cours.getId_cours());
        assertEquals("101", cours.getId_salle());
        assertEquals("Mathématiques", cours.getMatiere());
        assertEquals("21/05/2025", cours.getDate());
        assertEquals("08h00", cours.getHeure_debut());
        assertEquals("10h00", cours.getHeure_fin());
        assertEquals("20000", cours.getId_enseignant());
        assertEquals("G4A", cours.getClasse());
        assertEquals(false, cours.isAnnulation());
        assertNotNull(cours);
    }

    @Test
    void testGetters() {
        Cours cours = new Cours("1", "101", "Mathématiques", "21/05/2025", "08h00", "10h00", "20000", "G4A", false);

        assertEquals("1", cours.getId_cours());
        assertEquals("101", cours.getId_salle());
        assertEquals("Mathématiques", cours.getMatiere());
        assertEquals("21/05/2025", cours.getDate());
        assertEquals("08h00", cours.getHeure_debut());
        assertEquals("10h00", cours.getHeure_fin());
        assertEquals("20000", cours.getId_enseignant());
        assertEquals("G4A", cours.getClasse());
        assertFalse(cours.isAnnulation());
        assertNotNull(cours.getEtudiants());
        assertTrue(cours.getEtudiants().isEmpty());
    }

    @Test
    void testSetters() {
        Cours cours = new Cours("1", "101", "Mathématiques", "21/05/2025", "08h00", "10h00", "20000", "G4A", false);

        cours.setId_cours("2");
        cours.setId_salle("102");
        cours.setMatiere("Physique");
        cours.setDate("02/06/2025");
        cours.setHeure_debut("09h00");
        cours.setHeure_fin("11h00");
        cours.setEnseignant("20001");
        cours.setClasse("G4B");
        cours.setAnnulation(true);

        ArrayList<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant("10000", "Dupont", "Jean", "jean.dupont@example.com", "pass123", "G4A"));
        cours.setEtudiants(etudiants);

        assertEquals("2", cours.getId_cours());
        assertEquals("102", cours.getId_salle());
        assertEquals("Physique", cours.getMatiere());
        assertEquals("02/06/2025", cours.getDate());
        assertEquals("09h00", cours.getHeure_debut());
        assertEquals("11h00", cours.getHeure_fin());
        assertEquals("20001", cours.getId_enseignant());
        assertEquals("G4B", cours.getClasse());
        assertTrue(cours.isAnnulation());
        assertEquals(1, cours.getEtudiants().size());
        assertEquals("10000", cours.getEtudiants().getFirst().getId());
    }
}
