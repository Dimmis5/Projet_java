package org.example.projet_java.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StatistiquesTest {

    @Test
    public void testConstructorAndGetters() {
        Statistiques stats = new Statistiques();

        assertNotNull(stats.getUtilisationSalle());
        assertNotNull(stats.getUtilisationEnseignant());
        assertTrue(stats.getUtilisationSalle().isEmpty());
        assertTrue(stats.getUtilisationEnseignant().isEmpty());
    }

    @Test
    public void testSettersAndGetters() {
        Statistiques stats = new Statistiques();

        HashMap<Salle, Double> salleMap = new HashMap<>();
        Salle salle = new Salle("S1", "Bâtiment A", "30", true);
        salleMap.put(salle, 75.0);

        HashMap<Enseignant, Double> enseignantMap = new HashMap<>();
        Enseignant enseignant = new Enseignant("E1", "Dupont", "Jean", "jean.dupont@mail.com", "pass");
        enseignantMap.put(enseignant, 60.5);

        stats.setUtilisationSalle(salleMap);
        stats.setUtilisationEnseignant(enseignantMap);

        assertEquals(1, stats.getUtilisationSalle().size());
        assertEquals(75.0, stats.getUtilisationSalle().get(salle));

        assertEquals(1, stats.getUtilisationEnseignant().size());
        assertEquals(60.5, stats.getUtilisationEnseignant().get(enseignant));
    }

}