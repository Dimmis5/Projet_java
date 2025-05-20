package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.projet_java.model.Cours;
import org.example.projet_java.model.Enseignant;
import org.example.projet_java.model.Utilisateur;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class EdtEnseignantController extends EdtController {
    
    @FXML
    private Label labelEnseignant;
    
    @FXML
    private Label labelHeures;
    
    @FXML
    private ListView<String> listCours;
    
    private Enseignant enseignant;
    
    @Override
    public void initialize() {
        super.initialize();
        
        if (enseignant != null) {
            labelEnseignant.setText("Emploi du temps de " + enseignant.getNom() + " " + enseignant.getPrenom());
            updateHeuresEnseignement();
            updateListeCours();
        }
    }
    
    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        if (labelEnseignant != null) {
            labelEnseignant.setText("Emploi du temps de " + enseignant.getNom() + " " + enseignant.getPrenom());
            updateHeuresEnseignement();
            updateListeCours();
        }
    }
    
    @Override
    public void setUtilisateur(Utilisateur utilisateur) {
        if (utilisateur instanceof Enseignant) {
            setEnseignant((Enseignant) utilisateur);
        }
    }
    
    @Override
    protected void refreshCalendar() {
        super.refreshCalendar();
        
        // Refresh teacher-specific data
        updateHeuresEnseignement();
        updateListeCours();
    }
    
    @Override
    protected void displayDayView() {
        // Implement teacher-specific day view
        // Show only courses for this teacher on selected day
        List<Cours> coursJour = getCoursForDate(currentDate)
                .stream()
                .filter(cours -> cours.getEnseignant().equals(enseignant))
                .collect(Collectors.toList());
        
        // Display courses in calendar grid
        displayCours(coursJour);
    }
    
    @Override
    protected void displayWeekView() {
        // Implement teacher-specific week view
        // Show only courses for this teacher for the week
        LocalDate weekStart = getStartOfWeek(currentDate);
        List<Cours> coursWeek = getCoursForWeek(weekStart)
                .stream()
                .filter(cours -> cours.getEnseignant().equals(enseignant))
                .collect(Collectors.toList());
        
        // Display courses in calendar grid
        displayCours(coursWeek);
    }
    
    @Override
    protected void displayMonthView() {
        // Implement teacher-specific month view
        // Show only courses for this teacher for the month
        YearMonth month = YearMonth.from(currentDate);
        List<Cours> coursMonth = getCoursForMonth(month)
                .stream()
                .filter(cours -> cours.getEnseignant().equals(enseignant))
                .collect(Collectors.toList());
        
        // Display courses in calendar grid
        displayCours(coursMonth);
    }
    
    private void displayCours(List<Cours> cours) {
        // Display courses in the calendar grid
        // This would be implemented to show course blocks for this teacher
    }
    
    private void updateHeuresEnseignement() {
        // Calculate total teaching hours for the current month
        YearMonth currentMonth = YearMonth.from(currentDate);
        LocalDate start = currentMonth.atDay(1);
        LocalDate end = currentMonth.atEndOfMonth();
        
        int totalHeures = emploiDuTemps.getCoursForPeriod(start, end)
                .stream()
                .filter(cours -> cours.getEnseignant().equals(enseignant))
                .mapToInt(cours -> cours.getDuree()) // Assuming cours.getDuree() returns duration in hours
                .sum();
        
        labelHeures.setText("Total heures d'enseignement: " + totalHeures + "h");
    }
    
    private void updateListeCours() {
        // Update the list of courses for this teacher for the current month
        YearMonth currentMonth = YearMonth.from(currentDate);
        LocalDate start = currentMonth.atDay(1);
        LocalDate end = currentMonth.atEndOfMonth();
        
        List<Cours> coursEnseignant = emploiDuTemps.getCoursForPeriod(start, end)
                .stream()
                .filter(cours -> cours.getEnseignant().equals(enseignant))
                .collect(Collectors.toList());
        
        listCours.getItems().clear();
        for (Cours cours : coursEnseignant) {
            listCours.getItems().add(cours.getDate() + " - " + cours.getNom() + " (" + cours.getSalle().getNom() + ")");
        }
    }
    
    @FXML
    private void handlePrintEdt() {
        // Implement print functionality for this teacher's schedule
    }
    
    @FXML
    private void handleExportEdt() {
        // Implement export functionality for this teacher's schedule (e.g., to PDF or Excel)
    }
}