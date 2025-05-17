module paagbat {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires javafx.base;
    requires org.kordamp.ikonli.fontawesome; 
    requires javafx.web;
    requires java.desktop;


    opens paagbat.controller to javafx.fxml;
    exports paagbat.controller;
    exports paagbat;
}
