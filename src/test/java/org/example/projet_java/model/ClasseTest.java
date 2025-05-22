package org.example.projet_java.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClasseTest {
    @Test
    public void testConstructorAndGetters() {
        Classe classe = new Classe("G4A", "31");

        assertEquals("G4A", classe.getClasse());
        assertEquals("31", classe.getEffectif());
        assertNotNull(classe);
    }

    @Test
    public void testSetters() {
        Classe classe = new Classe("G4A", "31");

        classe.setClasse("G4B");
        classe.setEffectif("28");

        assertEquals("G4B", classe.getClasse());
        assertEquals("28", classe.getEffectif());
    }
}