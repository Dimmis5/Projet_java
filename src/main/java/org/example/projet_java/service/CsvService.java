package org.example.projet_java.service;

import org.example.projet_java.model.Cours;
import org.example.projet_java.model.EmploiDuTemps;
import org.example.projet_java.model.Enseignant;
import org.example.projet_java.model.Etudiant;
import org.example.projet_java.model.Horaire;
import org.example.projet_java.model.Notification;
import org.example.projet_java.model.Salle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvService {
    
    private static final String FICHIER_COURS = "CSV_Java/Cours.csv";
    private static final String FICHIER_NOTIFICATIONS = "CSV_Java/Notification.csv";
    private static final String FICHIER_EDT = "CSV_Java/edt.csv";
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    
    // Méthodes de chargement des données
    
    public EmploiDuTemps chargerEmploiDuTemps() {
        EmploiDuTemps edt = new EmploiDuTemps();
        
        // Charger les cours depuis le fichier CSV
        List<Cours> cours = chargerCours();
        for (Cours c : cours) {
            edt.ajouterCours(c);
        }
        
        return edt;
    }
    
    public List<Cours> chargerCours() {
        List<Cours> cours = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_COURS))) {
            String line;
            // Skip header line
            reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) {
                    Cours c = new Cours();
                    c.setId(data[0]); // Utiliser l'ID comme chaîne de caractères
                    c.setNom(data[1]);
                    
                    // Créer l'enseignant
                    Enseignant enseignant = new Enseignant();
                    enseignant.setId(data[2]); // Utiliser l'ID comme chaîne de caractères
                    c.setEnseignant(enseignant);
                    
                    // Créer la salle
                    Salle salle = new Salle();
                    salle.setId(data[3]); // Utiliser l'ID comme chaîne de caractères
                    salle.setNom(data[4]);
                    c.setSalle(salle);
                    
                    // Créer l'horaire
                    LocalDate date = LocalDate.parse(data[5], DATE_FORMATTER);
                    String[] heures = data[6].split("-");
                    if (heures.length == 2) {
                        LocalTime heureDebut = LocalTime.parse(heures[0], TIME_FORMATTER);
                        LocalTime heureFin = LocalTime.parse(heures[1], TIME_FORMATTER);
                        
                        Horaire horaire = new Horaire();
                        horaire.setDate(date);
                        horaire.setHeureDebut(heureDebut);
                        horaire.setHeureFin(heureFin);
                        
                        c.setHoraire(horaire);
                    }
                    
                    // Ajouter des étudiants (cela devrait être fait ailleurs en pratique)
                    List<Etudiant> etudiants = new ArrayList<>();
                    if (data.length > 7) {
                        String[] etudiantIds = data[7].split(";");
                        for (String etudiantId : etudiantIds) {
                            Etudiant etudiant = new Etudiant();
                            etudiant.setId(etudiantId); // Utiliser l'ID comme chaîne de caractères
                            etudiants.add(etudiant);
                        }
                    }
                    c.setEtudiants(etudiants);
                    
                    cours.add(c);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des cours: " + e.getMessage());
        }
        
        return cours;
    }
    
    public List<Notification> chargerNotifications() {
        List<Notification> notifications = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_NOTIFICATIONS))) {
            String line;
            // Skip header line
            reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    Notification notification = new Notification();
                    notification.setId(data[0]); // Utiliser l'ID comme chaîne de caractères
                    notification.setDestinataireId(data[1]); // Utiliser l'ID comme chaîne de caractères
                    notification.setMessage(data[2]);
                    notification.setDate(LocalDate.parse(data[3], DATE_FORMATTER));
                    notification.setLue(Boolean.parseBoolean(data[4]));
                    
                    notifications.add(notification);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des notifications: " + e.getMessage());
        }
        
        return notifications;
    }
    
    // Méthodes de sauvegarde des données
    
    public void sauvegarderCours(List<Cours> cours) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_COURS))) {
            // Write header
            writer.write("ID,Nom,EnseignantID,SalleID,SalleNom,Date,Horaire,EtudiantIDs");
            writer.newLine();
            
            for (Cours c : cours) {
                StringBuilder sb = new StringBuilder();
                sb.append(c.getId()).append(",");
                sb.append(c.getNom()).append(",");
                sb.append(c.getEnseignant().getId()).append(",");
                sb.append(c.getSalle().getId()).append(",");
                sb.append(c.getSalle().getNom()).append(",");
                sb.append(c.getHoraire().getDate().format(DATE_FORMATTER)).append(",");
                sb.append(c.getHoraire().getHeureDebut().format(TIME_FORMATTER)).append("-");
                sb.append(c.getHoraire().getHeureFin().format(TIME_FORMATTER)).append(",");
                
                // Add student IDs
                if (!c.getEtudiants().isEmpty()) {
                    for (int i = 0; i < c.getEtudiants().size(); i++) {
                        if (i > 0) {
                            sb.append(";");
                        }
                        sb.append(c.getEtudiants().get(i).getId());
                    }
                }
                
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des cours: " + e.getMessage());
        }
    }
    
    public void sauvegarderNotifications(List<Notification> notifications) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_NOTIFICATIONS))) {
            // Write header
            writer.write("ID,DestinataireID,Message,Date,Lue");
            writer.newLine();
            
            for (Notification n : notifications) {
                StringBuilder sb = new StringBuilder();
                sb.append(n.getId()).append(",");
                sb.append(n.getDestinataireId()).append(",");
                sb.append(n.getMessage()).append(",");
                sb.append(n.getDate().format(DATE_FORMATTER)).append(",");
                sb.append(n.isLue());
                
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des notifications: " + e.getMessage());
        }
    }
    
    public void sauvegarderEmploiDuTemps(EmploiDuTemps edt) {
        // In this simplified model, we just save the courses
        sauvegarderCours(edt.getCours());
    }
}