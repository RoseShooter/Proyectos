package paagbat.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import paagbat.App;
import paagbat.model.base.PartekatutakoDatuak;
import paagbat.model.enumerazioa.Hizkuntza;

import java.io.IOException;
import java.util.List;

public class Emaitza {

    @FXML
    private VBox emaitzaContainer;

    @FXML
    private Button btnMenu;

    @FXML
    private Label mezuLabel;

    @FXML
    private ComboBox<Hizkuntza> cbHizkun;

    private List<Boolean> emaitzak;
    private String maila;
    private Hizkuntza hizkuntza = Hizkuntza.EUSKERA;

    @FXML
    /**
     * eszena hasi eta momentuan exekutatu behar diren gauzak, kasu honetan combobox-aren irudiak kargatu
     * eta erantzundako galdera kopuruaren araberako botoiak sortu eta erantzunaren arabera kolore bat
     * edo bestea jarri.
     */
    public void initialize() {
        cbHizkun.setItems(FXCollections.observableArrayList(Hizkuntza.EUSKERA, Hizkuntza.INGLES, Hizkuntza.ESPAÑOL));
        cbHizkun.setValue(Hizkuntza.EUSKERA);

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
    }

    /**
     * ArrayList-ean dauden emaitzak botoietan erakusteko.
     * @param emaitzak erantzundako galderen emaitza, true zuzena, false okerra
     */
    public void setEmaitzak(List<Boolean> emaitzak) {
        this.emaitzak = emaitzak;
        erakutsiEmaitzak();
    }

    public void setMaila(String maila) {
        this.maila = maila;
    }

    /**
     * Erantzundako galdera kopuruaren araberako botoiak sortu eta erantzunaren arabera kolore bat
     * edo bestea erabili, label batean erantzundako galdera zuzenen kopurua adierazi
     */
    private void erakutsiEmaitzak() {
        emaitzaContainer.getChildren().clear();
        int zuzenak = 0;

        for (int i = 0; i < emaitzak.size(); i++) {
            Button btnEmaitza = new Button((i + 1) + " galdera");
            boolean zuzena = emaitzak.get(i);

            btnEmaitza.getStyleClass().add("emaitza-button");
            btnEmaitza.setStyle("-fx-background-color: " + (zuzena ? "green" : "red") + ";");

            if (zuzena) zuzenak++;

            switch (hizkuntza) {
                case EUSKERA:
                    btnEmaitza.setText((i + 1) + ". galdera");
                    break;
                case INGLES:
                    btnEmaitza.setText("Question " + (i + 1));
                    break;
                case ESPAÑOL:
                    btnEmaitza.setText("Pregunta " + (i + 1));
                    break;
            }

            emaitzaContainer.getChildren().add(btnEmaitza);
        }

        textuaEguneratu(zuzenak);
    }

    /**
     * Hizkuntzaren arabera label batean erantzundako galdera kopuru zuzena adierazteko
     * @param zuzenak zuzen erantzundako galdera kopurua
     */
    private void textuaEguneratu(int zuzenak) {
        switch (hizkuntza) {
            case EUSKERA:
                mezuLabel.setText(emaitzak.size() + " galderetatik " + zuzenak + " galdera zuzen erantzun dituzu");
                break;
            case INGLES:
                mezuLabel.setText("You answered " + zuzenak + " out of " + emaitzak.size() + " questions correctly");
                break;
            case ESPAÑOL:
                mezuLabel.setText("Respondiste correctamente " + zuzenak + " de " + emaitzak.size() + " preguntas");
                break;
        }
    }

    /**
     * ComboBox-aren barruan dagoenaren arabera galderen hizkuntza aldatzen da.
     */
    private void hizkuntzaAldatu() {
        hizkuntza = cbHizkun.getValue();
        erakutsiEmaitzak();
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
     * kategoria desberdinak agertzen diren menura itzultzeko lehenik aukeratutako mailaren arabera
     */
    private void itzuliMenu() {
        try {
            if (maila == null || maila.isEmpty()) {
                System.out.println("Errorea: maila ez dago inizializatua.");
                return;
            }

            PartekatutakoDatuak.mailaTextua = maila;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/categoryMenu.fxml"));
            Parent root = loader.load();

            CategoryMenu controller = loader.getController();
            if (controller != null) {
                controller.setTextua(maila);
            }

            App.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
