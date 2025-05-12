package org.example.projet_java;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SalleTest {

    @Test
    public void testConstructeur(){
        Salle salle = new Salle(101, "Etage 2", 50, true);

        assertEquals(101, salle.id_salle);
        assertEquals("Etage 2", salle.localisation);
        assertEquals(50, salle.capacite);
        assertTrue(salle.getStatut());
        assertNotNull(salle.getEquipement());
        assertTrue(salle.getEquipement().isEmpty());
    }

    @Test
    public void testGetters(){
        Salle salle = new Salle(101, "Etage 2", 50, false);

        assertEquals(101, salle.getId_salle());
        assertEquals("Etage 2", salle.getLocalisation());
        assertEquals(50, salle.getCapacite());
        assertFalse(salle.getStatut());
    }

    @Test
    public void testSetters(){
        Salle salle = new Salle(101, "Etage 2", 50, true);

        salle.setId_salle(303);
        salle.setLocalisation("Etage 3");
        salle.setCapacite(40);
        salle.setStatut(false);

        assertEquals(303, salle.getId_salle());
        assertEquals("Etage 3", salle.getLocalisation());
        assertEquals(40, salle.getCapacite());
        assertFalse(salle.getStatut());
    }

    @Test
    public void testEquipement(){
        Salle salle = new Salle(101, "Etage 2", 50, true);
        ArrayList<String> equipements = new ArrayList<>(Arrays.asList("Projecteur", "Tableau blanc"));
        salle.setEquipement(equipements);

        assertEquals(2, salle.getEquipement().size());
        assertTrue(salle.getEquipement().contains("Projecteur"));
        assertTrue(salle.getEquipement().contains("Tableau blanc"));
    }


}