package org.example.projet_java.model;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistiques {
    private EmploiDuTemps emploiDuTemps;

    public Statistiques() {
        this.emploiDuTemps = null;
    }
    
    public Statistiques(EmploiDuTemps emploiDuTemps) {
        this.emploiDuTemps = emploiDuTemps;
    }

    public EmploiDuTemps getEmploiDuTemps() {
        return emploiDuTemps;
    }

    public void setEmploiDuTemps(EmploiDuTemps emploiDuTemps) {
        this.emploiDuTemps = emploiDuTemps;
    }
    
    public void updateStatistics(EmploiDuTemps emploiDuTemps) {
        this.emploiDuTemps = emploiDuTemps;
    }

    // Taux d'occupation des salles
    public Map<String, Double> getTauxOccupationSalles() {
        Map<String, Double> tauxOccupation = new HashMap<>();
        Map<String, Integer> heuresDispo = new HashMap<>();
        Map<String, Integer> heuresUtilisees = new HashMap<>();
        
        // Total des heures disponibles par semaine (40 heures par exemple)
        int heuresParSemaineParSalle = 40;
        
        // Récupérer toutes les salles uniques
        List<String> sallesIds = emploiDuTemps.getCours().stream()
                .map(c -> c.getSalle().getId())
                .distinct()
                .collect(Collectors.toList());
        
        // Initialiser les heures disponibles pour chaque salle
        for (String salleId : sallesIds) {
            heuresDispo.put(salleId, heuresParSemaineParSalle);
            heuresUtilisees.put(salleId, 0);
        }
        
        // Calculer les heures utilisées par salle
        for (Cours cours : emploiDuTemps.getCours()) {
            String salleId = cours.getSalle().getId();
            Horaire horaire = cours.getHoraire();
            
            // Calculer la durée du cours en heures
            int dureeHeures = horaire.getHeureFin().getHour() - horaire.getHeureDebut().getHour();
            
            // Ajouter la durée aux heures utilisées pour cette salle
            heuresUtilisees.put(salleId, heuresUtilisees.get(salleId) + dureeHeures);
        }
        
        // Calculer le taux d'occupation pour chaque salle
        for (String salleId : sallesIds) {
            double taux = (double) heuresUtilisees.get(salleId) / heuresDispo.get(salleId) * 100;
            tauxOccupation.put(salleId, taux);
        }
        
        return tauxOccupation;
    }
    
    // Répartition des cours par jour de la semaine
    public Map<DayOfWeek, Integer> getRepartitionCoursParJour() {
        Map<DayOfWeek, Integer> coursParJour = new HashMap<>();
        
        for (DayOfWeek jour : DayOfWeek.values()) {
            coursParJour.put(jour, 0);
        }
        
        for (Cours cours : emploiDuTemps.getCours()) {
            DayOfWeek jour = cours.getHoraire().getJour();
            coursParJour.put(jour, coursParJour.get(jour) + 1);
        }
        
        return coursParJour;
    }
    
    // Charge de travail des enseignants
    public Map<String, Integer> getChargeEnseignants() {
        Map<String, Integer> chargeParEnseignant = new HashMap<>();
        
        for (Cours cours : emploiDuTemps.getCours()) {
            if (cours.getEnseignant() == null) continue;
            
            String enseignantId = cours.getEnseignant().getId();
            Horaire horaire = cours.getHoraire();
            
            // Calculer la durée du cours en heures
            int dureeHeures = horaire.getHeureFin().getHour() - horaire.getHeureDebut().getHour();
            
            // Ajouter la durée à la charge de l'enseignant
            chargeParEnseignant.put(enseignantId, 
                                   chargeParEnseignant.getOrDefault(enseignantId, 0) + dureeHeures);
        }
        
        return chargeParEnseignant;
    }
    
    // Nombre moyen d'étudiants par cours
    public double getNombreMoyenEtudiantsParCours() {
        if (emploiDuTemps.getCours().isEmpty()) {
            return 0;
        }
        
        int totalEtudiants = 0;
        for (Cours cours : emploiDuTemps.getCours()) {
            totalEtudiants += cours.getEtudiantsInscrits().size();
        }
        
        return (double) totalEtudiants / emploiDuTemps.getCours().size();
    }
    
    // Pourcentage d'anomalies
    public double getPourcentageAnomalies() {
        int totalCours = emploiDuTemps.getCours().size();
        if (totalCours == 0) {
            return 0;
        }
        
        int totalAnomalies = emploiDuTemps.getAnomalies().size();
        return (double) totalAnomalies / totalCours * 100;
    }

    @Override
    public String toString() {
        return "Statistiques{" +
                "emploiDuTemps=" + emploiDuTemps.getId() +
                ", anomalies=" + emploiDuTemps.getAnomalies().size() +
                ", moyenneEtudiants=" + getNombreMoyenEtudiantsParCours() +
                '}';
    }
}