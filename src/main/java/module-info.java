module com.example.member {
    requires javafx.controls;
    requires  javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires org.eclipse.collections.api;
    requires org.eclipse.collections.impl;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires jackson.datatype.eclipse.collections;


    opens com.example.member to javafx.fxml;
    exports com.example.member;
    exports controllers;
    opens controllers to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
}