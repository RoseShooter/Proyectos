package paagbat.controller;

import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import paagbat.App;
import paagbat.model.DBKonektatu;
import paagbat.model.base.Jokoa;
import paagbat.model.base.Sesioa;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class InfoJokoaController {

    private Jokoa joko;

    private WebView webView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Text jokotitulu;

    @FXML
    private Label garatzaileak;

    @FXML
    private Label plataforma;

    @FXML
    private Label generoak;

    @FXML
    private Label pegi;

    @FXML
    private Label prezio;

    @FXML
    private Label irteeraData;

    @FXML
    private Label deskribapen;

    @FXML
    private ImageView jokoIrudi;

    @FXML
    private StackPane trailer;

    @FXML
    private Button itxiBotoia;

    @FXML
    private Button editatuBtn;

    @FXML
    private void initialize() {
        if (Sesioa.isAdmin()) {
            editatuBtn.setVisible(true);
        } else {
            editatuBtn.setVisible(false);
        }

        Platform.runLater(() -> {
            scrollPane.setVvalue(0.0); // vertical: al inicio
        });

    }

    /**
     * Aurreko lehioan aukeratu den jokoaren datu guztiak erakusteko
     * 
     * @param joko erakutsiko den jokoa
     */
    public void setJokoa(Jokoa joko) {
        this.joko = joko;

        jokotitulu.setText(joko.getTitulua());
        garatzaileak.setText(joko.getGaratzailea());
        generoak.setText(joko.getGeneroa());
        plataforma.setText(joko.getPlataforma().name());
        pegi.setText(Integer.toString(joko.getPegi()));
        prezio.setText(Double.toString(joko.getPrezioa()));
        irteeraData.setText(joko.getIrteeraData());
        deskribapen.setText(joko.getDeskribapena());

        String fitxategiIzena = joko.getIrudia();
        String irudiPath = DBKonektatu.JOKOAK_RUTA + fitxategiIzena;

        File file = new File(irudiPath);
        if (file.exists()) {
            Image irudia = new Image(file.toURI().toString());
            jokoIrudi.setImage(irudia);

            jokoIrudi.setFitHeight(440);
            jokoIrudi.setFitWidth(410);
            jokoIrudi.setPreserveRatio(true);
            jokoIrudi.setSmooth(true);
        } else {
            erakutziAlert("Errorea", "Ezin izan da irudia aurkitu.");
        }

        String trailerUrl = joko.getTrailer();
        if (trailerUrl != null && !trailerUrl.isBlank() && trailerUrl.contains("youtube.com")) {
            if (trailerUrl.contains("watch?v=")) {
                trailerUrl = trailerUrl.replace("watch?v=", "embed/");
            }

            webView = new WebView();
            webView.setPrefSize(600, 330);
            webView.getEngine().load(trailerUrl);
            trailer.getChildren().add(webView);
        }

    }

    /**
     * Aukeratutako jokoa zure zerrenda batean gehitzeko
     * @throws SQLException
     */
    @FXML
    private void gehituJokoa() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Jokoa gehitu");
        alert.setHeaderText("Non gehitu nahi duzu jokoa?");
        alert.setContentText("Hautatu aukera bat:");

        ButtonType botoiDesio = new ButtonType("Desiratutakoak");
        ButtonType botoiNireJokoak = new ButtonType("Nire Jokoak");
        ButtonType botoiUtzi = new ButtonType("Utzi", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(botoiDesio, botoiNireJokoak, botoiUtzi);

        Optional<ButtonType> emaitza = alert.showAndWait();
        if (emaitza.isPresent()) {
            if (emaitza.get() == botoiDesio) {
                App.db.gehituDesirora(Sesioa.getErabiltzaileId(), joko);
                erakutziAlert("Gehituta", "Jokoa zure desiratutako jokoen zerrendara gehitu da.");
            } else if (emaitza.get() == botoiNireJokoak) {
                App.db.gehituNireJokoa(joko, Sesioa.getErabiltzaileId());
                erakutziAlert("Gehituta", "Jokoa zure nire jokoak zerrendara gehitu da.");
            }
        }
    }

    /**
     * Admin bazara jokoaren datuak editatzeko lehioa irekitzeko
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void jokoEdit(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/paagbat/fxml/JokoaEditatu.fxml"));
        Parent root = loader.load();

        EditatuJokoaController controller = loader.getController();
        Jokoa jokoOsoa = App.db.jokoaLortu(joko.getId());
        controller.setJokoa(jokoOsoa);

        // Callback-a jarri errefreskatzeko editatu ondoren
        controller.setOnUpdateCallback(() -> {
            berkargatuJokoa(); // <- oraingo lehioa eguneratzen du
        });        

        Stage newStage = new Stage();
        newStage.setTitle("Jokoa editatu");
        newStage.setScene(new Scene(root));
        newStage.setWidth(700);
        newStage.setHeight(730);

        newStage.showAndWait();
    }

    /**
     * Jokoa aldatu bada berriro kargatzeko
     */
    private void berkargatuJokoa() {
        try {
            // Jokoa berriro kargatu datu berriekin
            Jokoa jokoOsoa = App.db.jokoaLortu(joko.getId());
            setJokoa(jokoOsoa); // Bistan datuak birkargatzen ditu
        } catch (SQLException e) {
            erakutziAlert("Errorea", "Ezin izan da jokoa berriro kargatu.");
            e.printStackTrace();
        }
    }
    

    /**
     * Alertak sortzeko modu errazago batean
     * @param titulua     alertaren titulua
     * @param deskripzioa alertaren gorputzean doan textua
     */
    private void erakutziAlert(String titulua, String deskripzioa) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulua);
        alert.setContentText(deskripzioa);
        alert.showAndWait();
    }

    /**
     * Lehioa itxi eta aurrekora itzultzeko
     */
    @FXML
    private void itxiLehioa() {
        if (webView != null) {
            WebEngine engine = webView.getEngine();
            engine.load(null); // Bideoa gelditzeko
        }

        Stage stage = (Stage) itxiBotoia.getScene().getWindow();
        stage.close();
    }

}
