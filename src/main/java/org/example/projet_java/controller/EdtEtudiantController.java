package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.projet_java.model.Cours;
import org.example.projet_java.model.Etudiant;
import org.example.projet_java.model.Notification;
import org.example.projet_java.model.Utilisateur;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class EdtEtudiantController extends EdtController {
    
    @FXML
    private Label labelEtudiant;
    
    @FXML
    private ListView<Notification> listNotifications;
    
    @FXML
    private ListView<String> listCoursProchainsJours;
    
    private Etudiant etudiant;
    
    @Override
    public void initialize() {
        super.initialize();
        
        if (etudiant != null) {
            labelEtudiant.setText("Emploi du temps de " + etudiant.getNom() + " " + etudiant.getPrenom());
            updateNotifications();
            updateCoursProchains();
        }
    }
    
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        if (labelEtudiant != null) {
            labelEtudiant.setText("Emploi du temps de " + etudiant.getNom() + " " + etudiant.getPrenom());
            updateNotifications();
            updateCoursProchains();
        }
    }
    
    @Override
    public void setUtilisateur(Utilisateur utilisateur) {
        if (utilisateur instanceof Etudiant) {
            setEtudiant((Etudiant) utilisateur);
        }
    }
    
    @Override
    protected void refreshCalendar() {
        super.refreshCalendar();
        
        // Refresh student-specific data
        updateNotifications();
        updateCoursProchains();
    }
    
    @Override
    protected void displayDayView() {
        // Implement student-specific day view
        // Show only courses for this student on selected day
        List<Cours> coursJour = getCoursForDate(currentDate)
                .stream()
                .filter(cours -> cours.getEtudiantsInscrits().contains(etudiant))
                .collect(Collectors.toList());
        
        // Display courses in calendar grid
        displayCours(coursJour);
    }
    
    @Override
    protected void displayWeekView() {
        // Implement student-specific week view
        // Show only courses for this student for the week
        LocalDate weekStart = getStartOfWeek(currentDate);
        List<Cours> coursWeek = getCoursForWeek(weekStart)
                .stream()
                .filter(cours -> cours.getEtudiantsInscrits().contains(etudiant))
                .collect(Collectors.toList());
        
        // Display courses in calendar grid
        displayCours(coursWeek);
    }
    
    @Override
    protected void displayMonthView() {
        // Implement student-specific month view
        // Show only courses for this student for the month
        YearMonth month = YearMonth.from(currentDate);
        List<Cours> coursMonth = getCoursForMonth(month)
                .stream()
                .filter(cours -> cours.getEtudiantsInscrits().contains(etudiant))
                .collect(Collectors.toList());
        
        // Display courses in calendar grid
        displayCours(coursMonth);
    }
    
    private void displayCours(List<Cours> cours) {
        // Display courses in the calendar grid
        // This would be implemented to show course blocks for this student
    }
    
    private void updateNotifications() {
        // Update notification list for this student
        List<Notification> notifications = etudiant.getNotifications();
        
        listNotifications.getItems().clear();
        listNotifications.getItems().addAll(notifications);
        
        // Custom cell factory to display notifications nicely
        listNotifications.setCellFactory(lv -> new javafx.scene.control.ListCell<Notification>() {
            @Override
            protected void updateItem(Notification item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText((item.getDate() != null ? item.getDate() : item.getDateCreation().toLocalDate()) + " - " + item.getMessage());
                    // Mark as read when displayed
                    if (!item.isLue()) {
                        setStyle("-fx-font-weight: bold");
                        item.setLue(true);
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }
    
    private void updateCoursProchains() {
        // Update list of upcoming courses for this student
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        
        List<Cours> coursProchains = emploiDuTemps.getCoursForPeriod(today, nextWeek)
                .stream()
                .filter(cours -> cours.getEtudiantsInscrits().contains(etudiant))
                .sorted((c1, c2) -> c1.getDate().compareTo(c2.getDate()))
                .collect(Collectors.toList());
        
        listCoursProchainsJours.getItems().clear();
        for (Cours cours : coursProchains) {
            listCoursProchainsJours.getItems().add(
                cours.getDate() + " - " + cours.getHoraire().getHeureDebut() + 
                " - " + cours.getNom() + " (" + cours.getSalle().getNom() + ")"
            );
        }
    }
    
    @FXML
    private void handleMarkAllRead() {
        // Mark all notifications as read
        for (Notification notification : etudiant.getNotifications()) {
            notification.setLue(true);
        }
        updateNotifications();
    }
    
    @FXML
    private void handleDeleteNotification() {
        // Delete selected notification
        Notification selectedNotification = listNotifications.getSelectionModel().getSelectedItem();
        if (selectedNotification != null) {
            etudiant.getNotifications().remove(selectedNotification);
            updateNotifications();
        }
    }
    
    @FXML
    private void handlePrintEdt() {
        // Implement print functionality for this student's schedule
    }
}