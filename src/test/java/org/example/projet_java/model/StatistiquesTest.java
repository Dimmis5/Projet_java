package org.example.projet_java.model;


import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StatistiquesTest {

    @Test
    public void testConstructor() {
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
        Salle salle = new Salle("202", "Etage 2", 30);
        salleMap.put(salle, 75.0);

        HashMap<Enseignant, Double> enseignantMap = new HashMap<>();
        Enseignant enseignant = new Enseignant("20000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        enseignantMap.put(enseignant, 60.5);

        stats.setUtilisationSalle(salleMap);
        stats.setUtilisationEnseignant(enseignantMap);

        assertEquals(1, stats.getUtilisationSalle().size());
        assertEquals(75.0, stats.getUtilisationSalle().get(salle));

        assertEquals(1, stats.getUtilisationEnseignant().size());
        assertEquals(60.5, stats.getUtilisationEnseignant().get(enseignant));
    }

}