package paagbat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import paagbat.model.HerrienAtzipena;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static HerrienAtzipena herriak = new HerrienAtzipena();
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("Nagusia"),800,800);
        scene.getStylesheets().add(getClass().getResource("css/ModenaAldatua.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("2025-02-26 AZTERKETA");
        
    //    stage.setFullScreen(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/paagbat/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}