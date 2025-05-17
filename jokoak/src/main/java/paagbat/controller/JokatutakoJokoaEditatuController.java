package paagbat.controller;

import javafx.util.StringConverter;

import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import paagbat.App;
import paagbat.model.base.JokatutakoJokoa;
import paagbat.model.enumerazioa.Egoera;

public class JokatutakoJokoaEditatuController {

    private JokatutakoJokoa jokoa;
    private Runnable onUpdateCallback;

    @FXML
    private TextField orduak;

    @FXML
    private ComboBox<Egoera> comboEgoera;

    @FXML
    private Text jokoTitulu;

    @FXML
    private Button itxiBotoia;

    @FXML
    private Button btnAldatu;

    @FXML
    private Button btnEzabatu;

    @FXML
    private void initialize() {
        comboEgoera.setItems(FXCollections.observableArrayList(Egoera.values()));

        comboEgoera.setConverter(new StringConverter<Egoera>() {
            @Override
            public String toString(Egoera egon) {
                return egon != null ? egon.toString() : "";
            }

            @Override
            public Egoera fromString(String string) {
                for (Egoera p : Egoera.values()) {
                    if (p.toString().equalsIgnoreCase(string)) {
                        return p;
                    }
                }
                return null;
            }
        });

    }

    /**
     * Aukeratutak jokoaren datuak agertzeko
     * 
     * @param jokoa aukeratutako jokoa
     */
    public void setJokoa(JokatutakoJokoa jokoa) {
        this.jokoa = jokoa;

        jokoTitulu.setText(jokoa.getTitulua());
        orduak.setText(String.valueOf(jokoa.getJokatutakoOrduak()));
        comboEgoera.setValue(jokoa.getEgoera());
    }

    /**
     * Jokoan egindako aldaketak gordetzeko
     * 
     * @throws SQLException
     */
    @FXML
    private void aldaketakGorde() throws SQLException {
        int jokatuOrduak = Integer.parseInt(orduak.getText());
        Egoera egoera_berri = comboEgoera.getValue();

        this.jokoa.setEgoera(egoera_berri);
        this.jokoa.setJokatutakoOrduak(jokatuOrduak);

        boolean eguneratu = App.db.jokatutakoaEguneratu(jokoa);

        if (eguneratu) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Eguneratua");
            alert.setHeaderText(null);
            alert.setContentText("Jokoa ondo eguneratu da.");
            alert.showAndWait();

            // Callback-a deitzeko definituta badago
            if (onUpdateCallback != null) {
                onUpdateCallback.run();
            }

            itxiLehioa();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errorea");
            alert.setHeaderText(null);
            alert.setContentText("Erroreren bat gertatu da jokoa eguneratzerakoan.");
            alert.showAndWait();
        }
    }

    /**
     * Aukeratutako jokoa ezabatzeko
     * 
     * @throws SQLException
     */
    public void ezabatu() throws SQLException {
        if (jokoa != null && jokoa.getNireListaId() != 0) {
            // Baieztapen alerta
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Baieztapena");
            alert.setHeaderText("Benetan jolasa zure zerrendatik ezabatu nahi duzu?");
            alert.setContentText("Akzio hau ezingo da atzera itzuli.");

            // Alertaren botoiak
            ButtonType ezabatuBtn = new ButtonType("Ezabatu");
            ButtonType utziBtn = new ButtonType("Utzi", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(ezabatuBtn, utziBtn);

            // Alerta erakutzi eta erabiltzailearen aukera itzaron
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ezabatuBtn) {
                // Erabiltzaileak ezabatzen badu
                try {
                    // Jokoa ezabatzen dugu
                    App.db.ezabatuNireJokoa(jokoa.getNireListaId());

                    // Success alert
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Ondo");
                    successAlert.setHeaderText("Jolasa ezabatuta");
                    successAlert.setContentText("Jolasa zuzen ezabatu da zure zerrendatik.");
                    successAlert.showAndWait();

                    // Callback-a deitzeko definituta badago
                    if (onUpdateCallback != null) {
                        onUpdateCallback.run();
                    }

                    itxiLehioa();
                } catch (SQLException e) {
                    // Erroreren bat gertatzen bada
                    e.printStackTrace();
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Errorea");
                    errorAlert.setHeaderText("Ezin izan da jolasa ezabatu");
                    errorAlert.setContentText("Arazoren bat gertatu da jolasa zure zerrendatik ezabatzerakoan.");
                    errorAlert.showAndWait();
                }
            } else {

                System.out.println("Ezabatu hautatu ez da.");
            }
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

    /**
     * Callback-a egiteko
     * 
     * @param callback
     */
    public void setOnUpdateCallback(Runnable callback) {
        this.onUpdateCallback = callback;
    }

}
