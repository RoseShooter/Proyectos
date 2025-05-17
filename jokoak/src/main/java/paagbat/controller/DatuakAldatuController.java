package paagbat.controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import paagbat.App;
import paagbat.model.base.Erabiltzailea;
import paagbat.model.base.Sesioa;

public class DatuakAldatuController {

    private Erabiltzailea erabiltzailea;

    @FXML
    private TextField username;

    @FXML
    private TextField gmail;

    @FXML
    private Button itxiBotoia;

    @FXML
    private void initialize() {

        
    }

    /**
     * Sesioko erabiltzailearen datuak
     * @param erabiltzailea
     */
    public void setErabiltzailea(Erabiltzailea erabiltzailea){
        this.erabiltzailea = erabiltzailea;

        username.setText(erabiltzailea.getErabiltzaileIzena());
        gmail.setText(erabiltzailea.getEmail());
    }

    /**
     * Egindako aldaketak gordetzeko
     * @throws SQLException
     */
    @FXML
    private void aldaketakGorde() throws SQLException{
        String erabilIzen = username.getText().strip();
        String email_berri = gmail.getText().strip();

        this.erabiltzailea.setErabiltzaileIzena(erabilIzen);
        this.erabiltzailea.setEmail(email_berri);

        boolean eguneratu = App.db.eguneratuErabiltzaileDatuak(erabiltzailea);

        if (eguneratu) {
            Sesioa.setErabiltzailea(erabiltzailea);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Eguneratua");
            alert.setHeaderText(null);
            alert.setContentText("Datuak ondo eguneratu da.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errorea");
            alert.setHeaderText(null);
            alert.setContentText("Erroreren bat gertatu da datuak eguneratzerakoan.");
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
