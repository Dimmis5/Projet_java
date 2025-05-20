package org.example.projet_java;

import org.example.projet_java.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CoursTest {

    @Test
    public void testConstructeur(){
        Salle salle = new Salle(202,"Vendée", 50, true);
        Enseignant enseignant = new Enseignant(4, "Corre", "Anaelle", "correanaelle@gmail.com", "op123");
        Horaire horaire = new Horaire("Mardi", "14h00", "15h00");
        Cours cours = new Cours(1, salle, "SII", horaire,enseignant);

        assertEquals(1, cours.getId_cours());
        assertEquals("Vendée", cours.getSalle().getLocalisation());
        assertEquals("SII", cours.getMatiere());
        assertEquals("14h00", cours.getHoraire().getHeure_debut());
        assertEquals(enseignant, cours.getEnseignant());
        assertNotNull(cours.getEnseignant());
        assertFalse(cours.isAnnule());
    }

    @Test
    public void testSetIdCours(){
        Salle salle = new Salle(202,"Vendée", 50, true);
        Enseignant enseignant = new Enseignant(4, "Corre", "Anaelle", "correanaelle@gmail.com", "op123");
        Horaire horaire = new Horaire("Mardi", "14h00", "15h00");
        Cours cours = new Cours(1, salle, "SII", horaire,enseignant);

        cours.setId_cours(42);
        assertEquals(42, cours.getId_cours());
    }

    @Test
    public void testSetMatiere(){
        Salle salle = new Salle(202,"Vendée", 50, true);
        Enseignant enseignant = new Enseignant(4, "Corre", "Anaelle", "correanaelle@gmail.com", "op123");
        Horaire horaire = new Horaire("Mardi", "14h00", "15h00");
        Cours cours = new Cours(1, salle, "", horaire,enseignant);

        cours.setMatiere("SVT");

        assertEquals("SVT", cours.getMatiere());
    }

    @Test
    public void testSetSalle(){
        Salle salle = new Salle(202,"Vendée", 50, true);
        Enseignant enseignant = new Enseignant(4, "Corre", "Anaelle", "correanaelle@gmail.com", "op123");
        Horaire horaire = new Horaire("Mardi", "14h00", "15h00");
        Cours cours = new Cours(1, salle, "", horaire,enseignant);

        cours.setSalle(salle);
        assertEquals(salle, cours.getSalle());
    }

    @Test
    public void testSetHoraire(){
        Salle salle = new Salle(202,"Vendée", 50, true);
        Enseignant enseignant = new Enseignant(4, "Corre", "Anaelle", "correanaelle@gmail.com", "op123");
        Horaire horaire = new Horaire("Mardi", "14h00", "15h00");
        Cours cours = new Cours(1, salle, "", horaire,enseignant);

        cours.setHoraire(horaire);
        assertEquals(horaire, cours.getHoraire());
    }

    @Test
    public void testSetEnseignant(){
        Salle salle = new Salle(202,"Vendée", 50, true);
        Enseignant enseignant = new Enseignant(4, "Corre", "Anaelle", "correanaelle@gmail.com", "op123");
        Horaire horaire = new Horaire("Mardi", "14h00", "15h00");
        Cours cours = new Cours(1, salle, "", horaire,enseignant);

        cours.setEnseignant(enseignant);
        assertEquals(enseignant, cours.getEnseignant());
    }

    @Test
    public void testSetEtudiants() {
        Salle salle = new Salle(202,"Vendée", 50, true);
        Enseignant enseignant = new Enseignant(1, "Corre", "Anaelle", "correanaelle@gmail.com", "op123");
        Horaire horaire = new Horaire("Mardi", "14h00", "15h00");
        Cours cours = new Cours(0, salle, "", horaire,enseignant);

        ArrayList<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant(2, "Aussel", "Meline", "AussiMeline@gmail.com", "Tanguy"));
        cours.setEtudiants(etudiants);

        assertEquals(1, cours.getEtudiants().size());
        assertEquals("Aussel", cours.getEtudiants().getFirst().getNom());
    }

    @Test
    public void testAnnulation(){
        Salle salle = new Salle(202,"Vendée", 50, true);
        Enseignant enseignant = new Enseignant(1, "Corre", "Anaelle", "correanaelle@gmail.com", "op123");
        Horaire horaire = new Horaire("Mardi", "14h00", "15h00");
        Cours cours = new Cours(0, salle, "", horaire,enseignant);

        assertFalse(cours.isAnnule());
        cours.setAnnule(true);
        assertTrue(cours.isAnnule());
    }

}