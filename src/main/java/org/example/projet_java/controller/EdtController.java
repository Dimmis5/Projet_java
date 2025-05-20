package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.projet_java.model.Cours;
import org.example.projet_java.model.EmploiDuTemps;
import org.example.projet_java.model.Utilisateur;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EdtController {

    @FXML
    protected Button btnJour;
    
    @FXML
    protected Button btnSemaine;
    
    @FXML
    protected Button btnMois;
    
    @FXML
    protected ComboBox<String> comboMois;
    
    @FXML
    protected GridPane calendarGrid;
    
    @FXML
    protected VBox calendarContainer;
    
    protected EmploiDuTemps emploiDuTemps;
    protected LocalDate currentDate;
    protected Utilisateur utilisateur;
    
    public enum ViewMode {
        DAY,
        WEEK,
        MONTH
    }
    
    protected ViewMode currentViewMode = ViewMode.WEEK;
    
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public void initialize() {
        currentDate = LocalDate.now();
        
        btnJour.setOnAction(event -> switchViewMode(ViewMode.DAY));
        btnSemaine.setOnAction(event -> switchViewMode(ViewMode.WEEK));
        btnMois.setOnAction(event -> switchViewMode(ViewMode.MONTH));
        
        // Initialize month selector
        for (int i = 1; i <= 12; i++) {
            comboMois.getItems().add(YearMonth.of(LocalDate.now().getYear(), i).format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        }
        comboMois.setValue(YearMonth.from(currentDate).format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        comboMois.setOnAction(event -> {
            String selected = comboMois.getValue();
            YearMonth yearMonth = YearMonth.parse(selected, DateTimeFormatter.ofPattern("MMMM yyyy"));
            currentDate = yearMonth.atDay(1);
            refreshCalendar();
        });
        
        refreshCalendar();
    }
    
    protected void switchViewMode(ViewMode mode) {
        currentViewMode = mode;
        
        // Update UI for selected mode
        btnJour.getStyleClass().remove("view-toggle-selected");
        btnSemaine.getStyleClass().remove("view-toggle-selected");
        btnMois.getStyleClass().remove("view-toggle-selected");
        
        switch (mode) {
            case DAY:
                btnJour.getStyleClass().add("view-toggle-selected");
                break;
            case WEEK:
                btnSemaine.getStyleClass().add("view-toggle-selected");
                break;
            case MONTH:
                btnMois.getStyleClass().add("view-toggle-selected");
                break;
        }
        
        refreshCalendar();
    }
    
    protected void refreshCalendar() {
        // Clear previous content
        calendarGrid.getChildren().clear();
        
        switch (currentViewMode) {
            case DAY:
                displayDayView();
                break;
            case WEEK:
                displayWeekView();
                break;
            case MONTH:
                displayMonthView();
                break;
        }
    }
    
    protected void displayDayView() {
        // Implement day view display logic
        // This will be overridden by specific implementations
    }
    
    protected void displayWeekView() {
        // Implement week view display logic
        // This will be overridden by specific implementations
    }
    
    protected void displayMonthView() {
        // Implement month view display logic
        // This will be overridden by specific implementations
    }
    
    protected List<Cours> getCoursForDate(LocalDate date) {
        // Return courses for a specific date
        return emploiDuTemps.getCoursForDate(date);
    }
    
    protected List<Cours> getCoursForWeek(LocalDate weekStart) {
        // Return courses for a week starting on weekStart
        LocalDate weekEnd = weekStart.plusDays(6);
        return emploiDuTemps.getCoursForPeriod(weekStart, weekEnd);
    }
    
    protected List<Cours> getCoursForMonth(YearMonth month) {
        // Return courses for a specific month
        LocalDate monthStart = month.atDay(1);
        LocalDate monthEnd = month.atEndOfMonth();
        return emploiDuTemps.getCoursForPeriod(monthStart, monthEnd);
    }
    
    // Helper method to get the start of the current week (Monday)
    protected LocalDate getStartOfWeek(LocalDate date) {
        return date.minusDays(date.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());
    }
}