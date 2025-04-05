module paagbat {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; 

    opens paagbat.controller to javafx.fxml;
    exports paagbat.controller;
    exports paagbat;
}
