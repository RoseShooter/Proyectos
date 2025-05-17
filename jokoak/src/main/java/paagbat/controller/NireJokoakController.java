package paagbat.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import paagbat.App;
import paagbat.model.DBKonektatu;
import paagbat.model.base.JokatutakoJokoa;
import paagbat.model.base.Sesioa;

public class NireJokoakController {

    @FXML
    private HBox erabilBox;

    @FXML
    private TableView<JokatutakoJokoa> jokoakTaula;

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
    private ObservableList<JokatutakoJokoa> jokoLista;

    @FXML
    private void initialize() throws SQLException {
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

        List<JokatutakoJokoa> jokoak = App.db.lortuNireJokoak(Sesioa.getErabiltzaileId());
        jokoLista = FXCollections.observableArrayList(jokoak);

        taulaSortu();
        
        profil.setOnMouseClicked(event -> {
            ContextMenu menu = sortuProfilMenua();
            menu.show(profil, event.getScreenX(), event.getScreenY());
        });   

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
     * Taularen zutabeak sortzeko
     * @throws SQLException
     */
    private void taulaSortu() throws SQLException {
        jokoakTaula.setItems(jokoLista);

        // Tituluaren zutabea, tamaina minimo batekin
        TableColumn<JokatutakoJokoa, String> colTitulua = new TableColumn<>("Titulua");
        colTitulua.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulua()));
        colTitulua.setResizable(true); // Tamaina aldatu daiteke
        colTitulua.setMinWidth(200); // Zutaberako tamaina minimoa

        // Generoaren zutabea, tamaina minimo batekin
        TableColumn<JokatutakoJokoa, String> colGeneroa = new TableColumn<>("Generoa");
        colGeneroa.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGeneroa()));
        colGeneroa.setResizable(true); // Tamaina aldatu daiteke
        colGeneroa.setMinWidth(100); // Zutaberako tamaina minimoa

        // Plataformaren zutabea, tamaina minimo batekin
        TableColumn<JokatutakoJokoa, String> colPlataforma = new TableColumn<>("Plataforma");
        colPlataforma.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPlataforma().toString()));
        colPlataforma.setResizable(true); // Tamaina aldatu daiteke
        colPlataforma.setMinWidth(100); // Zutaberako tamaina minimoa

        // Egoeraren zutabea, tamaina minimo batekin
        TableColumn<JokatutakoJokoa, String> colEgoera = new TableColumn<>("Egoera");
        colEgoera.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEgoera().toString()));
        colEgoera.setResizable(true); // Tamaina aldatu daiteke
        colEgoera.setMinWidth(100); // Zutaberako tamaina minimoa

        // Orduak zutabea, tamaina minimo batekin
        TableColumn<JokatutakoJokoa, Integer> colOrduak = new TableColumn<>("Orduak");
        colOrduak.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getJokatutakoOrduak()).asObject());
        colOrduak.setPrefWidth(50); // Zutaberako tamaina finkoa
        colOrduak.setMinWidth(50); // Zutaberako tamaina minimoa

        // Editatzeko zutabea, tamaina minimo batekin
        TableColumn<JokatutakoJokoa, Void> colEditatu = new TableColumn<>("Editatu");
        colEditatu.setCellFactory(param -> new TableCell<>() {
            // Botoia sortu
            private final Button btnEditatu = new Button();

            {
                // Botoiaren barruan ikono bat jartzeko
                FontIcon addIcon = new FontIcon("fa-plus-circle");
                addIcon.setIconSize(16);
                btnEditatu.setGraphic(addIcon);
                btnEditatu.setStyle("-fx-cursor: hand;");

                // Botoiak egingo duena klik egiterakoan
                btnEditatu.setOnAction(event -> {
                    JokatutakoJokoa joko = getTableRow().getItem();
                    System.out.println(joko.getNireListaId());
                    if (joko != null) {
                        try {
                            editatuJokatutakoa(joko);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnEditatu);
            }
        });

        // Zutabeak taulan jarri
        jokoakTaula.getColumns().setAll(colTitulua, colGeneroa, colPlataforma, colEgoera, colOrduak, colEditatu);
        jokoakTaula.setPlaceholder(new Text("Ez dago jorik erakusteko."));

    }

    /**
     * Jokatutako jokoak editatzeko
     * @param joko editatuko den jokoaren datuak lortzeko
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void editatuJokatutakoa(JokatutakoJokoa joko) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/paagbat/fxml/JokatutakoJokoaEditatu.fxml"));
        Parent root = loader.load();

        JokatutakoJokoa jokoOsoa = App.db.jokatutakoJokoaErakutzi(joko.getNireListaId());
        System.out.println(joko.getNireListaId());

        if (jokoOsoa != null) {
            JokatutakoJokoaEditatuController controller = loader.getController();
            controller.setJokoa(jokoOsoa);

            Stage newStage = new Stage();
            newStage.setTitle("Jokoa editatu");
            newStage.setScene(new Scene(root));
            newStage.setWidth(400);
            newStage.setHeight(350);

            newStage.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errorea");
            alert.setHeaderText("Ez da jokoa aurkitu");
            alert.setContentText("Jokoa ez da zerrendan existitzen.");
            alert.showAndWait();
        }
    }

    /**
     * Jokoak eguneratzeko
     * @throws SQLException
     */
    @FXML
    private void updateJokoak() throws SQLException {
        List<JokatutakoJokoa> jokoak = App.db.lortuNireJokoak(Sesioa.getErabiltzaileId());
        ObservableList<JokatutakoJokoa> jokoaList = FXCollections.observableArrayList(jokoak);
        jokoakTaula.setItems(jokoaList);
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
    private void desiroa() throws IOException {
        App.setRoot("Desiratutakoak");
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
