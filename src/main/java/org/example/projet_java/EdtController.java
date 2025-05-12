package org.example.projet_java;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class EdtController implements Initializable {

    @FXML
    private GridPane edtGrid;

    private final String[] jours = {"", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};
    private final String[] heures = {"", "09h00", "10h00", "11h00", "12h00", "13h00", "14h00", "15h00", "16h00"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 1; i < jours.length; i++) {
            Label labelJour = new Label(jours[i]);
            edtGrid.add(labelJour, i, 0);
        }

        for (int j = 1; j < heures.length; j++) {
            Label labelHeure = new Label(heures[j]);
            edtGrid.add(labelHeure, 0, j);
        }

        for (int i = 1; i < jours.length; i++) {
            for (int j = 1; j < heures.length; j++) {
                Rectangle rect = new Rectangle(100, 80);
                rect.setFill(Color.LIGHTGRAY);
                rect.setStroke(Color.BLACK);
                edtGrid.add(rect, i, j);
            }
        }
    }
}
