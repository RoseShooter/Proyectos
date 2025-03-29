package paagbat.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import paagbat.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}