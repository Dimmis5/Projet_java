package org.example.projet_java.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmploiDuTemps {
    private String id;
    private String semestre;
    private List<Cours> cours;
    private List<Anomalie> anomalies;

    public EmploiDuTemps() {
        this.cours = new ArrayList<>();
        this.anomalies = new ArrayList<>();
    }

    public EmploiDuTemps(String id, String semestre) {
        this.id = id;
        this.semestre = semestre;
        this.cours = new ArrayList<>();
        this.anomalies = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
        verifierAnomalies();
    }

    public List<Anomalie> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomalie> anomalies) {
        this.anomalies = anomalies;
    }

    public boolean ajouterCours(Cours nouveauCours) {
        // Vérification des conflits avec les cours existants
        for (Cours cours : this.cours) {
            if (cours.chavauchementAvec(nouveauCours)) {
                Anomalie anomalie = Anomalie.creerAnomalieConflit(cours, nouveauCours);
                anomalies.add(anomalie);
                return false;
            }
        }
        
        // Vérification de la capacité de la salle
        if (nouveauCours.getEtudiantsInscrits().size() > nouveauCours.getSalle().getCapacite()) {
            Anomalie anomalie = Anomalie.creerAnomalieCapacite(nouveauCours);
            anomalies.add(anomalie);
            return false;
        }
        
        this.cours.add(nouveauCours);
        return true;
    }

    public void verifierAnomalies() {
        anomalies.clear();
        
        // Vérification des conflits entre cours
        for (int i = 0; i < cours.size(); i++) {
            for (int j = i + 1; j < cours.size(); j++) {
                if (cours.get(i).chavauchementAvec(cours.get(j))) {
                    anomalies.add(Anomalie.creerAnomalieConflit(cours.get(i), cours.get(j)));
                }
            }
            
            // Vérification des capacités de salle
            if (cours.get(i).getEtudiantsInscrits().size() > cours.get(i).getSalle().getCapacite()) {
                anomalies.add(Anomalie.creerAnomalieCapacite(cours.get(i)));
            }
        }
    }

    public List<Cours> getCoursParJour(DayOfWeek jour) {
        return cours.stream()
                .filter(c -> c.getHoraire().getJour() == jour)
                .collect(Collectors.toList());
    }

    public List<Cours> getCoursParEnseignant(String enseignantId) {
        return cours.stream()
                .filter(c -> c.getEnseignant() != null && c.getEnseignant().getId().equals(enseignantId))
                .collect(Collectors.toList());
    }

    public List<Cours> getCoursParEtudiant(String etudiantId) {
        return cours.stream()
                .filter(c -> c.getEtudiantsInscrits().stream()
                        .anyMatch(e -> e.getId().equals(etudiantId)))
                .collect(Collectors.toList());
    }

    public Map<DayOfWeek, List<Cours>> getCoursParJourDeLaSemaine() {
        Map<DayOfWeek, List<Cours>> coursParJour = new HashMap<>();
        for (DayOfWeek jour : DayOfWeek.values()) {
            coursParJour.put(jour, getCoursParJour(jour));
        }
        return coursParJour;
    }
    
    public List<Cours> getCoursForDate(LocalDate date) {
        return cours.stream()
                .filter(c -> c.getHoraire().getDate().equals(date))
                .collect(Collectors.toList());
    }
    
    public List<Cours> getCoursForPeriod(LocalDate dateDebut, LocalDate dateFin) {
        return cours.stream()
                .filter(c -> {
                    LocalDate coursDate = c.getHoraire().getDate();
                    return !coursDate.isBefore(dateDebut) && !coursDate.isAfter(dateFin);
                })
                .collect(Collectors.toList());
    }
    
    public List<Anomalie> detecterAnomalies() {
        verifierAnomalies();
        return anomalies;
    }
    
    public void resoudreAnomalie(Anomalie anomalie) {
        // Logique pour résoudre une anomalie
        // Par exemple, déplacer un cours
        anomalies.remove(anomalie);
    }
    
    public List<Salle> getSalles() {
        // Retourner toutes les salles utilisées dans l'emploi du temps
        return cours.stream()
                .map(Cours::getSalle)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "EmploiDuTemps{" +
                "id='" + id + '\'' +
                ", semestre='" + semestre + '\'' +
                ", nbCours=" + cours.size() +
                ", nbAnomalies=" + anomalies.size() +
                '}';
    }
}