package org.example.projet_java.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SalleTest {
    @Test
    public void testConstructorAndGetters() {
        Salle salle = new Salle("S101", "Bâtiment A", "50", true);

        assertEquals("S101", salle.getId_salle());
        assertEquals("Bâtiment A", salle.getLocalisation());
        assertEquals("50", salle.getCapacite());
        assertTrue(salle.getStatut());
        assertNotNull(salle.getEquipement());
        assertTrue(salle.getEquipement().isEmpty());
    }

    @Test
    public void testSetters() {
        Salle salle = new Salle("S101", "Bâtiment A", "50", true);

        salle.setId_salle("S202");
        salle.setLocalisation("Bâtiment B");
        salle.setCapacite("100");
        salle.setStatut(false);

        ArrayList<String> equipements = new ArrayList<>(Arrays.asList("Projecteur", "Tableau"));
        salle.setEquipement(equipements);

        assertEquals("S202", salle.getId_salle());
        assertEquals("Bâtiment B", salle.getLocalisation());
        assertEquals("100", salle.getCapacite());
        assertFalse(salle.getStatut());
        assertEquals(2, salle.getEquipement().size());
        assertTrue(salle.getEquipement().contains("Projecteur"));
        assertTrue(salle.getEquipement().contains("Tableau"));
    }

}