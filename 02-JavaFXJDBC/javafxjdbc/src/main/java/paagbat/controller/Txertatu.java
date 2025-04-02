package paagbat.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import paagbat.App;
import paagbat.model.base.Herria;
import paagbat.model.base.KostakoHerria;

public class Txertatu {

    private TextField hondartza1 = new TextField();
    private TextField hondartza2 = new TextField();
    private TextField hondartza3 = new TextField();

    @FXML
    VBox vBoxHerriarenDatuak;

    @FXML
    TextField txfIzena;

    @FXML
    TextField txfProbintzia;

    @FXML
    CheckBox chkKostakoa;

    @FXML
    Label lbMezua;

    @FXML
    protected void initialize() {

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
     * 
     * @throws InterruptedException
     */
    @FXML
public void handleTxertatu() throws IOException, InterruptedException {
    String izena = txfIzena.getText().trim();
    String probintzia = txfProbintzia.getText().trim();

    if (izena.isEmpty() && probintzia.isEmpty()) {
        lbMezua.setText("Herriaren izena eta probintzia jarri behar dira.");
        return;
    } else if (izena.isEmpty()) {
        lbMezua.setText("Herriaren izena jarri behar da.");
        return;
    } else if (probintzia.isEmpty()) {
        lbMezua.setText("Probintziaren izena jarri behar da.");
        return;
    }

    Herria herri;

    if (chkKostakoa.isSelected()) {
        String[] hondartzak = {
            hondartza1.getText().trim(),
            hondartza2.getText().trim(),
            hondartza3.getText().trim()
        };
        herri = new KostakoHerria(izena, probintzia, hondartzak);
    } else {
        herri = new Herria(izena, probintzia);
    }

    if (App.herriak.herriaBadago(herri)) {
        lbMezua.setText("Herria dagoeneko datu basean dago.");
        return;
    }

    App.herriak.txertatu(herri);
    lbMezua.setText("Herria ondo txertatu da.");
    Thread.sleep(1000);
    handleAtzera();
}


    /*
     * *
     * Kostakoa CheckBoxa markatzerakoan, hiru TextField gehituko dira
     * herriaren hondartzak gehitu ahal izateko. (Ezingo ditugu hiru hondartza baino
     * gehiago gehitu)
     * Kostakoa CheckBox-a ezmarkatzerakoan, hiru TextField-ak ezabatuko dira.
     */
    @FXML
    void handleKostakoa() {
        if (chkKostakoa.isSelected()) {
            // Hondartzen textfield-ak erakutzi
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
