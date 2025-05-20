package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import org.example.projet_java.model.Administrateur;
import org.example.projet_java.model.Anomalie;
import org.example.projet_java.model.Cours;
import org.example.projet_java.model.Salle;
import org.example.projet_java.model.Statistiques;
import org.example.projet_java.model.Utilisateur;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class EdtAdministrateurController extends EdtController {

    @FXML
    private TableView<Anomalie> tableConflits;

    @FXML
    private ListView<String> listSalles;

    @FXML
    private BarChart<String, Number> chartUtilisation;

    @FXML
    private PieChart chartRepartition;

    private Administrateur administrateur;
    private List<Anomalie> anomalies;
    private Statistiques statistiques;

    @Override
    public void initialize() {
        super.initialize();
        
        // Initialize statistics
        statistiques = new Statistiques();
        
        // Setup conflict detection
        detectConflits();
        
        // Setup statistics view
        updateStatistiques();
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
    }
    
    @Override
    public void setUtilisateur(Utilisateur utilisateur) {
        if (utilisateur instanceof Administrateur) {
            setAdministrateur((Administrateur) utilisateur);
        }
    }

    @Override
    protected void refreshCalendar() {
        super.refreshCalendar();
        
        // Refresh admin-specific data
        detectConflits();
        updateStatistiques();
    }

    @Override
    protected void displayDayView() {
        // Implement admin-specific day view
        // Show all courses for the selected day with edit/delete options
        List<Cours> coursJour = getCoursForDate(currentDate);
        // Display courses in calendar grid with conflict highlighting
        displayCoursWithConflicts(coursJour);
    }

    @Override
    protected void displayWeekView() {
        // Implement admin-specific week view
        // Show all courses for the week with edit/delete options
        LocalDate weekStart = getStartOfWeek(currentDate);
        List<Cours> coursWeek = getCoursForWeek(weekStart);
        // Display courses in calendar grid with conflict highlighting
        displayCoursWithConflicts(coursWeek);
    }

    @Override
    protected void displayMonthView() {
        // Implement admin-specific month view
        // Show all courses for the month with edit/delete options
        YearMonth month = YearMonth.from(currentDate);
        List<Cours> coursMonth = getCoursForMonth(month);
        // Display courses in calendar grid with conflict highlighting
        displayCoursWithConflicts(coursMonth);
    }

    private void displayCoursWithConflicts(List<Cours> cours) {
        // Display courses with visual indication of conflicts
        // This would create course blocks in the calendar with color-coding for conflicts
    }

    private void detectConflits() {
        // Detect and display conflicts in the tableConflits
        anomalies = emploiDuTemps.detecterAnomalies();
        tableConflits.getItems().clear();
        tableConflits.getItems().addAll(anomalies);
    }

    private void updateStatistiques() {
        // Update room utilization statistics
        chartUtilisation.getData().clear();
        chartRepartition.getData().clear();
        
        // Get rooms from the system
        List<Salle> salles = emploiDuTemps.getSalles();
        
        // Update salles list
        listSalles.getItems().clear();
        for (Salle salle : salles) {
            listSalles.getItems().add(salle.getNom());
        }
        
        // Setup statistics charts
        statistiques.updateStatistics(emploiDuTemps);
        
        // Update room utilization chart
        // Fill chartUtilisation with room usage data
        
        // Update distribution chart
        // Fill chartRepartition with distribution data
    }

    @FXML
    private void handleResolveConflict() {
        // Handle conflict resolution
        Anomalie selectedAnomalie = tableConflits.getSelectionModel().getSelectedItem();
        if (selectedAnomalie != null) {
            // Logic to resolve the conflict
            // Could open a dialog to reschedule one of the conflicting courses
            resolveAnomalie(selectedAnomalie);
        }
    }

    private void resolveAnomalie(Anomalie anomalie) {
        // Implement conflict resolution logic
        // This might involve changing room, time, or other aspects of a course
        // After resolution, update the conflict table and refresh calendar
        emploiDuTemps.resoudreAnomalie(anomalie);
        detectConflits();
        refreshCalendar();
    }
    
    @FXML
    private void handleAddCours() {
        // Add a new course to the schedule
        // Open a dialog to input course details
    }
    
    @FXML
    private void handleEditCours() {
        // Edit selected course
        // Open a dialog with course details for editing
    }
    
    @FXML
    private void handleDeleteCours() {
        // Delete selected course
        // Show confirmation dialog before deletion
    }
}