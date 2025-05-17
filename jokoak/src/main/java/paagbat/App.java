package paagbat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import paagbat.model.DBKonektatu;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static DBKonektatu db = new DBKonektatu();

    public static Stage stageTotal;
    private static Scene scene;
    private static double xOffset = 0;
    private static double yOffset = 0;
    public static boolean gogoratu = false;
    public static String erabiltzailea = null;

    @Override
    public void start(Stage stage) throws IOException {
        stageTotal = stage;
        scene = new Scene(loadFXML("Login"), 980, 680);
        stage.setScene(scene);
        stage.getIcons().add(new Image("File:jokoak\\src\\main\\resources\\paagbat\\img\\GameHiveLogo.png"));
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        lehioaMugitu(stageTotal, scene);
        stage.show();
    }

    private static void lehioaMugitu(Stage stage, Scene scene) {
        scene.setOnMousePressed(MouseEvent -> {
            xOffset = MouseEvent.getSceneX();
            yOffset = MouseEvent.getSceneY();
        });

        scene.setOnMouseDragged(MouseEvent -> {
            stage.setX(MouseEvent.getScreenX() - xOffset);
            stage.setY(MouseEvent.getScreenY() - yOffset);
        });
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setFullScreen(boolean fullscreen) {
        stageTotal.setFullScreen(fullscreen);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/paagbat/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
