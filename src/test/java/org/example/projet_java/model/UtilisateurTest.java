/*
package org.example.projet_java.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTest {

    @Test
    public void testConstructorAndGetters() {
        Utilisateur user = new Utilisateur("id123", "Dupont", "Jean", "jean.dupont@mail.com", "pass123");

        assertEquals("id123", user.getId());
        assertEquals("Dupont", user.getNom());
        assertEquals("Jean", user.getPrenom());
        assertEquals("jean.dupont@mail.com", user.getMail());
        assertEquals("pass123", user.getMdp());
    }

    @Test
    public void testSetters() {
        Utilisateur user = new Utilisateur("id123", "Dupont", "Jean", "jean.dupont@mail.com", "pass123");

        user.setId("id456");
        user.setNom("Martin");
        user.setPrenom("Claire");
        user.setMail("claire.martin@mail.com");
        user.setMdp("newpass");

        assertEquals("id456", user.getId());
        assertEquals("Martin", user.getNom());
        assertEquals("Claire", user.getPrenom());
        assertEquals("claire.martin@mail.com", user.getMail());
        assertEquals("newpass", user.getMdp());
    }

    @Test
    public void testConsulter() {
        Utilisateur user = new Utilisateur("id123", "Dupont", "Jean", "jean.dupont@mail.com", "pass123");

        EmploiDuTemps emploi = new EmploiDuTemps("1","2");
        EmploiDuTemps result = user.consulter(emploi);

        assertSame(emploi, result, "La méthode consulter doit retourner l'objet EmploiDuTemps passé en paramètre");
    }

}*/