package org.example.projet_java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.example.projet_java.model.Cours;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EdtAdministrateurController {

    @FXML
    private GridPane agendaGrid;

    private final LocalTime startTime = LocalTime.of(8, 0);
    private final LocalTime endTime = LocalTime.of(18, 0);
    private final int intervalMinutes = 60;

    private LocalDate monday;

    @FXML
    public void initialize() {
        afficherSemaineCourante();
    }

    private void afficherSemaineCourante() {
    }

    private void afficherCoursDansGrille(List<Cours> coursList) {
    }
}
