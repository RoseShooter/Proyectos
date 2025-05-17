package paagbat.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import paagbat.App;
import paagbat.model.DBKonektatu;
import paagbat.model.base.Sesioa;

public class HasieraController {

    @FXML
    private Text desiratutakoak;

    @FXML
    private HBox erabilBox;

    @FXML
    private Text username;

    @FXML
    private Circle circleBorder;

    @FXML
    private Text ongietorria;

    @FXML
    private ImageView profil;

    @FXML
    private HBox nire_listak;

    @FXML
    private VBox subListak;

    @FXML
    private BarChart<String, Number> jokoenEgoera;

    private boolean desplegado = false;

    @FXML
    private void initialize() throws SQLException {
        if (Sesioa.isAdmin()) {
            erabilBox.setVisible(true);
        } else {
            erabilBox.setVisible(false);
        }

        try {
            grafikoaEguneratu();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String irudia = DBKonektatu.IMG_RUTA + Sesioa.getProfilIrudia();
        Image img = new Image(new File(irudia).toURI().toString());
        profil.setImage(img);

        profil.imageProperty().addListener((obs, oldImg, newImg) -> {
            irudiaZirKularra();
        });

        irudiaZirKularra();

        username.setText(Sesioa.getErabiltzaileIzen());

        ongietorria.setText("Ongi etorri, " + Sesioa.getErabiltzaileIzen() + "!!");

        nire_listak.setOnMouseClicked(event -> toggleListak());

        profil.setOnMouseClicked(event -> {
            ContextMenu menu = sortuProfilMenua();
            menu.show(profil, event.getScreenX(), event.getScreenY());
        });     

        desiratutakoak.setText(Integer.toString(App.db.desiroKantitatea(Sesioa.getErabiltzaileId())));

    }

    /**
     * Profileko irudia menu bat bihurtzeko
     * @return
     */
    private ContextMenu sortuProfilMenua() {
        ContextMenu menu = new ContextMenu();
    
        MenuItem profilaItem = new MenuItem("Nire Profila");
        profilaItem.setOnAction(e -> {
            try {
                App.setRoot("Nire_Profila");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    
        MenuItem logoutItem = new MenuItem("Itxi saioa");
        logoutItem.setOnAction(e -> {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Baieztapena");
                alert.setContentText("Benetan sesioa itxi nahi duzu?");
                Optional<ButtonType> aukera = alert.showAndWait();
                if (aukera.isPresent() && aukera.get() == ButtonType.OK) {
                    Sesioa.logOut(); // Sesiotik irten
                    App.setRoot("Login"); // Loginera bidali
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    
        menu.getItems().addAll(profilaItem, logoutItem);
        return menu;
    }

    /**
     * SubLista desplegatzeko
     */
    @FXML
    private void toggleListak() {
        desplegado = !desplegado;
        subListak.setVisible(desplegado);
        subListak.setManaged(desplegado);
    }

    /**
     * Irudiaren zirkuluaren radioa jartzeko
     */
    private void irudiaZirKularra() {
        Circle clip = new Circle(25, 25, 25);
        profil.setClip(clip);
    }

    /**
     * Grafikan datuak erakusteko
     * @throws SQLException
     */
    public void grafikoaEguneratu() throws SQLException {
        // Datu basetik datuak lortzeko
        int erositakoJokoak = App.db.jokoKopEgoera(Sesioa.getErabiltzaileId(), "EROSITA");
        int hasitakoJokoak = App.db.jokoKopEgoera(Sesioa.getErabiltzaileId(), "HASITA");
        int jolastenJokoak = App.db.jokoKopEgoera(Sesioa.getErabiltzaileId(), "JOLASTEN");
        int amaitutakoJokoak = App.db.jokoKopEgoera(Sesioa.getErabiltzaileId(), "AMAITUTA");
        int dropeatutakoJokoak = App.db.jokoKopEgoera(Sesioa.getErabiltzaileId(), "DROPEATUTA");
    
        // Ejeak konfiguratu
        CategoryAxis xAxis = (CategoryAxis) jokoenEgoera.getXAxis();
        xAxis.setLabel("Jokoen egoera");
        xAxis.setTickLabelRotation(0);
    
        NumberAxis yAxis = (NumberAxis) jokoenEgoera.getYAxis();
        yAxis.setLabel("Joko kopurua");
    
        // Y ejearen balio maximoa ateratzeko
        int maxJokoKop = IntStream.of(erositakoJokoak, hasitakoJokoak, jolastenJokoak,
                amaitutakoJokoak, dropeatutakoJokoak).max().orElse(10);
        int upperBound = ((maxJokoKop / 10) + 1) * 10;
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(upperBound);
        yAxis.setTickUnit(upperBound > 10 ? upperBound / 10 : 1);
    
        // Aurreko datuak garbitzeko
        jokoenEgoera.getData().clear();
    
        jokoenEgoera.setLegendVisible(false);
        jokoenEgoera.setCategoryGap(20);
        jokoenEgoera.setBarGap(5);
        jokoenEgoera.setAnimated(false);
    
        // Usando tu estructura de serie única (para mantener el estilo visual)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Jokoen egoera");
    
        // Datuak gehitu
        series.getData().add(new XYChart.Data<>("Erosita", erositakoJokoak));
        series.getData().add(new XYChart.Data<>("Hasita", hasitakoJokoak));
        series.getData().add(new XYChart.Data<>("Jolasten", jolastenJokoak));
        series.getData().add(new XYChart.Data<>("Amaituta", amaitutakoJokoak));
        series.getData().add(new XYChart.Data<>("Dropeatuta", dropeatutakoJokoak));
    
        jokoenEgoera.getData().add(series);
    
        
    }

    @FXML
    private void jokoetara() throws IOException {
        App.setRoot("Jokoak");
    }

    @FXML
    private void nireProfila() throws IOException {
        App.setRoot("Nire_Profila");
    }

    @FXML
    private void erabiltzaileetara() throws IOException {
        App.setRoot("Erabiltzaileak");
    }

    @FXML
    private void desiroa() throws IOException {
        App.setRoot("Desiratutakoak");
    }

    @FXML
    private void nireak() throws IOException {
        App.setRoot("Nire_Jokoak");
    }

    /**
     * Aplikazioa ixteko
     */
    @FXML
    private void itxi() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Baieztapena");
        alert.setContentText("Benetan aplilaziotik atera nahi duzu?");
        Optional<ButtonType> aukera = alert.showAndWait();
        if (aukera.isPresent() && aukera.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            return;
        }

    }

    /**
     * Aplikazioaren lehioa txikitzeko itxi barik
     */
    @FXML
    private void txikitu() {
        App.stageTotal.setIconified(true);
    }
}