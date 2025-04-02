package paagbat.controller;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import paagbat.App;
import paagbat.model.base.PartekatutakoDatuak;
import paagbat.model.enumerazioa.Hizkuntza;

public class CategoryMenu {

    @FXML
    private Label maila;

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
    private Button btnAtzera;

    private Hizkuntza hizkuntza = Hizkuntza.EUSKERA;
    private String mailaTextua;

    public void initialize() {
        this.mailaTextua = PartekatutakoDatuak.mailaTextua;

        if (mailaTextua != null && !mailaTextua.isEmpty()) {
            maila.setText(mailaTextua);
            setTextua(mailaTextua);
        } else {
            System.out.println("Errorea: maila ez dago gordeta PartekatutakoDatuak-en.");
        }

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
        

        btn1.setOnAction(this::botoiakKontrolatu);
        btn2.setOnAction(this::botoiakKontrolatu);
        btn3.setOnAction(this::botoiakKontrolatu);
        btn4.setOnAction(this::botoiakKontrolatu);
        btnAtzera.setOnAction(event -> atzeraItzuli());
    }

    /**
     * ComboBox-aren barruan dagoenaren arabera galderen hizkuntza aldatzen da.
     */
    private void hizkuntzaAldatu() {
        hizkuntza = cbHizkun.getValue();
        botoiTextua();
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
     * Botoietan agertuko den textua jartzeko
     * @param textua textua jartzeko
     */
    public void setTextua(String textua) {
        if (textua != null && !textua.isEmpty()) {
            this.mailaTextua = textua;
            maila.setText(textua);
            
            if (textua.contains("Level")) {
                hizkuntza = Hizkuntza.INGLES;
            } else if (textua.contains("Nivel")) {
                hizkuntza = Hizkuntza.ESPAÑOL;
            } else {
                hizkuntza = Hizkuntza.EUSKERA;
            }
            
            botoiTextua();
        } else {
            System.out.println("Errorea: Maila ez da jaso.");
        }
    }
    

    /**
     * Hizkuntzaren arabera kategorien hizkuntza aldatzeko
     */
    private void botoiTextua() {
        String nibela = maila.getText().trim();
        switch (nibela) {
            case "A1 Maila":
            case "A1 Level":
            case "Nivel A1":
                btn1.setText(hizkuntza == Hizkuntza.EUSKERA ? "Animaliak"
                        : (hizkuntza == Hizkuntza.INGLES ? "Animals" : "Animales"));
                btn2.setText(hizkuntza == Hizkuntza.EUSKERA ? "Koloreak"
                        : (hizkuntza == Hizkuntza.INGLES ? "Colours" : "Colores"));
                btn3.setText(hizkuntza == Hizkuntza.EUSKERA ? "Zenbakiak"
                        : (hizkuntza == Hizkuntza.INGLES ? "Numbers" : "Números"));
                btn4.setText(hizkuntza == Hizkuntza.EUSKERA ? "Gauzak"
                        : (hizkuntza == Hizkuntza.INGLES ? "Things" : "Cosas"));
                break;
            case "A2 Maila":
            case "A2 Level":
            case "Nivel A2":
                btn1.setText(hizkuntza == Hizkuntza.EUSKERA ? "Animaliak "
                        : (hizkuntza == Hizkuntza.INGLES ? "Animals " : "Animales "));
                btn2.setText(hizkuntza == Hizkuntza.EUSKERA ? "Koloreak "
                        : (hizkuntza == Hizkuntza.INGLES ? "Colours " : "Colores "));
                btn3.setText(hizkuntza == Hizkuntza.EUSKERA ? "Zenbakiak "
                        : (hizkuntza == Hizkuntza.INGLES ? "Numbers " : "Números "));
                btn4.setText(hizkuntza == Hizkuntza.EUSKERA ? "Gauzak "
                        : (hizkuntza == Hizkuntza.INGLES ? "Things " : "Cosas "));
                break;
            case "B1 Maila":
            case "B1 Level":
            case "Nivel B1":
                btn1.setText(hizkuntza == Hizkuntza.EUSKERA ? "Animaliak "
                        : (hizkuntza == Hizkuntza.INGLES ? "Animals " : "Animales "));
                btn2.setText(hizkuntza == Hizkuntza.EUSKERA ? "Koloreak "
                        : (hizkuntza == Hizkuntza.INGLES ? "Colours " : "Colores "));
                btn3.setText(hizkuntza == Hizkuntza.EUSKERA ? "Zenbakiak "
                        : (hizkuntza == Hizkuntza.INGLES ? "Numbers " : "Números "));
                btn4.setText(hizkuntza == Hizkuntza.EUSKERA ? "Gauzak "
                        : (hizkuntza == Hizkuntza.INGLES ? "Things " : "Cosas "));
                break;
            case "B2 Maila":
            case "B2 Level":
            case "Nivel B2":
                btn1.setText(hizkuntza == Hizkuntza.EUSKERA ? "Animaliak"
                        : (hizkuntza == Hizkuntza.INGLES ? "Animals" : "Animales"));
                btn2.setText(hizkuntza == Hizkuntza.EUSKERA ? "Koloreak"
                        : (hizkuntza == Hizkuntza.INGLES ? "Colours" : "Colores"));
                btn3.setText(hizkuntza == Hizkuntza.EUSKERA ? "Zenbakiak"
                        : (hizkuntza == Hizkuntza.INGLES ? "Numbers" : "Números"));
                btn4.setText(hizkuntza == Hizkuntza.EUSKERA ? "Gauzak"
                        : (hizkuntza == Hizkuntza.INGLES ? "Things" : "Cosas"));
                break;
            default:
                // Si no hay coincidencia con los niveles conocidos
                System.out.println("Maila ezezaguna: " + nibela);
        }
    }

    /**
     * Aurretik aukeratutako mailaren arabera fxml bat edo bestea kargatzeko
     * @param event gertzatzen dena, kasu honetan botoi bat aukeratu
     */
    public void botoiakKontrolatu(ActionEvent event) {

        Button klik = (Button) event.getSource();
        PartekatutakoDatuak.mailaTextua = maila.getText();

        PartekatutakoDatuak.textuBotoia = klik.getText();

        String mailaTestua = maila.getText().trim();

        String fxmlPath = null;
        if (mailaTestua.equalsIgnoreCase("A1 Maila") || mailaTestua.equalsIgnoreCase("A1 Level") || mailaTestua.equalsIgnoreCase("Nivel A1")) {
            fxmlPath = "/fxml/erantzunAnitz.fxml";
        } else if (mailaTestua.equalsIgnoreCase("A2 Maila") || mailaTestua.equalsIgnoreCase("A2 Level") || mailaTestua.equalsIgnoreCase("Nivel A2")) {
            fxmlPath = "/fxml/beteGaldera.fxml";
        } else if (mailaTestua.equalsIgnoreCase("B1 Maila") || mailaTestua.equalsIgnoreCase("B1 Level") || mailaTestua.equalsIgnoreCase("Nivel B1")) {
            fxmlPath = "/fxml/konpresioGalderak.fxml";
        }

        if (fxmlPath != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();
                App.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
                // Mostrar alerta para el usuario
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errorea");
                alert.setHeaderText("Ezin da kargatu pantaila");
                alert.setContentText("Errore bat gertatu da: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            System.out.println("Maila ez da ezagutzen: " + mailaTestua);
        }
    }

    /**
     * hasierako menura itzultzeko
     */
    public void atzeraItzuli() {
        try {
            App.setRoot(App.loadFXML("/fxml/mainMenu"));
        } catch (IOException e) {
            System.err.println("Error al volver al menú principal: " + e.getMessage());
        }
    }

}
