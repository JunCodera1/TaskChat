module com.example.member {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.member to javafx.fxml;
    exports com.example.member;
    exports controllers;
    opens controllers to javafx.fxml;
}