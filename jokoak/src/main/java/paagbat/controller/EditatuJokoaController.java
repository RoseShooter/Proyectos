package paagbat.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import paagbat.App;
import paagbat.model.DBKonektatu;
import paagbat.model.base.Jokoa;
import paagbat.model.enumerazioa.Plataforma;

public class EditatuJokoaController {
    private Jokoa joko;
    private Runnable onUpdateCallback;

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
    private ImageView jokoIrudia;

    @FXML
    private Button itxiBotoia;

    private File aukeratutakoIrudia;

    @FXML
    private void initialize() {
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
     * Aurreko lehioan aukeratu den jokoaren datu guztiak erakusteko
     * 
     * @param joko erakutsiko den jokoa
     */
    public void setJokoa(Jokoa joko) {
        this.joko = joko;

        titulu_berri.setText(joko.getTitulua());
        garatzailea.setText(joko.getGaratzailea());
        generoa.setText(joko.getGeneroa());
        comboPlataforma.setValue(joko.getPlataforma());
        pegi.setText(Integer.toString(joko.getPegi()));
        prezio.setText(Double.toString(joko.getPrezioa()));
        irteeraData.setText(joko.getIrteeraData());
        deskribapena.setText(joko.getDeskribapena());
        trailerra.setText(joko.getTrailer());

        String fitxategiIzena = joko.getIrudia();
        String irudiPath = DBKonektatu.JOKOAK_RUTA + fitxategiIzena;

        File file = new File(irudiPath);
        if (file.exists()) {
            Image irudia = new Image(file.toURI().toString());
            jokoIrudia.setImage(irudia);

            jokoIrudia.setFitHeight(440);
            jokoIrudia.setFitWidth(410);
            jokoIrudia.setPreserveRatio(true);
            jokoIrudia.setSmooth(true);
        } else {
            erakutziAlert("Errorea", "Ezin izan da irudia aurkitu.");
        }

    }

    // Irudi berria png moduan gordetzeko
    private void gordeIrudia(File irudiOriginala, String fitxategiBerria) throws IOException {
        // Irudi berria irakurtzeko
        BufferedImage irudia = ImageIO.read(irudiOriginala);
    
        // Irudia gordetzeko ruta
        File irudiPNG = new File(DBKonektatu.JOKOAK_RUTA + fitxategiBerria);
    
        // Irudia png moduan gordetzeko
        ImageIO.write(irudia, "PNG", irudiPNG);
    }
    
    /**
     * Egindako aldaketak gordetzeko
     * @throws IOException
     */
    @FXML
    private void gordeAldaketak() throws IOException {
        try {
            String titulua = titulu_berri.getText();
            String garatzaile = garatzailea.getText();
            String generoText = generoa.getText();
            Plataforma plat = comboPlataforma.getValue();
            int pegiZen = Integer.parseInt(pegi.getText());
            String data = irteeraData.getText();
            String description = deskribapena.getText();
            double prezioEdit = Double.parseDouble(prezio.getText());
            String trailerUrl = trailerra.getText();
    
            // Datuak balidatzeko
            if (titulua.isEmpty() || garatzaile.isEmpty() || generoText.isEmpty() || plat == null) {
                erakutziAlert("Errorea", "Datu guztiak betetzen saiatu behar duzu.");
                return;
            }
    
            // Jokoaren datu berriak jarri
            joko.setTitulua(titulua);
            joko.setGaratzailea(garatzaile);
            joko.setGeneroa(generoText);
            joko.setPlataforma(plat);
            joko.setPegi(pegiZen);
            joko.setIrteeraData(data);
            joko.setDeskribapena(description);
            joko.setPrezioa(prezioEdit);
            joko.setTrailer(trailerUrl);
    
            if (aukeratutakoIrudia == null) {
                erakutziAlert("Errorea", "Mesedez, aukeratu irudi bat lehenik.");
                return;
            }
    
            // Aukeratutako irudiaren extentzioa lortzeko
            String extentzioa = lortuFitxategiExtenzioa(aukeratutakoIrudia.getName());
    
            if (extentzioa == null) {
                erakutziAlert("Errorea", "Fitxategiaren extenzioa ez da egokia.");
                return;
            }
    
            // Aurretik irudia existitzen bada ezabatzeko
            ezabatuAurrekoIrudiak(titulua.replaceAll("\\s+", "_"), joko.getPlataforma());
    
            // Fitxategiarten izena jartzeko, beti png moduan
            String fitxategiIzena = titulua.replaceAll("\\s+", "_") + "_" + plat + ".png";
    
            // Irudi berria png moduan gordetzeko
            gordeIrudia(aukeratutakoIrudia, fitxategiIzena);
    
            // Irudiaren izen berria gordetzeko
            joko.setIrudia(fitxategiIzena);
    
            // Jokoa datu basean eguneratzeko
            App.db.jokoaEguneratu(joko);
            erakutziAlert("Arrakasta", "Jokoa eguneratuta izan da.");
    
            // Callback-a deitzeko definituta badago
            if (onUpdateCallback != null) {
                onUpdateCallback.run();
            }
    
            // Lehioa izteko
            itxiLehioa();
    
        } catch (NumberFormatException e) {
            erakutziAlert("Errorea", "Zifra ez egokia.");
        } catch (SQLException e) {
            erakutziAlert("Errorea", "Datu-basearekin arazo bat egon da.");
            e.printStackTrace();
        }
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

    /**
     * Callback-a egiteko
     * @param callback
     */
    public void setOnUpdateCallback(Runnable callback) {
        this.onUpdateCallback = callback;
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
        }
    }

    /**
     * Aukeratutako irudiaren extentzioa lortzeko
     * @param fitxategia aukeratutako irudia
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
     * Alertak sortzeko modu errazago batean
     * @param titulua     alertaren titulua
     * @param deskripzioa alertaren gorputzean doan textua
     */
    private void erakutziAlert(String titulua, String deskripzioa) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulua);
        alert.setContentText(deskripzioa);
        alert.showAndWait();
    }

    /**
     * Lehioa itxi eta aurrekora itzultzeko
     */
    @FXML
    private void itxiLehioa() {
        Stage stage = (Stage) itxiBotoia.getScene().getWindow();
        stage.close();
    }

}
