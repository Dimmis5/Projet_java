package org.example.projet_java.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EmploiDuTempsTest {

    @Test
    public void testConstructeur() {
        EmploiDuTemps edt = new EmploiDuTemps("10000", "1");
        assertEquals("10000", edt.getId_etudiant());
        assertEquals("1", edt.getId_cours());
        assertNotNull(edt);
    }

    @Test
    public void testGettersSetters() {
        EmploiDuTemps edt = new EmploiDuTemps("10000", "1");

        edt.setId_etudiant("20000");
        assertEquals("20000", edt.getId_etudiant());

        edt.setId_cours("20000");
        assertEquals("20000", edt.getId_cours());
    }
}
