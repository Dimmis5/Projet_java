package org.example.projet_java.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SalleTest {
    @Test
    public void testConstructorAndGetters() {
        Salle salle = new Salle("101", "Etage 1", 50);

        assertEquals("101", salle.getId_salle());
        assertEquals("Etage 1", salle.getLocalisation());
        assertEquals(50, salle.getCapacite());
        assertTrue(salle.getEquipement().isEmpty());
    }

    @Test
    public void testSetters() {
        Salle salle = new Salle("101", "Etage 2", 50);

        salle.setId_salle("202");
        salle.setLocalisation("Etage 2");
        salle.setCapacite(100);

        ArrayList<String> equipements = new ArrayList<>(Arrays.asList("Vidéoprojecteur"));
        salle.setEquipement(equipements);

        assertEquals("202", salle.getId_salle());
        assertEquals("Etage 2", salle.getLocalisation());
        assertEquals(100, salle.getCapacite());
        assertEquals(1, salle.getEquipement().size());
        assertTrue(salle.getEquipement().contains("Vidéoprojecteur"));
    }

}