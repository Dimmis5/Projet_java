package org.example.projet_java;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AccueilController {
    @FXML
    private GridPane edtGrid;
    private final String[] jours = {"", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};
    private final String[] heures = {"", "09h00", "10h00", "11h00", "12h00", "13h00", "14h00", "15h00", "16h00"};

    public void edt() {
        edtGrid.getChildren().clear();
        edtGrid.getColumnConstraints().clear();
        edtGrid.getRowConstraints().clear();

        edtGrid.setHgap(10);
        edtGrid.setVgap(10);

        for (int i = 1; i < jours.length; i++) {
            edtGrid.add(new Label(jours[i]), i, 0);
        }

        for (int j = 1; j < heures.length; j++) {
            edtGrid.add(new Label(heures[j]), 0, j);
        }

        for (int i = 1; i < jours.length; i++) {
            for (int j = 1; j < heures.length; j++) {
                Rectangle rect = new Rectangle(60, 40);
                rect.setFill(Color.LIGHTGRAY);
                rect.setStroke(Color.BLACK);
                edtGrid.add(rect, i, j);
            }
        }
    }

}
