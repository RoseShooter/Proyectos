module paagbat {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens paagbat.controller to javafx.fxml;
    exports paagbat.controller;
    exports paagbat;
}
