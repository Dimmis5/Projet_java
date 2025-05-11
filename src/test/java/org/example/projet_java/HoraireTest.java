package org.example.projet_java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HoraireTest {

    @Test
    public void testConstructeur(){
        Horaire horaire = new Horaire("Lundi", "8h00", "10h00");
        assertNotNull(horaire);
    }

    @Test
    public void testGetters(){
        Horaire horaire = new Horaire("Lundi", "8h00", "10h00");

        assertEquals("Lundi", horaire.getJour());
        assertEquals("8h00", horaire.getHeure_debut());
        assertEquals("10h00", horaire.getHeure_fin());
    }

    @Test
    public void testSetJour(){
        Horaire horaire = new Horaire("Lundi", "8h00", "10h00");
        horaire.setJour("Mardi");

        assertEquals("Mardi", horaire.getJour());
    }

    @Test
    public void testSetHeureDebut(){
        Horaire horaire = new Horaire("Lundi", "8h00", "10h00");
        horaire.setHeure_debut("9h00");

        assertEquals("9h00", horaire.getHeure_debut());
    }

    @Test
    public void testSetHeureFin(){
        Horaire horaire = new Horaire("Lundi", "8h00", "10h00");
        horaire.setHeure_fin("11h00");

        assertEquals("11h00", horaire.getHeure_fin());
    }
}