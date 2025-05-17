package paagbat.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
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

public class JokoakController {

    @FXML
    private HBox erabilBox;

    @FXML
    private TableView<Jokoa> jokoakTaula;

    @FXML
    private VBox sartu;

    @FXML
    private VBox eguneratu;

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

    @FXML
    private Pagination pagination;

    private boolean desplegado = false;
    private static final int ROWS_PER_PAGE = 16;
    private ObservableList<Jokoa> jokoakLista;

    /**
     * Eszena ireki bezain laster gertatuko diren gauzak,
     * kasu honetan taularen zutabeak taulan jarriko dira eta bertako informazioa
     * agertuko da.
     * 
     * @throws SQLException
     */
    @FXML
    private void initialize() throws SQLException {
        if (Sesioa.isAdmin()) {
            sartu.setVisible(true);
            eguneratu.setVisible(true);
            erabilBox.setVisible(true);
        } else {
            sartu.setVisible(false);
            eguneratu.setVisible(false);
            erabilBox.setVisible(false);
        }

        String irudia = DBKonektatu.IMG_RUTA + Sesioa.getProfilIrudia();
        Image img = new Image(new File(irudia).toURI().toString());
        profil.setImage(img);

        profil.imageProperty().addListener((obs, oldImg, newImg) -> {
            irudiaZirkularra();
        });

        List<Jokoa> jokoak = App.db.jokoakErakutsi();
        jokoakLista = FXCollections.observableArrayList(jokoak);

        int pageCount = (int) Math.ceil((double) jokoakLista.size() / ROWS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(0);

        // Listener para cambios de página
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            updateTableView(newIndex.intValue());
        });

        taulaSortu();

        updateTableView(0);

        irudiaZirkularra();

        username.setText(Sesioa.getErabiltzaileIzen());

        nire_listak.setOnMouseClicked(event -> toggleListak());

        profil.setOnMouseClicked(event -> {
            ContextMenu menu = sortuProfilMenua();
            menu.show(profil, event.getScreenX(), event.getScreenY());
        });        

    }

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
     * Taulako zutabeak sortzeko
     * @throws SQLException
     */
    private void taulaSortu() throws SQLException {

        jokoakTaula.setItems(jokoakLista);

        // PEGI zutabea, tamaina minimo batekin
        TableColumn<Jokoa, Integer> colPegi = new TableColumn<>("PEGI");
        colPegi.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPegi()).asObject());
        colPegi.setPrefWidth(50); // Zutaberako tamaina finkoa
        colPegi.setMinWidth(50); // Zutaberako tamaina minimoa

        // Irteera dataren zutabea, tamaina minimo batekin
        TableColumn<Jokoa, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIrteeraData()));
        colData.setPrefWidth(80); // Zutaberako tamaina finkoa
        colData.setMinWidth(80); // Zutaberako tamaina minimoa

        // Tituluaren zutabea, tamaina minimo batekin
        TableColumn<Jokoa, String> colTitulua = new TableColumn<>("Titulua");
        colTitulua.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulua()));
        colTitulua.setResizable(true); // Tamaina aldatu daiteke
        colTitulua.setMinWidth(200); // Zutaberako tamaina minimoa

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

        // Gehitzeko zutabea, tamaina minimo batekin
        TableColumn<Jokoa, Void> gehituCol = new TableColumn<>("Gehitu");
        gehituCol.setCellFactory(param -> new TableCell<>() {
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
                            jokoaMugitu(joko);
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

        gehituCol.setPrefWidth(100);
        gehituCol.setMinWidth(100);

        // Zutabeak taulan jarri
        jokoakTaula.getColumns().setAll(colTitulua, colGeneroa, colPlataforma, colPegi, colData, colPrezioa, infoCol,
                gehituCol);
        jokoakTaula.setPlaceholder(new Text("Ez dago jorik erakusteko."));
    }

    /**
     * Taulako jokoen datuak eguneratzeko
     * @throws SQLException
     */
    @FXML
    private void eguneratuJokoak() throws SQLException {
        // Eguneratu aurretik momentuko orriaren zenbakia gorde
        int momentukoPag = pagination.getCurrentPageIndex();

        // Datuak eguneratu
        List<Jokoa> jokoak = App.db.jokoakErakutsi();
        jokoakLista = FXCollections.observableArrayList(jokoak);

        // Orri zenbakia berriro kalkulatu
        int orriKop = (int) Math.ceil((double) jokoakLista.size() / ROWS_PER_PAGE);
        pagination.setPageCount(orriKop);

        // Orria eguneratu
        int pageToSet = Math.min(momentukoPag, orriKop - 1);
        pagination.setCurrentPageIndex(pageToSet);

        // Taula eguneratu
        updateTableView(pageToSet);
    }

    /**
     * Pagination-ak edukiko dituen orri kopurua
     * @param pageIndex
     */
    private void updateTableView(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, jokoakLista.size());

        jokoakTaula.setItems(FXCollections.observableArrayList(
                jokoakLista.subList(fromIndex, toIndex)));

        jokoakTaula.refresh();
    }

    /**
     * Nire Listak zatiak daukan submenua erakusteko
     */
    @FXML
    private void toggleListak() {
        desplegado = !desplegado;
        subListak.setVisible(desplegado);
        subListak.setManaged(desplegado);
    }

    /**
     * Profileko irudia zirkularra ikusteko
     */
    private void irudiaZirkularra() {
        Circle clip = new Circle(25, 25, 25);
        profil.setClip(clip);
    }

    /**
     * Aukeratutako jokoa zure zerrendetako batean kopiatzeko
     * @param joko kopiatuko den jokoaa
     * @throws SQLException datu basean gordeko delako
     */
    @FXML
    private void jokoaMugitu(Jokoa joko) throws SQLException {
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
     * Jokoaren informazioa erakusten duen lehioa irekitzeko
     * 
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

    @FXML
    private void jokatuak() throws IOException {
        App.setRoot("Amaitutakoak");
    }

    /**
     * Joko berri bat gehitzeko lehioa irekitzeko
     * 
     * @throws IOException
     */
    @FXML
    private void jokoaGehitu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/paagbat/fxml/JokoaSortu.fxml"));
        Parent root = loader.load();

        Stage newStage = new Stage();
        newStage.setTitle("Jokoa gehitu");
        newStage.setScene(new Scene(root));
        newStage.setWidth(500);
        newStage.setHeight(780);

        newStage.showAndWait();
    }
}
