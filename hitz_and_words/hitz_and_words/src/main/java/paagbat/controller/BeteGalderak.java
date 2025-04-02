package paagbat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import paagbat.App;
import paagbat.model.GalderakAccess;
import paagbat.model.base.BeteGaldera;
import paagbat.model.base.PartekatutakoDatuak;
import paagbat.model.enumerazioa.Hizkuntza;

public class BeteGalderak {

    @FXML
    private ImageView irudia;

    @FXML
    private ComboBox<Hizkuntza> cbHizkun;

    @FXML
    private TextField erantzun;

    @FXML
    private Label galdera;

    @FXML
    private Label mailaTe;

    @FXML
    private Button bidali;

    private List<BeteGaldera> galderaZerrenda;
    private List<Boolean> emaitzak;
    private int galderaCount = 0;
    private Hizkuntza hizkuntza = Hizkuntza.EUSKERA;

    @FXML
    /**
     * eszena hasi eta momentuan exekutatu behar diren gauzak, kasu honetan combobox-aren irudiak kargatu
     * eta galdera bat jarri.
     */
    public void initialize() {
        String kategoria = PartekatutakoDatuak.textuBotoia;
        String mailaText = PartekatutakoDatuak.mailaTextua;

        mailaTe.setText(mailaText);

        cbHizkun.setItems(FXCollections.observableArrayList(Hizkuntza.EUSKERA, Hizkuntza.INGLES, Hizkuntza.ESPAÑOL));
        cbHizkun.setValue(hizkuntza);

        cbHizkun.setCellFactory(param -> new ListCell<Hizkuntza>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Hizkuntza item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(getClass().getResource(getImagePath(item)).toExternalForm()));
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);
                }
            }
        });

        cbHizkun.setOnAction(event -> hizkuntzaAldatu());

        galderaZerrenda = galderakBilatu(kategoria);
        Collections.shuffle(galderaZerrenda);
        if (galderaZerrenda.size() > 8) {
            galderaZerrenda = galderaZerrenda.subList(0, 8);
        }

        bidali.setOnAction(event -> erantzunaEgiaztatu());

        emaitzak = new ArrayList<>();
        galderaJarri();
    }

    /**
     * GalderaAccess-eko beteGalderen artean kategoriaren arabera galderak bilatu eta beste
     * array batean gordetzen dira.
     * @param kategoria parametroaren arabera galderak bilatzen ditu
     * @return emaitza ArrayList-a itzultzen du 
     */
    private List<BeteGaldera> galderakBilatu(String kategoria) {
        List<BeteGaldera> emaitza = new ArrayList<>();
        for (BeteGaldera pregunta : GalderakAccess.galderaBete) {
            if (pregunta.getKategoriaEus().equals(kategoria) || pregunta.getKategoriaIng().equals(kategoria) || pregunta.getKategoriaEs().equals(kategoria)) {
                emaitza.add(pregunta);
            }
        }
        return emaitza;
    }

    /**
     * GalderaZerrenda arrayListaren barruan dagoen galdera eta irudia jartzen ditu.
     */
    private void galderaJarri() {
        reset();

        if (galderaCount >= galderaZerrenda.size()) {
            return;
        }

        BeteGaldera momentukoGaldera = galderaZerrenda.get(galderaCount);
        galdera.setText(momentukoGaldera.getGaldera(hizkuntza));

        if (momentukoGaldera.getIrudia() != null) {
            Image galderaImg = new Image(getClass().getResource(momentukoGaldera.getIrudia()).toExternalForm());
            irudia.setImage(galderaImg);
        } else {
            irudia.setImage(null);
        }
    }

    /**
     * ComboBox-aren barruan dauden irudien direkzioa
     * @param hizkuntza enumerazioa hizkuntza jakiteko
     * @return itzultzen duena, kasu honetan string bat
     */
    private String getImagePath(Hizkuntza hizkuntza) {
        switch (hizkuntza) {
            case EUSKERA: return "/img/ikurriña.png";
            case INGLES: return "/img/ingles.png";
            case ESPAÑOL: return "/img/espana.png";
            default: return "/img/default.png";
        }
    }

    /**
     * ComboBox-aren barruan dagoenaren arabera galderen hizkuntza aldatzen da.
     */
    private void hizkuntzaAldatu() {
        hizkuntza = cbHizkun.getValue();
        galderaJarri();
    }

    /**
     * Galderen erantzunak zuzenak edo okerrak diren ikusteko
     */
    private void erantzunaEgiaztatu() {
        if (galderaCount >= galderaZerrenda.size()) {
            return;
        }

        BeteGaldera momBeteGaldera = galderaZerrenda.get(galderaCount);
        String erantzunaString = erantzun.getText().trim();

        boolean zuzena = momBeteGaldera.zuzenduErantzuna(erantzunaString);
        

        emaitzak.add(zuzena);

        // Botoiaren kolorea momentuan aldatu
        if (zuzena) {
            erantzun.setStyle("-fx-background-color: green;");
        } else {
            erantzun.setStyle("-fx-background-color: red;");
        }

        bidali.setDisable(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            galderaCount++;

            // Botoiak berriro martxan jarri
            bidali.setDisable(false);

            // Galdera guztiak erantzun badira emaitzak pantailara joan
            if (galderaCount >= galderaZerrenda.size()) {
                bukaeraPantaila(); // Emaitzak pantailara joan
            } else {
                galderaJarri(); // Hurrengo galdera erakutzi
            }
        });
        pause.play();
    }

    /**
     * erantzun TextField-aren kolorea esta textua ezabatzeko
     */
    private void reset() {
        erantzun.setStyle("");
        erantzun.setText("");
    }

    /**
     * emaitzak fxml-a eta honen controller-a kargatzeko
     */
    private void bukaeraPantaila() {
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
