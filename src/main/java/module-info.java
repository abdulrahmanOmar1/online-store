module com.example.comp333_finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.comp333_finalproject to javafx.fxml;
    exports com.example.comp333_finalproject;
    exports com.example.comp333_finalproject.Classes;
    opens com.example.comp333_finalproject.Classes to javafx.fxml;
    exports com.example.comp333_finalproject.Controllers;
    opens com.example.comp333_finalproject.Controllers to javafx.fxml;
    exports com.example.comp333_finalproject.TableClasses;
    opens com.example.comp333_finalproject.TableClasses to javafx.fxml;
}