module org.example.projet_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens org.example.projet_java to javafx.fxml;
    opens org.example.projet_java.controller to javafx.fxml;
    opens org.example.projet_java.model to javafx.base;
    
    exports org.example.projet_java;
    exports org.example.projet_java.controller;
    exports org.example.projet_java.model;
    exports org.example.projet_java.service;
}