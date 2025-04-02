package paagbat.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import paagbat.App;
import paagbat.model.base.PartekatutakoDatuak;
import paagbat.model.enumerazioa.Hizkuntza;

public class MainMenu {

    @FXML
    private ComboBox<Hizkuntza> cbHizkun;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private ImageView logo;

    private Hizkuntza hizkuntza = Hizkuntza.EUSKERA;

    @FXML
    public void initialize() {
        // Combo box barruan dauden aukerak jarri
        cbHizkun.setItems(FXCollections.observableArrayList(Hizkuntza.EUSKERA, Hizkuntza.INGLES, Hizkuntza.ESPAÑOL));
        cbHizkun.setValue(Hizkuntza.EUSKERA); // Hasierako hizkuntza

        // Combo box-aren barruan irudiak jarri
        cbHizkun.setCellFactory(param -> new ListCell<Hizkuntza>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Hizkuntza item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(getClass().getResource(getImagePath(item)).toExternalForm()));
                    //Combo box-aren barruan dauden irudien tamaina ezarri
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);
                }
            }
        });

        cbHizkun.setOnAction(event -> hizkuntzaAldatu());

        // Botoientzako ebentoak konfigutatu
        btn1.setOnAction(this::botoiakKontrolatu);
        btn2.setOnAction(this::botoiakKontrolatu);
        btn3.setOnAction(this::botoiakKontrolatu);

        // Hasierako textu gutziak jarri
        textuaJarri();

        try {
            // Hasierako logoa kargatu
            Image logoIrudi = new Image(getClass().getResource("/img/hitz.png").toExternalForm());
            logo.setImage(logoIrudi);
            logo.setFitWidth(375);
            logo.setFitHeight(375);
            logo.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Error al cargar el logo: " + e.getMessage());
        }
    }

    /**
     * Hizkuntzaren arabera textua aldatu
     */
    private void textuaJarri() {
        switch (hizkuntza) {
            case EUSKERA:
                btn1.setText("A1 Maila");
                btn2.setText("A2 Maila");
                btn3.setText("B1 Maila");
                break;
            case INGLES:
                btn1.setText("A1 Level");
                btn2.setText("A2 Level");
                btn3.setText("B1 Level");
                break;
            case ESPAÑOL:
                btn1.setText("Nivel A1");
                btn2.setText("Nivel A2");
                btn3.setText("Nivel B1");
                break;
        }
    }

    /**
     * ComboBox-aren barruan dagoenaren arabera galderen hizkuntza aldatzen da.
     */
    private void hizkuntzaAldatu() {
        hizkuntza = cbHizkun.getValue();
        textuaJarri();
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

    @FXML
    /**
     * Aukeratutako botoiaren textua gordetzeko
     * @param event gertzatzen dena, kasu honetan botoi bat aukeratu
     */
    public void botoiakKontrolatu(ActionEvent event) {
        try {
            // Obtener el texto del botón presionado
            String textuBotoia = ((Button) event.getSource()).getText();
            PartekatutakoDatuak.mailaTextua = textuBotoia;

            // Cargar nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/categoryMenu.fxml"));
            Parent categoryMenuRoot = loader.load();
            CategoryMenu controller = loader.getController();
            controller.setTextua(textuBotoia);

            // Cambiar de escena
            App.setRoot(categoryMenuRoot);
        } catch (IOException e) {
            System.err.println("Error al cargar el FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Aplikazioa izteko
     */
    public void itxi() {
        Platform.exit();
    }
}
