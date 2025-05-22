package org.example.projet_java.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTest {

    @Test
    public void testConstructorAndGetters() {
        Utilisateur utilisateur = new Utilisateur("00001", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");

        assertEquals("00001", utilisateur.getId());
        assertEquals("Dupont", utilisateur.getNom());
        assertEquals("Jean", utilisateur.getPrenom());
        assertEquals("jean.dupont@gmail.com", utilisateur.getMail());
        assertEquals("dj123", utilisateur.getMdp());
        assertNotNull(utilisateur);
    }

    @Test
    public void testSetters() {
        Utilisateur utilisateur = new Utilisateur("00001", "Dupont", "Jean", "jean.dupont@gmail.com", "dj123");

        utilisateur.setId("00002");
        utilisateur.setNom("Martin");
        utilisateur.setPrenom("Claire");
        utilisateur.setMail("claire.martin@gmail.com");
        utilisateur.setMdp("mc123");

        assertEquals("00002", utilisateur.getId());
        assertEquals("Martin", utilisateur.getNom());
        assertEquals("Claire", utilisateur.getPrenom());
        assertEquals("claire.martin@gmail.com", utilisateur.getMail());
        assertEquals("mc123", utilisateur.getMdp());
    }
}