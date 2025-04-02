package paagbat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import paagbat.App;
import paagbat.model.GalderakAccess;
import paagbat.model.base.KonpresioGaldera;
import paagbat.model.base.PartekatutakoDatuak;

public class KonpresioGalderak {

    @FXML
    private Label irakurketa;

    @FXML
    private Label galdera1;

    @FXML
    private Label galdera2;

    @FXML
    private Label galdera3;

    @FXML
    private ComboBox<String> erantzun1;

    @FXML
    private ComboBox<String> erantzun2;

    @FXML
    private ComboBox<String> erantzun3;

    @FXML
    private Label mailaTe;

    @FXML
    private Button bidali;

    private List<KonpresioGaldera> galderaZerrenda;
    private List<Boolean> emaitzak;
    private KonpresioGaldera galdera;

    @FXML
    public void initialize() {
        String kategoria = PartekatutakoDatuak.textuBotoia;
        String mailaText = PartekatutakoDatuak.mailaTextua;

        mailaTe.setText(mailaText); // Zein mailatan zauden inprimatzeko

        galderaZerrenda = galderakBilatu(kategoria); // Iragazitako galderen zerrenda hartzeko

        int galderaNum = (int) (Math.random() * galderaZerrenda.size());

        galdera = galderaZerrenda.get(galderaNum); // Galdera bat aukeratzeko
        setKonpresioGaldera(galdera); // Galdera eta erantzunak kargatzeko

        emaitzak = new ArrayList<>();

        bidali.setOnAction(event -> {
            erantzunaEgiaztatu(); // Erantzuna egiaztatu eta emaitza kalkulatzeko
        });
    }

    private List<KonpresioGaldera> galderakBilatu(String kategoria) { // Galderak kategoria honetan bilatzeko
        List<KonpresioGaldera> emaitza = new ArrayList<>();
        for (KonpresioGaldera galdera : GalderakAccess.konpresioGaldera) {
            if (galdera.getKategoriaEus().equals(kategoria) ||
                    galdera.getKategoriaIng().equals(kategoria)) {
                emaitza.add(galdera);
            }
        }

        return emaitza;
    }

    public void setKonpresioGaldera(KonpresioGaldera galdera) { // Galdera eta erantzunak kargatzeko
        irakurketa.setText(galdera.getEsaldi());

        String[][] galEtaEran = galdera.getGalderakEtaEraZuz();

        galdera1.setText(galEtaEran[0][0]);
        galdera2.setText(galEtaEran[0][1]);
        galdera3.setText(galEtaEran[0][2]);

        String[][] erantzunakSel = galdera.getErantzunak();

        List<String> erantzunak1 = new ArrayList<>();
        Collections.addAll(erantzunak1, erantzunakSel[0]);
        erantzun1.getItems().setAll(erantzunak1);

        List<String> erantzunak2 = new ArrayList<>();
        Collections.addAll(erantzunak2, erantzunakSel[1]);
        erantzun2.getItems().setAll(erantzunak2);

        List<String> erantzunak3 = new ArrayList<>();
        Collections.addAll(erantzunak3, erantzunakSel[2]);
        erantzun3.getItems().setAll(erantzunak3);
    }

    private void erantzunaEgiaztatu() {
        String[][] erantzunakZuz = galdera.getGalderakEtaEraZuz(); // Erantzun zuzenak aurkitu eta gordetzeko

        // Erabiltzaileak emandako erantzunak biltegiratzea
        String seleccion1 = erantzun1.getValue();
        String seleccion2 = erantzun2.getValue();
        String seleccion3 = erantzun3.getValue();

        // Egiaztatu hautatutako erantzunak zuzenak diren
        boolean correcta1 = seleccion1 != null && seleccion1.equals(erantzunakZuz[1][0]);
        boolean correcta2 = seleccion2 != null && seleccion2.equals(erantzunakZuz[1][1]);
        boolean correcta3 = seleccion3 != null && seleccion3.equals(erantzunakZuz[1][2]);

        // Bidali erantzunak emaitzen orrira
        if (correcta1) {
            emaitzak.add(true);
        } else {
            emaitzak.add(false);
        }

        if (correcta2) {
            emaitzak.add(true);
        } else {
            emaitzak.add(false);
        }

        if (correcta3) {
            emaitzak.add(true);
        } else {
            emaitzak.add(false);
        }

        bukaeraPantaila(); // Emaitza pantaila kargatzeko
    }

    private void bukaeraPantaila() { // Emaitza FXML eta kontroladorea kargatzeko
        System.out.println(mailaTe.getText());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/emaitzak.fxml"));
            Parent emaitza = loader.load();

            Emaitza controller = loader.getController();
            controller.setEmaitzak(emaitzak);
            controller.setMaila(mailaTe.getText());

            App.setRoot(emaitza);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
