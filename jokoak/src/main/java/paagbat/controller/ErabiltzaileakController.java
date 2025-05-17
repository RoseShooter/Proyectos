package paagbat.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.kordamp.ikonli.fontawesome.FontAwesome;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import java.nio.file.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import paagbat.App;
import paagbat.model.DBKonektatu;
import paagbat.model.base.Erabiltzailea;
import paagbat.model.base.Sesioa;
import org.kordamp.ikonli.javafx.FontIcon;

public class ErabiltzaileakController {

    @FXML
    private HBox erabilBox;

    @FXML
    private Button btnAldatu;

    @FXML
    private Text username;

    @FXML
    private TableView<Erabiltzailea> erabiltzaileakTaula;

    @FXML
    private Circle circleBorder;

    @FXML
    private ImageView profil;

    @FXML
    private HBox nire_listak;

    @FXML
    private VBox subListak;

    private boolean desplegado = false;
    private ObservableList<Erabiltzailea> erabiltzaileak;

    @FXML
    private void initialize() throws SQLException, IOException {
        erabilBox.setVisible(Sesioa.isAdmin());

        String irudia = DBKonektatu.IMG_RUTA + Sesioa.getProfilIrudia();
        Image img = new Image(new File(irudia).toURI().toString());
        profil.setImage(img);
        profil.imageProperty().addListener((obs, oldImg, newImg) -> irudiaZirkularra());
        irudiaZirkularra();

        username.setText(Sesioa.getErabiltzaileIzen());
        nire_listak.setOnMouseClicked(event -> toggleListak());

        List<Erabiltzailea> erabiltzaileakList = App.db.erabiltzaileakErakutsi();
        erabiltzaileak = FXCollections.observableArrayList(erabiltzaileakList);

        taulaSortu();

        profil.setOnMouseClicked(event -> {
            ContextMenu menu = sortuProfilMenua();
            menu.show(profil, event.getScreenX(), event.getScreenY());
        });   
    }

    /**
     * Taulako zutabeak sortzeko
     * @throws SQLException
     */
    private void taulaSortu() throws SQLException {
        erabiltzaileakTaula.setItems(erabiltzaileak);
        erabiltzaileakTaula.setEditable(true);

        TableColumn<Erabiltzailea, String> colErab = new TableColumn<>("Erabiltzailea");
        colErab.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getErabiltzaileIzena()));
        colErab.setMinWidth(150);

        TableColumn<Erabiltzailea, String> colGmail = new TableColumn<>("Email");
        colGmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        colGmail.setMinWidth(120);

        TableColumn<Erabiltzailea, String> colRola = new TableColumn<>("Rola");
        colRola.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRola().toString()));
        colRola.setMinWidth(100);

        // Aldatu zutabea
        TableColumn<Erabiltzailea, Void> colAldatu = new TableColumn<>("Aldatu");
        colAldatu.setCellFactory(param -> new TableCell<>() {
            // Botoia sortu
            private final Button btnAldatu = new Button();

            {
                // Botoiaren barruan ikono bat jartzeko
                FontIcon editIcon = new FontIcon(FontAwesome.EDIT);
                editIcon.setIconSize(16);
                btnAldatu.setGraphic(editIcon);

                // Botoiak egingo duena klik egiterakoan
                btnAldatu.setOnAction(event -> {
                    Erabiltzailea user = getTableRow().getItem();
                    if (user != null) {
                        try {
                            erabiltzaileEditatu(user);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnAldatu);
                }
            }
        });

        // Ezabatu zutabea
        TableColumn<Erabiltzailea, Void> colEzabatu = new TableColumn<>("Ezabatu");
        colEzabatu.setCellFactory(param -> new TableCell<>() {
            // Botoia sortu
            private final Button btnEzabatu = new Button();

            {
                // Botoiaren barruan ikono bat jartzeko
                FontIcon deleteIcon = new FontIcon(FontAwesome.TRASH);
                deleteIcon.setIconSize(16);
                btnEzabatu.setGraphic(deleteIcon);

                // Botoiak egingo duena klik egiterakoan
                btnEzabatu.setOnAction(event -> {
                    // Botoian klik egiterakoan erabiltzailea ezabatu
                    Erabiltzailea user = getTableRow().getItem();
                    if (user != null) {
                        try {
                            ezabatuErabiltzailea(user); // Ezabatzeko metodoa deitu
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEzabatu);
                }
            }
        });

        erabiltzaileakTaula.getColumns().setAll(colErab, colGmail, colRola, colAldatu, colEzabatu);
        erabiltzaileakTaula.setPlaceholder(new Text("Ez dago jorik erakusteko."));
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
    private void irudiaZirkularra() {
        Circle clip = new Circle(25, 25, 25);
        profil.setClip(clip);
    }

    /**
     * Aukeratutako erabiltzailearen datuak editatzeko
     * @param erabiltzailea editatuko den erabiltzaileaten datuak
     * @throws IOException 
     */
    @FXML
    private void erabiltzaileEditatu(Erabiltzailea erabiltzailea) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/paagbat/fxml/ErabiltzaileaEditatu.fxml"));
        Parent root = loader.load();

        ErabiltzaileaEditatuController controller = loader.getController();
        controller.setErabiltzailea(erabiltzailea);

        Stage newStage = new Stage();
        newStage.setTitle("Erabiltzailea editatu");
        newStage.setScene(new Scene(root));
        newStage.setWidth(400);
        newStage.setHeight(390);

        newStage.showAndWait();
    }

    /**
     * Erabiltzaileen taula birkargatzeko
     * @throws SQLException
     */
    @FXML
    private void updateErabiltzaileTaula() throws SQLException {
        List<Erabiltzailea> erabiltzaileak = App.db.erabiltzaileakErakutsi();
        ObservableList<Erabiltzailea> erabiltzaileakList = FXCollections.observableArrayList(erabiltzaileak);
        erabiltzaileakTaula.getItems().clear();
        erabiltzaileakTaula.setItems(erabiltzaileakList);
        erabiltzaileakTaula.refresh();
    }

    /**
     * Erabiltzailea ezabatzeko
     * @param user erabiltzailearen datuak
     * @throws SQLException
     */
    private void ezabatuErabiltzailea(Erabiltzailea user) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ezabatze egiaztapena");
        alert.setHeaderText(null);
        alert.setContentText("Seguru zaude " + user.getErabiltzaileIzena() + " ezabatu nahi duzula?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                App.db.erabiltzaileaEzabatu(user);
                ezabatuProfilaIrudia(user.getProfilIrudi());
                updateErabiltzaileTaula();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Errorea");
                alerta.setHeaderText("Ezabatzeko arazoa");
                alerta.setContentText("Erroreren bat gertatu da erabiltzailea ezabatzerakoan.");
                alerta.showAndWait();
            }
            updateErabiltzaileTaula(); // Actualizar la tabla
        }
    }

    /**
     * Profileko irudi berria aukeratzerakoan aurrekoa ezabatzeko default-a izan ezik
     * @param fitxategiIzena
     */
    private void ezabatuProfilaIrudia(String fitxategiIzena) {
        final String DEFAULT_IRUDIA = "default.png";
    
        if (fitxategiIzena != null && !fitxategiIzena.equals(DEFAULT_IRUDIA)) {
            Path irudiaPath = Paths.get(DBKonektatu.IMG_RUTA + fitxategiIzena);
            try {
                Files.deleteIfExists(irudiaPath);
                System.out.println("Irudia ezabatuta: " + irudiaPath);
            } catch (IOException e) {
                System.err.println("Ezin izan da irudia ezabatu: " + irudiaPath);
                e.printStackTrace();
            }
        }
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

    @FXML
    private void nireProfila() throws IOException {
        App.setRoot("Nire_Profila");
    }

    @FXML
    private void hasierara() throws IOException {
        App.setRoot("Hasiera");
    }

    @FXML
    private void jokoetara() throws IOException {
        App.setRoot("Jokoak");
    }

    @FXML
    private void desiroa() throws IOException {
        App.setRoot("Desiratutakoak");
    }

    @FXML
    private void nireak() throws IOException {
        App.setRoot("Nire_Jokoak");
    }

}
