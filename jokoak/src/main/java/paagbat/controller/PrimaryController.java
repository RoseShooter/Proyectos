package paagbat.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import paagbat.App;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
