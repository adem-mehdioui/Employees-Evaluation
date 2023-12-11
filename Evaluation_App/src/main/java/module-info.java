module com.example.myownapp {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.java;
    requires com.jfoenix;
    requires javafx.base;
    requires org.controlsfx.controls;

    opens com.example.myownapp to javafx.fxml;
    exports com.example.myownapp;
}


