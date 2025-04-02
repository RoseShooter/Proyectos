package paagbat.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.stream.Collectors;
import paagbat.App;
import paagbat.model.base.Herria;
import paagbat.model.base.KostakoHerria;

public class Aldatu {

    @FXML
    VBox vBoxHerriarenDatuak;

    @FXML
    VBox vBoxHondartzak;

    @FXML
    TextField txfIzena;

    @FXML
    TextField txfProbintzia;

    @FXML
    TextField herriBerri;

    @FXML
    TextField probintziaBerri;

    @FXML
    ChoiceBox<String> choiceBoxHerriak;

    @FXML
    ChoiceBox<String> choiceBoxProbintziak;

    @FXML
    CheckBox chkKostakoa;

    @FXML
    Label lbMezua;

    private TextField hondartza1 = new TextField();
    private TextField hondartza2 = new TextField();
    private TextField hondartza3 = new TextField();

    @FXML
    protected void initialize() {
        choiceBoxProbintziak.getItems().clear();
        HBox.setHgrow(choiceBoxProbintziak, Priority.ALWAYS);
        HBox.setHgrow(choiceBoxHerriak, Priority.ALWAYS);

        // stream().distinct().toList()) errepikapenak ezabatzeko
        choiceBoxProbintziak.getItems()
                .addAll(App.herriak.getProbintziaLista().stream().distinct().collect(Collectors.toList()));

        /**
         * choiceBoxProbintziak.getSelectionModel().selectedItemProperty() choiceBox-ean
         * momentuan aukeratuta dagoena
         * 
         * .addListener((obs, oldValue, newValue) -> { ... }):
         * obs → ikusten ari dena, kasu honetan ChoiceBox-a
         * oldvalue → aurretik aukeratuta zegoen balioa
         * newvalue → erabiltzaileak aukeratu berri duen balioa
         * new Herria(null, newValue) → Herria objektu bat sortzen du choiceBox-ean
         * dagoen probintziarekin
         * .setAll(...) → choiceBoxHerriak dagoen lista berri batekin aldatzen du
         * aukeratutako probintziaren arabera
         */
        choiceBoxProbintziak.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            choiceBoxHerriak.getItems().setAll(App.herriak.getProbintziaBatekoHerriak(new Herria(null, newValue)));
        });
    }

    /**
     * Herria txertatzeko botoia sakatzerakoan exekutatzen da metodo hau.
     * Lehengo eta behin izena eta probintzia eremuak beteta dauden egiaztatuko da
     * eta beteta
     * ez badaude, mezua aterako da lbMezua labelean.
     * Herri hori dagoeneko zerrendan baldin badago, mezu egokia idatziko da mezuen
     * labelean.
     * Baldintza biak beteta, eszenako datuekin herri edo kostako herri berri bat
     * gehituko da
     * herrien zerrendan. Eta eszena nagusira itzuliko gara.
     */
    @FXML
public void handleAldatu() throws IOException {
    String herriIzena = txfIzena.getText();
    String probintziaIzena = txfProbintzia.getText();

    if (herriIzena == null || herriIzena.trim().isEmpty()) {
        lbMezua.setText("Herriaren izena jarri behar da.");
        return;
    }
    
    if (probintziaIzena == null || probintziaIzena.trim().isEmpty()) {
        lbMezua.setText("Probintziaren izena jarri behar da.");
        return;
    }

    Herria h1 = new Herria(
        choiceBoxHerriak.getValue() != null ? choiceBoxHerriak.getValue() : "",
        choiceBoxProbintziak.getValue() != null ? choiceBoxProbintziak.getValue() : ""
    );
    Herria h2 = new Herria(herriIzena, probintziaIzena);

    if (App.herriak.herriaBadago(h2)) {
        lbMezua.setText("Herria dagoeneko datu basean dago");
        return;
    }

    if (chkKostakoa.isSelected()) {
        String[] hondartzak = {
            hondartza1.getText().trim(),
            hondartza2.getText().trim(),
            hondartza3.getText().trim()
        };
        h2 = new KostakoHerria(herriIzena, probintziaIzena, hondartzak); // Kostako herria bihurtzen da
    }

    App.herriak.herriaEguneratu(h1, h2);
    
    // Mensaje de éxito
    lbMezua.setText("Herria eguneratu da!");
    
    // Solo volver si todo ha ido bien
    handleAtzera();
}


    @FXML
    public void handleCheckKostakoa() {
        if (chkKostakoa.isSelected()) {
            hondartza1.setPromptText("Hondartza 1");
            hondartza2.setPromptText("Hondartza 2");
            hondartza3.setPromptText("Hondartza 3");
            vBoxHerriarenDatuak.getChildren().addAll(hondartza1, hondartza2, hondartza3);
        } else {
            // Hondartzen textfield-ak ezabatu
            vBoxHerriarenDatuak.getChildren().removeAll(hondartza1, hondartza2, hondartza3);
        }
    }

    @FXML
    void handleAtzera() throws IOException {
        App.setRoot("Nagusia");
    }

}
