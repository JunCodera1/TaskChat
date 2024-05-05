module com.example.member {
    requires javafx.controls;
    requires  javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.member to javafx.fxml;
    exports com.example.member;
    exports controllers;
    opens controllers to javafx.fxml;
}