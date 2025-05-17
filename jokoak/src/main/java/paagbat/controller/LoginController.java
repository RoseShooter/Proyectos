package paagbat.controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import paagbat.App;
import paagbat.model.base.Erabiltzailea;

public class LoginController {

    @FXML
    private TextField erabiltzaileData;

    @FXML
    private PasswordField pasahitzaData;

    @FXML
    private TextField erabi_berri;

    @FXML
    private PasswordField pasahitza1;

    @FXML
    private PasswordField pasahitza2;

    @FXML
    private TextField email;

    @FXML
    private Text login_error;

    @FXML
    private Text erregistro_error;

    @FXML
    private Pane login;

    @FXML
    private Pane erregistro;

    @FXML
    private ImageView logo;

    @FXML
    private CheckBox gogoratu;

    @FXML
    private void initialize() {
        System.out.println("LoginController has been initialized.");
        if(App.gogoratu){
            System.out.println("Gogoratu checkbox is selected.");
            System.out.println("Erabiltzailea: " + App.erabiltzailea);
            erabiltzaileData.setText(App.erabiltzailea);
        }
    }

    /**
     * Aplikazioa ixteko
     */
    @FXML
    private void itxi() {
        System.exit(0);
    }

    /**
     * Aplikazioaren lehioa txikitzeko itxi barik
     */
    @FXML
    private void txikitu() {
        App.stageTotal.setIconified(true);
    }

    /**
     * Erregistroaren panela erakusteko klik egiterakoan eta login panela
     * desagertzeko
     * 
     * @param event saguarekin klik egiterakoan
     */
    @FXML
    private void erregistrora(MouseEvent event) {
        // Login-a desagertzeko transizioa
        FadeTransition desagertu = new FadeTransition(Duration.millis(400), login);
        desagertu.setFromValue(1.0);
        desagertu.setToValue(0.0);

        // Erregistroa agertzeko transizioa
        FadeTransition agertu = new FadeTransition(Duration.millis(400), erregistro);
        agertu.setFromValue(0.0);
        agertu.setToValue(1.0);

        // Login-a desagertu denean erregistroa agertzeko
        desagertu.setOnFinished(e -> {
            login.setVisible(false); // Login desagertuta
            erregistro.setVisible(true); // Erregistroa agertu
            agertu.play(); // Erregistroa ikusiteko transizioa hasi
        });

        desagertu.play(); // Login-aren transizioa hasten da
        loginGarbitu();
    }

    /**
     * Login-aren panela erakisteko klik egiterakoan eta erregistro panela
     * desagertzeko
     * 
     * @param event
     */
    @FXML
    private void logina(MouseEvent event) {
        // Erregistroa desagertzeko transizioa
        FadeTransition desagertu = new FadeTransition(Duration.millis(500), erregistro);
        desagertu.setFromValue(1.0);
        desagertu.setToValue(0.0);

        // Login-a agertzeko transizioa
        FadeTransition agertu = new FadeTransition(Duration.millis(500), login);
        agertu.setFromValue(0.0);
        agertu.setToValue(1.0);

        // Erregistroa desagertu denean erregistroa agertzeko
        desagertu.setOnFinished(e -> {
            erregistro.setVisible(false); // Erregistroa desagertuta
            login.setVisible(true); // Login-a agertu
            agertu.play(); // Login-a ikusiteko transizioa hasi
        });

        desagertu.play(); // Erregistroaren transizioa hasten da
        erregistroaGarbitu();
    }

    /**
     * Sesioa hasteko
     */
    @FXML
    private void login() {
        try {
            String erabiltzailea = erabiltzaileData.getText().toString();
            String pasahitza = pasahitzaData.getText().toString();

            if (erabiltzailea.isEmpty() || pasahitza.isEmpty()) {
                login_error.setText("Erabiltzailea eta pasahitza sartu behar dira.");
                login_error.setVisible(true);
                return;
            }

            boolean egiaztatu = App.db.erabiltzaileaEgiaztatu(erabiltzailea, pasahitza);

            if (egiaztatu) {
                login_error.setVisible(false);

                boolean konprobatu = App.db.erabiltzaileaEgiaztatu(erabiltzailea, pasahitza);
                if (konprobatu) {
                    if (gogoratu.isSelected()) {
                        App.gogoratu = true;
                    } else {
                        App.gogoratu = false;
                    }
                }
                App.erabiltzailea = erabiltzailea; 
                
                System.out.println(App.gogoratu);
                App.setRoot("Hasiera");
                App.stageTotal.setHeight(720);
                App.stageTotal.setWidth(1080);
            } else {
                login_error.setText("Erabiltzailea edo pasahitza okerrak dira.");
                login_error.setVisible(true);
                loginGarbitu();
            }

        } catch (Exception e) {
            e.printStackTrace();
            login_error.setText("Errore bat gertatu da login prozesuan");
            login_error.setVisible(true);
            loginGarbitu();
        }

    }

    /**
     * Erabiltzaile berria sortzeko
     */
    @FXML
    private void erregistroa() {
        try {
            String erabiltzailea = erabi_berri.getText().toString();
            String pasahitz = pasahitza1.getText().toString();
            String pasahitza = pasahitza2.getText().toString();
            String gmail = email.getText().toString();
    
            // Ezer hutsik ez egoteko
            if (erabiltzailea.isEmpty() || pasahitz.isEmpty() || pasahitza.isEmpty() || gmail.isEmpty()) {
                erregistro_error.setText("Datu guztiak bete behar dira.");
                erregistro_error.setVisible(true);
                return;
            }
    
            // Email-eko balidazioa basikoa
            if (!gmail.matches(".+@.+\\..+")) {
                erregistro_error.setText("Email-a ez da baliozkoa (formatoa: erabiltzailea@dominio.ext)");
                erregistro_error.setVisible(true);
                return;
            }
    
            // Pasahitzak berdinak izatearen balidazioa
            if (!pasahitz.equals(pasahitza)) {
                erregistro_error.setText("Pasahitza berdina izan behar da");
                erregistro_error.setVisible(true);
                return;
            }
    
            // Pasahitzaren luzeera eta zailtasunaren balidazioa
            if (!pasahitzaBalio(pasahitz)) {
                erregistro_error.setText("Gutzienez 8 karaktere, horietako bat zenbakia");
                erregistro_error.setVisible(true);
                return;
            }
    
            Erabiltzailea e = new Erabiltzailea(erabiltzailea, gmail, pasahitza);
    
            if (App.db.gehituErabiltzailea(e)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erregistroa");
                alert.setHeaderText(null);
                alert.setContentText(e.getErabiltzaileIzena() + " erabiltzailea ondo sortu da.");
                alert.showAndWait();
                erregistroaGarbitu();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erregistroa");
                alert.setHeaderText(null);
                alert.setContentText("Errorea erabiltzailea sortzerakoan.");
                alert.showAndWait();
                erregistroaGarbitu();
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * Datuak garbitzeko
     */
    private void loginGarbitu() {
        erabiltzaileData.clear();
        pasahitzaData.clear();
    }

    /**
     * Datuak garbitzeko
     */
    private void erregistroaGarbitu() {
        erabi_berri.clear();
        pasahitza1.clear();
        pasahitza2.clear();
        email.clear();
    }

}
