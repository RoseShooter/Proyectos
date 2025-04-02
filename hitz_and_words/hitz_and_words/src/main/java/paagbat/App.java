package paagbat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.input.KeyCombination;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML("/fxml/mainMenu");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true); // Pantaia osoa
        stage.setFullScreenExitHint(""); // Irtetzeko mezua eskutatu
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); // Erabiltzaileak Esc ematerakoan ez ateratzeko pantaia osotik
        stage.show();
        scene.getStylesheets().add(getClass().getResource("/css/Modena.css").toExternalForm());

        Image logoIrudi = new Image(getClass().getResource("/img/hitz.png").toExternalForm());
        stage.getIcons().add(logoIrudi);
    }

    public static void setRoot(Parent root) {
        if (scene != null) {
            scene.setRoot(root);
        } else {
            System.err.println("Error: La escena no está inicializada.");
        }
    }
    
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}