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
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import paagbat.App;
import paagbat.model.GalderakAccess;
import paagbat.model.base.ErantzunAnitzekoGaldera;
import paagbat.model.base.PartekatutakoDatuak;
import paagbat.model.enumerazioa.Hizkuntza;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.util.Duration;

public class ErantzunAnitz {

    @FXML
    private ComboBox<Hizkuntza> cbHizkun;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button botoia;

    @FXML
    private Label mailaTe;

    @FXML
    private ImageView irudia;

    @FXML
    private Label galdera;


    private List<ErantzunAnitzekoGaldera> galderaZerrenda;
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

        emaitzak = new ArrayList<>();
        galderaJarri();
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
     * GalderaAccess-eko beteGalderen artean kategoriaren arabera galderak bilatu eta beste
     * array batean gordetzen dira.
     * @param kategoria parametroaren arabera galderak bilatzen ditu
     * @return emaitza ArrayList-a itzultzen du 
     */
    private List<ErantzunAnitzekoGaldera> galderakBilatu(String kategoria) {
        List<ErantzunAnitzekoGaldera> emaitza = new ArrayList<>();
        for (ErantzunAnitzekoGaldera pregunta : GalderakAccess.galderakErantzunAnitz) {
            if (pregunta.getKategoriaEus().equals(kategoria) || 
                pregunta.getKategoriaIng().equals(kategoria) || 
                pregunta.getKategoriaEs().equals(kategoria)) { // Añadimos soporte para español
                emaitza.add(pregunta);
            }
        }
        return emaitza;
    }

    /**
     * GalderaZerrenda arrayListaren barruan dagoen galdera eta irudia jartzen ditu.
     */
    private void galderaJarri() {
        resetKolorea(); // Botoien koloreak reseteatu galdera bakoitzean

        if (galderaCount >= galderaZerrenda.size()) {
            return;
        }

        ErantzunAnitzekoGaldera momentukoGaldera = galderaZerrenda.get(galderaCount);
        galdera.setText(momentukoGaldera.getGaldera(hizkuntza));

        String[] aukerak = momentukoGaldera.getAukerak(hizkuntza);
        btn1.setText(aukerak[0]);
        btn2.setText(aukerak[1]);
        btn3.setText(aukerak[2]);
        btn4.setText(aukerak[3]);

        if (momentukoGaldera.getIrudia() != null) {
            Image galderaImg = new Image(getClass().getResource(momentukoGaldera.getIrudia()).toExternalForm());
            irudia.setImage(galderaImg);
        } else {
            irudia.setImage(null);
        }

        // Botoiei akzioak ezarri
        btn1.setOnAction(event -> erantzunaEgiaztatu(btn1.getText(), momentukoGaldera));
        btn2.setOnAction(event -> erantzunaEgiaztatu(btn2.getText(), momentukoGaldera));
        btn3.setOnAction(event -> erantzunaEgiaztatu(btn3.getText(), momentukoGaldera));
        btn4.setOnAction(event -> erantzunaEgiaztatu(btn4.getText(), momentukoGaldera));
    }

    private boolean respuestaSeleccionada = false;

    /**
     * Erantzundako galderaren erantzuna zuzena edo okerra den ikusteko
     * @param erantzuna erabiltzaileak autatutako aukera
     * @param galderaAktuala momentuan erantzun den galdera
     */
    private void erantzunaEgiaztatu(String erantzuna, ErantzunAnitzekoGaldera galderaAktuala) {
        if (respuestaSeleccionada) return;
        respuestaSeleccionada = true;
    
        boolean zuzena = galderaAktuala.zuzenduErantzuna(erantzuna);
        emaitzak.add(zuzena);
    
        // Aukeratutako botoia lortu
        Button aukeratutakoBotoia = getAukeratutakoBotoia(erantzuna);
    
        // Aukeratutako botoiaren kolorea aldatu.
        if (zuzena) {
            aukeratutakoBotoia.setStyle("-fx-background-color: green;");
        } else {
            aukeratutakoBotoia.setStyle("-fx-background-color: red;");
        }
    
        // Botoi guztiak ezgaitu
        btn1.setDisable(true);
        btn2.setDisable(true);
        btn3.setDisable(true);
        btn4.setDisable(true);
        aukeratutakoBotoia.setDisable(false);
    
        // Botoien opazitatea mantendu
        if (btn1 != aukeratutakoBotoia) btn1.setStyle("-fx-opacity: 1.0;");
        if (btn2 != aukeratutakoBotoia) btn2.setStyle("-fx-opacity: 1.0;");
        if (btn3 != aukeratutakoBotoia) btn3.setStyle("-fx-opacity: 1.0;");
        if (btn4 != aukeratutakoBotoia) btn4.setStyle("-fx-opacity: 1.0;");
    
        // Hurrengo galderara joan baino lehen segundu 1 itzaron
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            galderaCount++;
    
            // Botoi guztiak berriro gaitu
            btn1.setDisable(false);
            btn2.setDisable(false);
            btn3.setDisable(false);
            btn4.setDisable(false);
    
            // Botoien estiloa berrezarri
            btn1.setStyle("");
            btn2.setStyle("");
            btn3.setStyle("");
            btn4.setStyle("");
    
            // Klik-ak berriro baimendu
            respuestaSeleccionada = false;
    
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
     * aukeratutako botoiaren textua lortzeko
     * @param erantzuna aukeratutako botoiaren textua 
     * @return itzultzen dena
     */
    private Button getAukeratutakoBotoia(String erantzuna) {
        if (erantzuna.equals(btn1.getText())) {
            return btn1;
        } else if (erantzuna.equals(btn2.getText())) {
            return btn2;
        } else if (erantzuna.equals(btn3.getText())) {
            return btn3;
        } else {
            return btn4;
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
     * botoien TextField-aren kolorea esta textua ezabatzeko
     */
    private void resetKolorea() {
        btn1.setStyle("");
        btn2.setStyle("");
        btn3.setStyle("");
        btn4.setStyle("");
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
