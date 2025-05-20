module org.example.projet_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports org.example.projet_java.controller;
    opens org.example.projet_java.controller to javafx.fxml;
    exports org.example.projet_java;
}