package paagbat.controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class ControladorGameHive {

    @FXML
    private VBox leftPanel;

    @FXML
    private StackPane rightContent;

    @FXML
    private VBox loginForm;

    @FXML
    private VBox registerForm;

    @FXML
private void handleSwitchToRegister() {
    animateFormSwitch(loginForm, registerForm, "#e1701a", -100);
}

@FXML
private void handleSwitchToLogin() {
    animateFormSwitch(registerForm, loginForm, "white", 0);
}

private void animateFormSwitch(Node fromForm, Node toForm, String newColor, double slideToX) {
    TranslateTransition slide = new TranslateTransition(Duration.millis(400), rightContent);
    slide.setToX(slideToX);

    FadeTransition fadeOut = new FadeTransition(Duration.millis(300), fromForm);
    fadeOut.setToValue(0);

    FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toForm);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);

    slide.setOnFinished(e -> {
        fromForm.setVisible(false);  // Aseguramos que el formulario de inicio de sesión se oculte
        toForm.setVisible(true);  // Hacemos visible el formulario de registro
        leftPanel.setStyle("-fx-background-color: " + newColor + ";");
        fadeIn.play();
    });

    fadeOut.setOnFinished(e -> slide.play());
    fadeOut.play();
}

}
