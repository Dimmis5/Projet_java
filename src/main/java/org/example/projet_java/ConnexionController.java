package org.example.projet_java;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConnexionController {

    public void validerConnexion(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("edt.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Emploi du temps");
            stage.setScene(new Scene(root));
            stage.show();

            Stage current = (Stage) ((Node) event.getSource()).getScene().getWindow();
            current.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
