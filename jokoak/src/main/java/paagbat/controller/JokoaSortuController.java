package paagbat.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import javafx.util.StringConverter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import paagbat.App;
import paagbat.model.DBKonektatu;
import paagbat.model.base.Jokoa;
import paagbat.model.enumerazioa.Plataforma;

public class JokoaSortuController {

    @FXML
    private TextField titulu_berri;

    @FXML
    private TextField garatzailea;

    @FXML
    private TextField generoa;

    @FXML
    private TextField pegi;

    @FXML
    private ComboBox<Plataforma> comboPlataforma;

    @FXML
    private TextField irteeraData;

    @FXML
    private TextArea deskribapena;

    @FXML
    private TextField prezio;

    @FXML
    private TextField trailerra;

    @FXML
    private Text irudiIzen;

    @FXML
    private Button itxiBotoia;

    private File aukeratutakoIrudia;

    @FXML
    private void initialize() {
        garbitu();
        comboPlataforma.setItems(FXCollections.observableArrayList(Plataforma.values()));

        comboPlataforma.setConverter(new StringConverter<Plataforma>() {
            @Override
            public String toString(Plataforma plataforma) {
                return plataforma != null ? plataforma.getIzena() : "";
            }
        
            @Override
            public Plataforma fromString(String string) {
                for (Plataforma p : Plataforma.values()) {
                    if (p.getIzena().equalsIgnoreCase(string)) {
                        return p;
                    }
                }
                return null;
            }
        });
        
    }

    /**
     * Joko berri bat datu basera sartzeko
     */
    @FXML
    private void sortuJokoa() {
        String titulua = titulu_berri.getText().strip();
        String garatzaile = garatzailea.getText().strip();
        String genero = generoa.getText().strip();

        Plataforma plataformEnum = comboPlataforma.getValue();
        String plataform = plataformEnum.getIzena();

        String pegiZen = pegi.getText().strip();
        String data = irteeraData.getText().strip();
        String dirua = prezio.getText().strip();
        String trailer = trailerra.getText().strip();
        String deskribapen = deskribapena.getText().strip();

        if (titulua.isEmpty() || garatzaile.isEmpty() || genero.isEmpty() || pegiZen.isEmpty()
                || plataform.isEmpty() || data.isEmpty() || dirua.isEmpty()) {
            erakutziAlert("Errorea", "Eremu guztiak bete behar dira!");
            return;
        }

        if (aukeratutakoIrudia == null) {
            erakutziAlert("Errorea", "Mesedez, aukeratu irudi bat lehenik.");
            return;
        }

        try {
            int pegiZenb = Integer.parseInt(pegiZen);
            double prezioJoko = Double.parseDouble(dirua);

            // Extensioa lortu
            String extentzioa = lortuFitxategiExtenzioa(aukeratutakoIrudia.getName());

            if (extentzioa == null) {
                erakutziAlert("Errorea", "Fitxategiaren extenzioa ez da egokia.");
                return;
            }

            String plataformaIzena = plataformEnum.name();

            // Irudiari jokoaren tituluaren izen berdina jarri
            String fitxategiIzena = izenaGarbitu(titulua) + "_" + plataformaIzena + "." + extentzioa;

            // Irudia nahi dugun karpetan gorde
            File helmuga = new File(DBKonektatu.JOKOAK_RUTA + fitxategiIzena);
            Files.copy(aukeratutakoIrudia.toPath(), helmuga.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Aurreko irudiak ezabatu (Ezabatu aurreko irudiak)
            ezabatuAurrekoIrudiak(titulua, plataformEnum);

            // Jokoa sortu eta datu basean gorde bere datuak
            Jokoa berria = new Jokoa(titulua, genero, garatzaile, deskribapen, plataformEnum, pegiZenb, data, prezioJoko,
                    fitxategiIzena, trailer);

            App.db.jokoaSortu(berria);

            erakutziAlert("Ondo", "Jokoa ondo gorde da!");
            garbitu();

        } catch (NumberFormatException e) {
            erakutziAlert("Errorea", "PEGI edo prezioa ez dira zenbaki egokiak.");
        } catch (IOException e) {
            erakutziAlert("Errorea", "Ezin izan da irudia gorde: " + e.getMessage());
        } catch (SQLException e) {
            erakutziAlert("Errorea", "Ezin izan da jokoa gorde datu-basean: " + e.getMessage());
        }
    }

    public static String izenaGarbitu(String fitxategiIzena) {
        return fitxategiIzena.replaceAll("[\\\\/:*?\"<>|]", "_")  // Caracteres especiales
                     .replaceAll("\\s+", "_");             // Espacios
    }
    
    /**
     * Irudia aukeratzeko
     */
    @FXML
    private void irudiaAukeratu() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Aukeratu jokoaren irudia");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Irudiak", "*.png", "*.jpg", "*.jpeg"));
        File hautatua = fileChooser.showOpenDialog(null);

        if (hautatua != null) {
            String extension = lortuFitxategiExtenzioa(hautatua.getName());
            if (extension == null) {
                erakutziAlert("Errorea", "Fitxategi mota ez da egokia.");
                return;
            }

            aukeratutakoIrudia = hautatua;
            erakutziAlert("Ondo", "Irudia aukeratuta: " + hautatua.getName());
            irudiIzen.setText("Ondo");
        } else {
            irudiIzen.setText("Errorea");
        }
    }

    /**
     * Fitxategiaren extentzioa lortzeko
     * @param fitxategia fitxategiaren izena
     * @return
     */
    private String lortuFitxategiExtenzioa(String fitxategia) {
        int i = fitxategia.lastIndexOf('.');
        if (i > 0) {
            return fitxategia.substring(i + 1).toLowerCase();
        }
        return null;
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
     * Datuak garbitzeko
     */
    public void garbitu() {
        titulu_berri.clear();
        garatzailea.clear();
        generoa.clear();
        pegi.clear();
        irteeraData.clear();
        prezio.clear();
        irudiIzen.setVisible(false);
        deskribapena.clear();
        comboPlataforma.getSelectionModel().clearSelection();
        trailerra.clear();
    }

    /**
     * Lehioak izteko
     */
    @FXML
    private void itxiLehioa() {
        Stage stage = (Stage) itxiBotoia.getScene().getWindow();
        stage.close();
    }

    /**
     * Aurretik irudia baldin badago ezabatzeko, irudiak jokoaren izenarekin eta plataformarekin gordetzen dira
     * @param titulua ezabatuko den irudiaren izena
     * @param plataformEnum ezabatuko den irudiaren plataforma
     */
    private void ezabatuAurrekoIrudiak(String titulua, Plataforma plataformEnum) {
        String[] formatuak = { "png", "jpg", "jpeg" };

        for (String ext : formatuak) {
            File f = new File(DBKonektatu.JOKOAK_RUTA + titulua.replaceAll("\\s+", "_") + "_" + plataformEnum.name() + "." + ext);
            if (f.exists())
                f.delete();
        }
    }
}
