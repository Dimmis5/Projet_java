module org.example.projet_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens org.example.projet_java to javafx.fxml;
    exports org.example.projet_java;
    exports org.example.projet_java.model;
    opens org.example.projet_java.model to javafx.fxml;
}