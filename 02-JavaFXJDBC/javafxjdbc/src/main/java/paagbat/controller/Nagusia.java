package paagbat.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import paagbat.App;
import paagbat.model.base.Herria;
import paagbat.model.base.KostakoHerria;

public class Nagusia {
        
    @FXML
    TextArea tAreaHerriak;

    @FXML
    protected void initialize() {
        tAreaHerriak.clear();
        textAreaBete();                    
    }

    

    @FXML
    void handleTableView() throws IOException {
        App.setRoot("TableView");
    }

    @FXML
    void handleTxertatu() throws IOException {
        App.setRoot("Txertatu");
    }

    @FXML
    void handleEzabatu() throws IOException {
        App.setRoot("Ezabatu");
    }

    @FXML
    void handleAldatu() throws IOException {
        App.setRoot("Aldatu");
    }

    @FXML
    void handleIrten() throws IOException {
       Platform.exit();
    }

    /** Textareari herrien zerrendako balio oso numeratuak ezartzen dizkio. Ikus irudia.  */
    private void textAreaBete(){
        int count = 1;
        if (App.herriak.konektatu() != null) {
            for(Herria herri : App.herriak.getHerriak()){
                if (herri instanceof KostakoHerria) {
                    tAreaHerriak.appendText(count + ".- " + (((KostakoHerria)herri).toString()) + "\n");

                    String[] hondartzak = ((KostakoHerria) herri).getHondartzak();
                    for(String hondartza : hondartzak){
                        if (hondartza != null && !hondartza.isEmpty()) {
                            tAreaHerriak.appendText("\t- " + hondartza + "\n");
                        }
                    }
                    
                } else {
                    tAreaHerriak.appendText(count + ".- " + herri + "\n");
                    count++;
                }
            }
        } else {
            tAreaHerriak.appendText("Ezin izan da datu basera konektatu.");
        }
    }

}
