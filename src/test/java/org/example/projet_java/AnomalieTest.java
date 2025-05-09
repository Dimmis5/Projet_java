package org.example.projet_java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnomalieTest {

    @Test
    public void testConstructeur(){
        Anomalie anomalie = new Anomalie("Erreur de salle", "La salle attribué est deja occupée");

        assertEquals("Erreur de salle", anomalie.getType());
        assertEquals("La salle attribué est deja occupée", anomalie.getDescription());
    }

    @Test
    public void testSetType(){
        Anomalie anomalie = new Anomalie("Ancien type", "Description");

        anomalie.setType("Nouvelle type");
        assertEquals("Nouvelle type", anomalie.getType());
    }

    @Test
    public void testGetType(){
        Anomalie anomalie = new Anomalie("Erreur", "Plus de connection");

        assertEquals("Plus de connection", anomalie.getDescription());
    }

    @Test
    public void testSetDescription(){
        Anomalie anomalie = new Anomalie("Type", "Ancienne description");
        anomalie.setDescription("Nouvelle description");

        assertEquals("Nouvelle description", anomalie.getDescription());
    }

    @Test
    public void testGetDescription(){
        Anomalie anomalie = new Anomalie("Type", "Erreur moyenne");

        assertEquals("Erreur moyenne", anomalie.getDescription());
    }
}