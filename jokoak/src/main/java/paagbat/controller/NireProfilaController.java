package paagbat.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.imageio.ImageIO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import paagbat.App;
import paagbat.model.DBKonektatu;
import paagbat.model.base.Erabiltzailea;
import paagbat.model.base.Sesioa;

public class NireProfilaController {
    private Erabiltzailea erabiltzailea;

    @FXML
    private HBox erabilBox;

    @FXML
    private Text erabilIzen;

    @FXML
    private Text gmail;

    @FXML
    private ImageView imgProfil;

    @FXML
    private Circle circleBorder;

    @FXML
    private HBox nire_listak;

    @FXML
    private VBox subListak;

    private boolean desplegado = false;

    @FXML
    private void initialize() {
        this.erabiltzailea = Sesioa.getErabiltzailea();
        if (Sesioa.isAdmin()) {
            erabilBox.setVisible(true);
        } else {
            erabilBox.setVisible(false);
        }

        String irudia = DBKonektatu.IMG_RUTA + Sesioa.getProfilIrudia();
        Image img = new Image(new File(irudia).toURI().toString());
        imgProfil.setImage(img);

        imgProfil.imageProperty().addListener((obs, oldImg, newImg) -> {
            irudiaZirkularra();
        });

        irudiaZirkularra();

        erabilIzen.setText("Username: " + Sesioa.getErabiltzaileIzen());
        gmail.setText("Email: " + Sesioa.getErabiltzaileEmail());

        nire_listak.setOnMouseClicked(event -> toggleListak());
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
     * Bertan agertzen den irudia zirkularra izateko
     */
    private void irudiaZirkularra() {
        if (imgProfil.getImage() != null) {
            double radius = Math.min(imgProfil.getFitWidth(), imgProfil.getFitHeight()) / 2;
            Circle clip = new Circle(radius, radius, radius);
            imgProfil.setClip(clip);

            circleBorder.setCenterX(radius);
            circleBorder.setCenterY(radius);
            circleBorder.setRadius(radius);
        }
    }

    /**
     * Profileko irudia eguneratzeko
     * @param event
     */
    @FXML
    public void irudiaEguneratu(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Aukeratu profilerako irudia");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Irudiak", "*.png", "*.jpg", "*.jpeg"));

        File aukeratutakoa = fileChooser.showOpenDialog(null);

        if (aukeratutakoa != null) {
            try {
                String extensioa = lortuFitxategiExtenzioa(aukeratutakoa.getName());
                if (extensioa == null) {
                    erakutziAlert("Errorea",
                            "Fitxategi mota ez da baliozkoa. PNG, JPEG edo JPG motako fitxategia aukeratu.");
                    return;
                }

                Erabiltzailea erabiltzaile = Sesioa.getErabiltzailea();
                String izena = erabiltzaile.getErabiltzaileIzena() + "_" + erabiltzaile.getId();
                String fitxategiBerria = izena + ".png";

                // Ezabatu aurreko irudi guztiak (beste formatuetan badaude)
                ezabatuAurrekoIrudiak(erabiltzaile.getErabiltzaileIzena(), erabiltzaile.getId());

                // Gorde .png formatuan
                gordePNGn(aukeratutakoa, fitxategiBerria);

                // DB eguneratu eta irudia bistaratu
                App.db.eguneratuIrudia(erabiltzaile.getErabiltzaileIzena(), fitxategiBerria);
                erabiltzaile.setProfilIrudi(fitxategiBerria);

                Image irudiBerri = new Image(new File(DBKonektatu.IMG_RUTA + fitxategiBerria).toURI().toString());
                imgProfil.setImage(irudiBerri);

                erakutziAlert("Arrakasta", "Irudia ondo eguneratu da.");
            } catch (IOException e) {
                e.printStackTrace();
                erakutziAlert("Errorea", "Ezin izan da irudia gorde.");
            }
        }
    }

    /**
     * Irudia beti png formatoan gordetzeko
     * @param irudiOriginala irudiaren fitxategia
     * @param fitxategiBerria fitxategiak edukiko duen izena
     * @throws IOException
     */
    private void gordePNGn(File irudiOriginala, String fitxategiBerria) throws IOException {
        BufferedImage irudia = ImageIO.read(irudiOriginala);
        File irudiPNG = new File(DBKonektatu.IMG_RUTA + fitxategiBerria);
        ImageIO.write(irudia, "png", irudiPNG);
    }

    /**
     * Aurretik zueden irutiak ezabatzeko
     * @param erabiltzaileIzena sesioko erabiltzailearen izena
     * @param id sesioko erabiltzailearen id-a
     */
    private void ezabatuAurrekoIrudiak(String erabiltzaileIzena, int id) {
        String[] formatuak = { "png", "jpg", "jpeg" };

        for (String ext : formatuak) {
            File f = new File(DBKonektatu.IMG_RUTA + erabiltzaileIzena + "_" + id + "." + ext);
            if (f.exists())
                f.delete();
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
     * Pasahitza aldatzeko lehioa irekitzeko
     * @throws IOException
     */
    @FXML
    private void pasahitzaAldatu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/paagbat/fxml/PasahitzaAldatu.fxml"));
        Parent root = loader.load();

        Stage newStage = new Stage();
        newStage.setTitle("Pasahitza aldatu");
        newStage.setScene(new Scene(root));
        newStage.setWidth(400);
        newStage.setHeight(350);

        newStage.showAndWait();
    }

    /**
     * Sesioko erabiltzailearen datuak aldatzeko
     * @throws IOException
     */
    @FXML
    private void datuakAldatu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/paagbat/fxml/DatuakAldatu.fxml"));
        Parent root = loader.load();

        DatuakAldatuController controller = loader.getController();

        if (erabiltzailea != null) {
            controller.setErabiltzailea(erabiltzailea);
        } else {
            System.out.println("ERROREA: erabiltzailea null da");
        }

        Stage newStage = new Stage();
        newStage.setTitle("Datuak aldatu");
        newStage.setScene(new Scene(root));
        newStage.setWidth(400);
        newStage.setHeight(350);

        newStage.showAndWait();
    }

    @FXML
    private void jokoetara() throws IOException {
        App.setRoot("Jokoak");
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
        }
    }

    /**
     * Aplikazioaren lehioa txikitzeko itxi barik
     */
    @FXML
    private void txikitu() {
        App.stageTotal.setIconified(true);
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
}
