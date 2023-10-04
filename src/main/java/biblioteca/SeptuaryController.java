package biblioteca;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class SeptuaryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imagen;

    @FXML
    private Button volver;

    @FXML
    void volver(ActionEvent event) throws IOException {
        App.changeScene("quintuary.fxml", 810, 500);
    }

    @FXML
    void initialize() {
        assert imagen != null : "fx:id=\"imagen\" was not injected: check your FXML file 'septuary.fxml'.";
        assert volver != null : "fx:id=\"volver\" was not injected: check your FXML file 'septuary.fxml'.";

    }

}