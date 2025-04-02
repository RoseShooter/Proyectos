package paagbat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import paagbat.App;
import paagbat.model.HerrienAtzipena;

public class Ezabatu {
    HerrienAtzipena atzipena = App.herriak;

    @FXML
    VBox vBoxHerriak;

    @FXML
    Label lbMezua;

    @FXML
    Button btnEzabatu;  // Ezabatzeko botoia

    List<CheckBox> checkBoxes = new ArrayList<>();  // CheckBox-ak gordetzeko
    List<String> aukeratutakoHerriak = new ArrayList<>();  // Aukeratutako herriak gordetzeko

    @FXML
    protected void initialize() {
        // Herri bakoitzeko checkBox bat sortzeko
        for (String herria : atzipena.getHerriaLista()) {
            CheckBox checkBox = new CheckBox(herria);
            vBoxHerriak.getChildren().add(checkBox);
            checkBoxes.add(checkBox);  // CheckBox-a gorde

            // Aukeratutako herrien zerrenda eguneratuta edukitzeko listener bat
            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    aukeratutakoHerriak.add(herria);
                } else {
                    aukeratutakoHerriak.remove(herria);
                }
            });
        }
    }

    // ezabatu botoia kontrolatzeko
    @FXML
    void handleEzabatu() throws IOException {
        if (aukeratutakoHerriak.isEmpty()) {
            lbMezua.setText("Ez dago herririk hautatuta.");
        } else if (lbMezua.getText().startsWith("Ziur zaude")) {
            // Mezua jada erakutzi bada herria bilatu eta ezabatu
            for (String herria : aukeratutakoHerriak) {
                atzipena.ezabatu(herria);
            }
            aukeratutakoHerriak.clear();  // Aukeratutako herrien zerrenda garbitu
            lbMezua.setText("Herriak ezabatu dira.");
            App.setRoot("Nagusia");  // Menu nagusira itzuli
        } else {
            // Mezua erakutzi
            lbMezua.setText("Ziur zaude " + aukeratutakoHerriak.size() + " herri ezabatu nahi dituzula?");
        }
    }

    @FXML
    void handleAtzera() throws IOException {
        App.setRoot("Nagusia");
    }
}
