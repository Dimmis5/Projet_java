package org.example.projet_java.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CoursTest {

    @Test
    void testGetters() {
        Cours cours = new Cours("C001", "S101", "Maths", "2025-05-21", "08:00", "10:00", "E001", "2A", false);

        assertEquals("C001", cours.getId_cours());
        assertEquals("S101", cours.getId_salle());
        assertEquals("Maths", cours.getMatiere());
        assertEquals("2025-05-21", cours.getDate());
        assertEquals("08:00", cours.getHeure_debut());
        assertEquals("10:00", cours.getHeure_fin());
        assertEquals("E001", cours.getId_enseignant());
        assertEquals("2A", cours.getClasse());
        assertFalse(cours.isAnnulation());
        assertNotNull(cours.getEtudiants());
        assertTrue(cours.getEtudiants().isEmpty());
    }

    @Test
    void testSetters() {
        Cours cours = new Cours("C001", "S101", "Maths", "2025-05-21", "08:00", "10:00", "E001", "2A", false);

        cours.setId_cours("C002");
        cours.setId_salle("S102");
        cours.setMatiere("Physique");
        cours.setDate("2025-06-01");
        cours.setHeure_debut("09:00");
        cours.setHeure_fin("11:00");
        cours.setEnseignant("E002");
        cours.setClasse("3A");
        cours.setAnnulation(true);

        ArrayList<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant("ET001", "Dupont", "Jean", "jean.dupont@example.com", "pass123", "3A"));
        cours.setEtudiants(etudiants);

        assertEquals("C002", cours.getId_cours());
        assertEquals("S102", cours.getId_salle());
        assertEquals("Physique", cours.getMatiere());
        assertEquals("2025-06-01", cours.getDate());
        assertEquals("09:00", cours.getHeure_debut());
        assertEquals("11:00", cours.getHeure_fin());
        assertEquals("E002", cours.getId_enseignant());
        assertEquals("3A", cours.getClasse());
        assertTrue(cours.isAnnulation());
        assertEquals(1, cours.getEtudiants().size());
        assertEquals("ET001", cours.getEtudiants().getFirst().getId());
    }
}
