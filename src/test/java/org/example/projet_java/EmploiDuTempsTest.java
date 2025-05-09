package org.example.projet_java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmploiDuTempsTest {

    @Test
    public void testConstructeur(){
        int id = 42;
        EmploiDuTemps edt = new EmploiDuTemps(id);

        assertEquals(id, edt.getId(), "L'identifiant doit etre correctement initialisé");
        assertNotNull(edt.getCours(), "La liste de cours ne doit pas être null");
        assertTrue(edt.getCours().isEmpty(), "La liste de cours doit être vide au début");
    }

    @Test
    public void testCreationEmploiDuTemps(){
        EmploiDuTemps edt = new EmploiDuTemps(1);

        assertEquals(1, edt.getId());
        assertTrue(edt.getCours().isEmpty(), "LA liste de cours doit etre vide au début");
    }

    @Test
    public void testSetCoursMethod(){
        EmploiDuTemps edt = new EmploiDuTemps(1);

        Salle salle = new Salle(201, "Amhi A", 100, true);
        Horaire horaire = new Horaire("Lundi", "17h00", "18h00");
        Enseignant enseignant = new Enseignant(10, "Lucas", "Ronan", "LucasRonan@gmail.com", "Jade132");
        Cours cours = new Cours(12, salle, "Espagnol", horaire, enseignant);

        edt.setCours(edt, cours);

        assertEquals(1, edt.getCours().size());
        assertEquals("Espagnol", edt.getCours().getFirst().getMatiere());
    }

    @Test
    public void testGetEleve() {
        EmploiDuTemps edt = new EmploiDuTemps(0).getEleve(1);

        assertEquals(2, edt.getCours().size());
        assertEquals("Mathematiques", edt.getCours().get(0).getMatiere());
        assertEquals("Anglais", edt.getCours().get(1).getMatiere());
        //Revenir dessus
        //assertEquals("Durand", edt.getCours().get(0).getEnseignant().getNom());

    }

    @Test
    public void testGetEnseignant(){
        int enseignantId = 0;
        EmploiDuTemps edt = new EmploiDuTemps(0).getEnseignant(enseignantId);

        assertEquals(2, edt.getCours().size());
        assertEquals(enseignantId, edt.getCours().get(0).getEnseignant().getId());
        assertEquals(enseignantId, edt.getCours().get(1).getEnseignant().getId());
    }

}