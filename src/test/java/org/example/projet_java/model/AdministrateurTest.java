package org.example.projet_java.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministrateurTest {

    @Test
    void testGetId() {
        Administrateur admin = new Administrateur("A001", "Dupont", "Jean", "jean.dupont@example.com", "password123");
        assertEquals("A001", admin.getId());
    }

    @Test
    void testSetId() {
        Administrateur admin = new Administrateur("A001", "Dupont", "Jean", "jean.dupont@example.com", "password123");
        admin.setId("A002");
        assertEquals("A002", admin.getId());
    }

    @Test
    void testGetNom() {
        Administrateur admin = new Administrateur("A001", "Dupont", "Jean", "jean.dupont@example.com", "password123");
        assertEquals("Dupont", admin.getNom());
    }

    @Test
    void testSetNom() {
        Administrateur admin = new Administrateur("A001", "Dupont", "Jean", "jean.dupont@example.com", "password123");
        admin.setNom("Durand");
        assertEquals("Durand", admin.getNom());
    }

    @Test
    void testGetPrenom() {
        Administrateur admin = new Administrateur("A001", "Dupont", "Jean", "jean.dupont@example.com", "password123");
        assertEquals("Jean", admin.getPrenom());
    }

    @Test
    void testSetPrenom() {
        Administrateur admin = new Administrateur("A001", "Dupont", "Jean", "jean.dupont@example.com", "password123");
        admin.setPrenom("Paul");
        assertEquals("Paul", admin.getPrenom());
    }

    @Test
    void testGetMail() {
        Administrateur admin = new Administrateur("A001", "Dupont", "Jean", "jean.dupont@example.com", "password123");
        assertEquals("jean.dupont@example.com", admin.getMail());
    }

    @Test
    void testSetMail() {
        Administrateur admin = new Administrateur("A001", "Dupont", "Jean", "jean.dupont@example.com", "password123");
        admin.setMail("paul.durand@example.com");
        assertEquals("paul.durand@example.com", admin.getMail());
    }

    @Test
    void testGetMdp() {
        Administrateur admin = new Administrateur("A001", "Dupont", "Jean", "jean.dupont@example.com", "password123");
        assertEquals("password123", admin.getMdp());
    }

    @Test
    void testSetMdp() {
        Administrateur admin = new Administrateur("A001", "Dupont", "Jean", "jean.dupont@example.com", "password123");
        admin.setMdp("newpass456");
        assertEquals("newpass456", admin.getMdp());
    }
}
