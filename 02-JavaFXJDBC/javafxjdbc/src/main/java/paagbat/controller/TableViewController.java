package paagbat.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import paagbat.App;
import paagbat.model.base.Herria;
import paagbat.model.base.KostakoHerria;

public class TableViewController implements Initializable {

    // Taula eta honen zutabeak
    @FXML
    private TableView<Herria> tableViewHerriak;

    @FXML
    private TableColumn<Herria, String> tableColumnHerria;

    @FXML
    private TableColumn<Herria, String> tableColumnProbintzia;

    @FXML
    private TableColumn<Herria, String> tableColumnHondartza1;

    @FXML
    private TableColumn<Herria, String> tableColumnHondartza2;

    @FXML
    private TableColumn<Herria, String> tableColumnHondartza3;

    // Taularen paginazioa kontrolatzeko elementua
    @FXML
    private Pagination pagination;

    // Orri bakoitzeko erakutziko diren erregistro kopuru maximoa
    private static final int ROWS_PER_PAGE = 5;

    // Herrien lista observable bat taula modu dinamiko batean eguneratzeko
    private ObservableList<Herria> herrienObservableLista;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Herrien lista lortzeko
        List<Herria> herriakList = App.herriak != null ? App.herriak.getHerriak() : new ArrayList<>();
        herrienObservableLista = FXCollections.observableArrayList(herriakList);
        
        // Paginazioan orrien kopurua konfiguratzeko
        pagination.setPageCount((int) Math.ceil((double) herrienObservableLista.size() / ROWS_PER_PAGE));
        pagination.setCurrentPageIndex(0);

        // Listener bat orria aldatzeko erablitzaileak paginazioa klikatzen duenean
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> updateTableView(newIndex.intValue()));
        
        // Taularen zutabeak konfiguratzeko eta bista eguneratzeko
        setupTableColumns();
        updateTableView(0);
    }

    /**
     * Taulan dauden zutebeak konfiguratzeko eta datu zuzenak erakusteko
     */
    private void setupTableColumns() {

        // HerriIzena eta Probintzia baloreak bilatu eta bere zutabean jartzen ditu
        tableColumnHerria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHerriIzena()));
        tableColumnProbintzia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProbintzia()));

        // Kostako herrientzako hondartzak bere zutabeetan jartzen dira
        tableColumnHondartza1.setCellValueFactory(cellData -> new SimpleStringProperty(getHondartza(cellData.getValue(), 0)));
        tableColumnHondartza2.setCellValueFactory(cellData -> new SimpleStringProperty(getHondartza(cellData.getValue(), 1)));
        tableColumnHondartza3.setCellValueFactory(cellData -> new SimpleStringProperty(getHondartza(cellData.getValue(), 2)));

        // Taularen edizioa ahalbidetzen du
        tableViewHerriak.setEditable(true);

        // Herria zutabearen edizioa konfiguratzen du
        tableColumnHerria.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnHerria.setOnEditCommit(t -> t.getRowValue().setHerriIzena(t.getNewValue()));

        // Probintzia zutabearen edizioa konfiguratzen du
        tableColumnProbintzia.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnProbintzia.setOnEditCommit(t -> t.getRowValue().setProbintzia(t.getNewValue()));
    }

    /**
     * Taulan erakusten diren datuak eguneratzen ditu aukeratutako orriaren arabera
     * @param pageIndex momentuan zauden orriaren indizea
     */
    private void updateTableView(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE; // Hasierako indizea
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, herrienObservableLista.size()); // Amaierako indizea

        // Taulan erakusten diren elementuak eguneratzen ditu
        tableViewHerriak.setItems(FXCollections.observableArrayList(herrienObservableLista.subList(fromIndex, toIndex)));
    }


    /**
     * Herria kostakoa bada, hondartzen izenak lortzen ditu
     * @param herria Herria motako objektua
     * @param index hondartzaren indizea (0, 1 edo 2)
     * @return Hondartzaren izena edo hutsik dagoen String bat hondartzarik ez badago
     */
    private String getHondartza(Herria herria, int index) {
        if (herria instanceof KostakoHerria) { // herria kostakoa den begiratzen du
            String[] hondartzak = ((KostakoHerria) herria).getHondartzak(); // hondartzen arraya lortzen du
            return hondartzak.length > index ? hondartzak[index] : ""; // hondartza edo hutsik itzultzen du
        }
        return ""; // kostako herria ez bada hutsik
    }

    @FXML
    void handleAtzera() throws IOException {
        App.setRoot("Nagusia");
    }
}
