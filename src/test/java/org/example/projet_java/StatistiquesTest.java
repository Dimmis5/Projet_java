package org.example.projet_java;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StatistiquesTest {

    @Test
    public void testConstructeur(){
        Statistiques stats = new Statistiques();

        assertNotNull(stats.getUtilisationSalle());
        assertTrue(stats.getUtilisationSalle().isEmpty());
        assertNotNull(stats.getUtilisationEnseignant());
        assertTrue(stats.getUtilisationEnseignant().isEmpty());
    }

    @Test
    public void testGetUtilisationSalle(){
        Statistiques stats = new Statistiques();

        assertNotNull(stats.getUtilisationSalle());
        assertTrue(stats.getUtilisationSalle().isEmpty());
    }

    @Test
    public void testGetUtilisationEnseignant(){
        Statistiques stats = new Statistiques();

        assertNotNull(stats.getUtilisationEnseignant());
        assertTrue(stats.getUtilisationEnseignant().isEmpty());
    }

    @Test
    public void testSetUtilisationSalle(){
        Statistiques stats = new Statistiques();
        Salle salle = new Salle(120, "Etage 2", 10, true);

        HashMap<Salle, Double> mapSalle = new HashMap<>();
        mapSalle.put(salle, 75.0);

        stats.setUtilisationSalle(mapSalle);

        assertEquals(75.0, stats.getUtilisationSalle().get(salle));
    }

    @Test
    public void testSetUtilisationEnseigant(){
        Statistiques stats = new Statistiques();
        Enseignant enseignant = new Enseignant(9,"Meyer", "Perle", "mp@gmail.com", "mp123");

        HashMap<Enseignant, Double> mapEnseignant = new HashMap<>();
        mapEnseignant.put(enseignant, 60.0);

        stats.setUtilisationEnseignant(mapEnseignant);

        assertEquals(60.0, stats.getUtilisationEnseignant().get(enseignant));
    }
}