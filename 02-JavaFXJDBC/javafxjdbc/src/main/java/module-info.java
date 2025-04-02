module paagbat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens paagbat to javafx.fxml;
    opens paagbat.controller to javafx.fxml;
    exports paagbat;
}
