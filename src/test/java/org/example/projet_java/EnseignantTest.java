package org.example.projet_java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnseignantTest {

    @Test
    public void testConstructeur(){
        Enseignant enseignant = new Enseignant(1, "Bouyer","Maruis", "bouyermaruis@gmail.com","w123");

        assertNotNull(enseignant);
    }

    @Test
    public void testGet(){
        Enseignant enseignant = new Enseignant(1, "Bouyer","Maruis", "bouyermaruis@gmail.com","w123");

        assertEquals(1, enseignant.id);
        assertEquals("Bouyer", enseignant.nom);
        assertEquals("Maruis", enseignant.prenom);
        assertEquals("bouyermaruis@gmail.com", enseignant.mail);
        assertEquals("w123", enseignant.mdp);
    }

    @Test
    public void testSet(){
        Enseignant enseignant = new Enseignant(1, "Bouyer","Maruis", "bouyermaruis@gmail.com","w123");

        enseignant.setId(2);
        enseignant.setNom("Valentin");
        enseignant.setPrenom("Marie");
        enseignant.setMail("valentin.marie@gmail.com");
        enseignant.setMdp("h123");

        assertEquals(2, enseignant.getId());
        assertEquals("Valentin", enseignant.getNom());
        assertEquals("Marie", enseignant.getPrenom());
        assertEquals("valentin.marie@gmail.com", enseignant.getMail());
        assertEquals("h123", enseignant.getMdp());
    }


    @Test
    public void testConsulterEmploiDuTemps(){
        Enseignant enseignant = new Enseignant(1, "Bouyer","Maruis", "bouyermaruis@gmail.com","w123");
        EmploiDuTemps emploi = new EmploiDuTemps(0);
        EmploiDuTemps edt = enseignant.consulter(emploi);

        assertEquals(2, edt.getCours().size());
        assertEquals("Mathematiques", edt.getCours().getFirst().getMatiere());
        assertEquals(1, edt.getCours().getFirst().getEnseignant().getId());
    }
}