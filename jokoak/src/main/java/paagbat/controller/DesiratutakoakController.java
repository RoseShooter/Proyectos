package paagbat.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import paagbat.App;
import paagbat.model.DBKonektatu;
import paagbat.model.base.Jokoa;
import paagbat.model.base.Sesioa;

public class DesiratutakoakController {
    private Jokoa joko;

    @FXML
    private HBox erabilBox;

    @FXML
    private TableView<Jokoa> jokoakTaula;

    @FXML
    private ImageView profil;

    @FXML
    private Text username;

    @FXML
    private Circle circleBorder;

    @FXML
    private HBox nire_listak;

    @FXML
    private VBox subListak;

    private boolean desplegado = false;
    private ObservableList<Jokoa> jokoLista;

    @FXML
    private void initialize() throws SQLException{
        if (Sesioa.isAdmin()) {
            erabilBox.setVisible(true);
        } else {
            erabilBox.setVisible(false);
        }

        String irudia = DBKonektatu.IMG_RUTA + Sesioa.getProfilIrudia();
        Image img = new Image(new File(irudia).toURI().toString());
        profil.setImage(img);

        profil.imageProperty().addListener((obs, oldImg, newImg) -> {
            irudiaZirkularra();
        });

        subListak.setVisible(true);
        subListak.setManaged(true);
        desplegado = true;

        irudiaZirkularra();

        username.setText(Sesioa.getErabiltzaileIzen());

        nire_listak.setOnMouseClicked(event -> toggleListak());

        List<Jokoa> jokoak = App.db.lortuDesiratutakoak(Sesioa.getErabiltzaileId());
        jokoLista = FXCollections.observableArrayList(jokoak);

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
        jokoakTaula.setItems(jokoLista);

        // Tituluaren zutabea, tamaina minimo batekin
        TableColumn<Jokoa, String> colTitulua = new TableColumn<>("Titulua");
        colTitulua.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulua()));
        colTitulua.setResizable(true); // Tamaina aldatu daiteke
        colTitulua.setMinWidth(200); // Zutaberako tamaina minimoa

        // Garatzailearen zutabea, tamaina minimo batekin
        TableColumn<Jokoa, String> colGaratzaile = new TableColumn<>("Garatzailea");
        colGaratzaile.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGaratzailea()));
        colGaratzaile.setResizable(true); // Tamaina aldatu daiteke
        colGaratzaile.setMinWidth(100); // Zutaberako tamaina minimoa

        // Generoaren zutabea, tamaina minimo batekin
        TableColumn<Jokoa, String> colGeneroa = new TableColumn<>("Generoa");
        colGeneroa.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGeneroa()));
        colGeneroa.setResizable(true); // Tamaina aldatu daiteke
        colGeneroa.setMinWidth(100); // Zutaberako tamaina minimoa

        // Plataformaren zutabea, tamaina minimo batekin
        TableColumn<Jokoa, String> colPlataforma = new TableColumn<>("Plataforma");
        colPlataforma.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPlataforma().toString()));
        colPlataforma.setResizable(true); // Tamaina aldatu daiteke
        colPlataforma.setMinWidth(100); // Zutaberako tamaina minimoa

        // Irteera dataren zutabea, tamaina minimo batekin
        TableColumn<Jokoa, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIrteeraData()));
        colData.setPrefWidth(80); // Zutaberako tamaina finkoa
        colData.setMinWidth(80); // Zutaberako tamaina minimoa

        // Prezioaren zutabea, tamaina minimo batekin
        TableColumn<Jokoa, Double> colPrezioa = new TableColumn<>("Prezioa");
        colPrezioa.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrezioa()).asObject());
        colPrezioa.setPrefWidth(80); // Zutaberako tamaina finkoa
        colPrezioa.setMinWidth(80); // Zutaberako tamaina minimoa

        // Informazioaren zutabea, tamaina minimo batekin
        TableColumn<Jokoa, Void> infoCol = new TableColumn<>("Info");
        infoCol.setCellFactory(param -> new TableCell<>() {
            // Botoia sortu
            private final Button btnInfo = new Button();

            {
                // Botoiaren barruan ikono bat jartzeko
                FontIcon infoCol = new FontIcon("fa-info-circle");
                infoCol.setIconSize(16);
                btnInfo.setGraphic(infoCol);
                btnInfo.setStyle("-fx-cursor: hand;");

                // Botoiak egingo duena klik egiterakoan
                btnInfo.setOnAction(event -> {
                    Jokoa joko = getTableRow().getItem();
                    if (joko != null) {
                        try {
                            jokoInformazioa(joko);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnInfo);
            }
        });

        infoCol.setPrefWidth(80);
        infoCol.setMinWidth(80);

        // Editatzeko zutabea, tamaina minimo batekin
        TableColumn<Jokoa, Void> colEditatu = new TableColumn<>("Editatu");
        colEditatu.setCellFactory(param -> new TableCell<>() {
            // Botoia sortu
            private final Button btnGehitu = new Button();

            {
                // Botoiaren barruan ikono bat jartzeko
                FontIcon addIcon = new FontIcon("fa-plus-circle");
                addIcon.setIconSize(16);
                btnGehitu.setGraphic(addIcon);
                btnGehitu.setStyle("-fx-cursor: hand;");

                // Botoiak egingo duena klik egiterakoan
                btnGehitu.setOnAction(event -> {
                    Jokoa joko = getTableRow().getItem();
                    if (joko != null) {
                        try {
                            jokoaEditatu(joko);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnGehitu);
            }
        });

        jokoakTaula.getColumns().setAll(colTitulua, colGaratzaile, colGeneroa, colPlataforma, colData, colPrezioa, infoCol, colEditatu);
        jokoakTaula.setPlaceholder(new Text("Ez dago jorik erakusteko."));

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
     * Jokoaren informazioa erakusten duen lehioa irekitzeko
     * @param joko
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void jokoInformazioa(Jokoa joko) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/paagbat/fxml/InfoJokoa.fxml"));
        Parent root = loader.load();

        InfoJokoaController controller = loader.getController();
        Jokoa jokoOsoa = App.db.jokoaLortu(joko.getId());
        controller.setJokoa(jokoOsoa);

        Stage newStage = new Stage();
        newStage.setTitle("Joko informazioa");
        newStage.setScene(new Scene(root));
        newStage.setWidth(860);
        newStage.setHeight(720);

        newStage.showAndWait();
    }

    /**
     * Jokoa editatzeko lehia irekitzeko
     * @param joko editatuko den jokoa
     * @throws SQLException
     */
    @FXML
    private void jokoaEditatu(Jokoa joko) throws SQLException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Jokoa editatu");
        alert.setHeaderText("Zer egin nahi duzu jokoarekin, mugitu edo ezabatu?");
        alert.setContentText("Hautatu aukera bat:");

        ButtonType botoiEzabatu = new ButtonType("Ezabatu");
        ButtonType botoiNireJokoak = new ButtonType("Nire Jokoak");
        ButtonType botoiUtzi = new ButtonType("Utzi", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(botoiEzabatu, botoiNireJokoak, botoiUtzi);

        Optional<ButtonType> emaitza = alert.showAndWait();
        if (emaitza.isPresent()) {
            if (emaitza.get() == botoiEzabatu) {
                App.db.ezabatuDesiroa(Sesioa.getErabiltzaileId(), joko.getId());
                erakutziAlert("Ezabatuta", "Jokoa zure desiroen zerrendatik ezabatu da.");
            } else if (emaitza.get() == botoiNireJokoak) {
                if (App.db.jokoaDagoenekoGehitutaDago(Sesioa.getErabiltzaileId(), joko.getId())) {
                    erakutziAlert("Errorea", "Jokoa dagoeneko nire jokoak zerrendan dago.");
                    return;
                }     
                App.db.gehituNireJokoa(joko, Sesioa.getErabiltzaileId());
                App.db.ezabatuDesiroa(Sesioa.getErabiltzaileId(), joko.getId());
                erakutziAlert("Gehituta", "Jokoa zure nire jokoak zerrendara gehitu da.");
                App.db.ezabatuDesiroa(Sesioa.getErabiltzaileId(), joko.getId());
            }
        }
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

    @FXML
    private void nireProfila() throws IOException {
        App.setRoot("Nire_Profila");
    }

    @FXML
    private void hasierara() throws IOException {
        App.setRoot("Hasiera");
    }

    @FXML
    private void erabiltzaileetara() throws IOException {
        App.setRoot("Erabiltzaileak");
    }

    @FXML
    private void jokoetara() throws IOException {
        App.setRoot("Jokoak");
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

    /**
     * Alertak modu azkarrako batean sortzeko
     * @param titulua alertare titulua
     * @param deskripzioa alertaren barruko textua
     */
    private void erakutziAlert(String titulua, String deskripzioa) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulua);
        alert.setContentText(deskripzioa);
        alert.showAndWait();
    }
}
