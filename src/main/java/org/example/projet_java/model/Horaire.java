package org.example.projet_java.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Horaire {
    private String id;
    private DayOfWeek jour;
    private LocalDate date; // Ajout du champ date
    private LocalTime heureDebut;
    private LocalTime heureFin;

    public Horaire() {
    }

    public Horaire(String id, DayOfWeek jour, LocalDate date, LocalTime heureDebut, LocalTime heureFin) {
        this.id = id;
        this.jour = jour;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DayOfWeek getJour() {
        return jour;
    }

    public void setJour(DayOfWeek jour) {
        this.jour = jour;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
        if (date != null) {
            this.jour = date.getDayOfWeek();
        }
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public boolean chavauchementAvec(Horaire autreHoraire) {
        // Si les dates sont différentes, pas de chevauchement
        if (this.date != null && autreHoraire.date != null && !this.date.equals(autreHoraire.date)) {
            return false;
        }
        
        // Si les dates sont null, on vérifie les jours de la semaine
        if ((this.date == null || autreHoraire.date == null) && this.jour != autreHoraire.jour) {
            return false;
        }
        
        return !(this.heureFin.isBefore(autreHoraire.heureDebut) || 
                 this.heureDebut.isAfter(autreHoraire.heureFin));
    }

    @Override
    public String toString() {
        return "Horaire{" +
                "id='" + id + '\'' +
                ", jour=" + jour +
                ", date=" + date +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                '}';
    }
}