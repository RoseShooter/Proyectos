package paagbat.controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import paagbat.App;
import paagbat.model.base.Erabiltzailea;
import paagbat.model.base.Sesioa;

public class PasahitzaAldatuController {

    @FXML
    private PasswordField passZaharra;

    @FXML
    private PasswordField passBerria1;

    @FXML
    private PasswordField passBerria2;

    @FXML
    private Button itxiBotoia;

    @FXML
    private void initialize() {

    }

    /**
     * Pasahitza aldatzeko
     * @throws SQLException
     */
    @FXML
    private void pasahitzaAldatu() throws SQLException {
        String zaharra = passZaharra.getText().strip();
        String passBerria = passBerria1.getText().strip();
        String pass_berria = passBerria2.getText().strip();

        Erabiltzailea e = Sesioa.getErabiltzailea();

        if (!App.db.pasahitzaEgiaztatu(e.getErabiltzaileIzena(), zaharra)) {
            erakutziAlert("Errorea", "Pasahitza zaharra ez da zuzena.");
            return;
        }

        if (!passBerria.equals(pass_berria)) {
            erakutziAlert("Errorea", "Pasahitz berriak ez datoz bat.");
            return;
        }

        if (!pasahitzaBalio(pass_berria)) {
            erakutziAlert("Errorea", "Pasahitzak minimo 8 karaktere izan behar ditu eta zenbaki bat gutxienez.");
            return;
        }

        App.db.eguneratuPasahitza(e.getErabiltzaileIzena(), passBerria);
        erakutziAlert("Ondo", "Pasahitza ondo eguneratu da.");
        itxiLehioa();

    }

    /**
     * Pasahitza konprobatzeko
     * @param pasahitza
     * @return
     */
    private boolean pasahitzaBalio(String pasahitza) {
        if (pasahitza.length() < 8) {
            return false;
        }

        return pasahitza.matches(".*\\d.*");
    }

    /**
     * Alertak modu azkarrako batean sortzeko
     * 
     * @param titulua     alertare titulua
     * @param deskripzioa alertaren barruko textua
     */
    private void erakutziAlert(String titulua, String deskripzioa) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulua);
        alert.setContentText(deskripzioa);
        alert.showAndWait();
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
