module paagbat {
    requires javafx.controls;
    requires javafx.fxml;

    opens paagbat to javafx.fxml;
    exports paagbat;
}
