module org.example.projet_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.incubator.vector;


    opens org.example.projet_java to javafx.fxml;
    exports org.example.projet_java;
}