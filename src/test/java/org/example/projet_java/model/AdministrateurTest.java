package org.example.projet_java.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministrateurTest {

    @Test
    public void testConstructeur() {
        Administrateur admin = new Administrateur("30000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        assertEquals("30000", admin.getId());
        assertEquals("Dupont", admin.getNom());
        assertEquals("Jean", admin.getPrenom());
        assertEquals("jean.dupont@gmail.com", admin.getMail());
        assertEquals("dj123", admin.getMdp());
        assertNotNull(admin);
    }

    @Test
    void testGetId() {
        Administrateur admin = new Administrateur("30000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        assertEquals("30000", admin.getId());
    }

    @Test
    void testSetId() {
        Administrateur admin = new Administrateur("30000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        admin.setId("30001");
        assertEquals("30001", admin.getId());
    }

    @Test
    void testGetNom() {
        Administrateur admin = new Administrateur("30000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        assertEquals("Dupont", admin.getNom());
    }

    @Test
    void testSetNom() {
        Administrateur admin = new Administrateur("30000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        admin.setNom("Durand");
        assertEquals("Durand", admin.getNom());
    }

    @Test
    void testGetPrenom() {
        Administrateur admin = new Administrateur("30000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        assertEquals("Jean", admin.getPrenom());
    }

    @Test
    void testSetPrenom() {
        Administrateur admin = new Administrateur("30000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        admin.setPrenom("Paul");
        assertEquals("Paul", admin.getPrenom());
    }

    @Test
    void testGetMail() {
        Administrateur admin = new Administrateur("30000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        assertEquals("jean.dupont@gmail.com", admin.getMail());
    }

    @Test
    void testSetMail() {
        Administrateur admin = new Administrateur("30000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        admin.setMail("paul.durand@gmail.com");
        assertEquals("paul.durand@gmail.com", admin.getMail());
    }

    @Test
    void testGetMdp() {
        Administrateur admin = new Administrateur("30000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        assertEquals("dj123", admin.getMdp());
    }

    @Test
    void testSetMdp() {
        Administrateur admin = new Administrateur("30000", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");
        admin.setMdp("dj456");
        assertEquals("dj456", admin.getMdp());
    }
}
