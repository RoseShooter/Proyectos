package paagbat.controller;

import javafx.util.StringConverter;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import paagbat.App;
import paagbat.model.base.Erabiltzailea;
import paagbat.model.enumerazioa.Rola;

public class ErabiltzaileaEditatuController {

    private Erabiltzailea erabiltzailea;

    @FXML
    private TextField username;

    @FXML
    private TextField gmail;

    @FXML
    private ComboBox<Rola> comboRola;


    @FXML
    private Button itxiBotoia;

    @FXML
    private void initialize() {
        comboRola.setItems(FXCollections.observableArrayList(Rola.values()));

        comboRola.setConverter(new StringConverter<Rola>() {
            @Override
            public String toString(Rola rol) {
                return rol != null ? rol.toString() : "";
            }
        
            @Override
            public Rola fromString(String string) {
                for (Rola p : Rola.values()) {
                    if (p.toString().equalsIgnoreCase(string)) {
                        return p;
                    }
                }
                return null;
            }
        });
        
    }

    /**
     * Aukeratutako erabiltzailearen datuak erakusteko
     * @param erabiltzailea
     */
    public void setErabiltzailea(Erabiltzailea erabiltzailea){
        this.erabiltzailea = erabiltzailea;

        username.setText(erabiltzailea.getErabiltzaileIzena());
        gmail.setText(erabiltzailea.getEmail());
        comboRola.setValue(erabiltzailea.getRola());
    }

    /**
     * Egindako aldaketak gordetzeko
     * @throws SQLException
     */
    @FXML
    private void aldaketakGorde() throws SQLException{
        String erabilIzen = username.getText().strip();
        String email_berri = gmail.getText().strip();
        Rola rol_berri = comboRola.getValue();

        this.erabiltzailea.setErabiltzaileIzena(erabilIzen);
        this.erabiltzailea.setEmail(email_berri);
        this.erabiltzailea.setRola(rol_berri);

        boolean eguneratu = App.db.erabiltzaileaEguneratu(erabiltzailea);

        if (eguneratu) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Eguneratua");
            alert.setHeaderText(null);
            alert.setContentText("Erabiltzailea ondo eguneratu da.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errorea");
            alert.setHeaderText(null);
            alert.setContentText("Erroreren bat gertatu da erabiltzailea eguneratzerakoan.");
            alert.showAndWait();
        }
    }

    /**
     * Lehioa izteko
     */
    @FXML
    private void itxiLehioa() {
        Stage stage = (Stage) itxiBotoia.getScene().getWindow();
        stage.close();
    }

}
