package org.example.projet_java;

import org.example.projet_java.model.EmploiDuTemps;
import org.example.projet_java.model.Utilisateur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTest {

    @Test
    public void testConstructeur(){
        Utilisateur utilisateur = new Utilisateur(41, "Maltre", "Baptiste", "mb@gmail.com", "mb123");

        assertNotNull(utilisateur);
        assertEquals(41, utilisateur.getId());
        assertEquals("Maltre", utilisateur.getNom());
        assertEquals("Baptiste", utilisateur.getPrenom());
        assertEquals("mb@gmail.com", utilisateur.getMail());
        assertEquals("mb123", utilisateur.getMdp());
    }

    @Test
    public void testGet(){
        Utilisateur utilisateur = new Utilisateur(41, "Maltre", "Baptiste", "mb@gmail.com", "mb123");

        assertEquals(41, utilisateur.getId());
        assertEquals("Maltre", utilisateur.getNom());
        assertEquals("Baptiste", utilisateur.getPrenom());
        assertEquals("mb@gmail.com", utilisateur.getMail());
        assertEquals("mb123", utilisateur.getMdp());
    }

    @Test
    public void testSet(){
        Utilisateur utilisateur = new Utilisateur(41, "Maltre", "Baptiste", "mb@gmail.com", "mb123");

        utilisateur.setId(7);
        utilisateur.setNom("Popa");
        utilisateur.setPrenom("Pierre");
        utilisateur.setMail("Pp@gmail.com");

        assertEquals(7, utilisateur.getId());
        assertEquals("Popa", utilisateur.getNom());
        assertEquals("Pierre", utilisateur.getPrenom());
        assertEquals("Pp@gmail.com", utilisateur.getMail());
    }

    @Test
    public void testConsulterReturnsSameEmploi(){
        Utilisateur utilisateur = new Utilisateur(41, "Maltre", "Baptiste", "mb@gmail.com", "mb123");
        EmploiDuTemps edt = new EmploiDuTemps(99);

        EmploiDuTemps retourne = utilisateur.consulter(edt);

        assertSame(edt, retourne);
    }
}