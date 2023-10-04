package biblioteca;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SextuaryController {
    ResultSet rs;
    ObservableList<Usuario> lista;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Usuario, String> adminTabla;

    @FXML
    private TableView<Usuario> tablaUs;

    @FXML
    private TableColumn<Usuario, String> usuarioTabla;

    @FXML
    private Button volver;

    @FXML
    void volver(ActionEvent event) throws IOException {
        App.changeScene("quintuary.fxml", 810, 500);
    }

    @FXML
    void initialize() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
            PreparedStatement stmt1=c.prepareStatement("select usuario, adm from usuarios", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt1.executeQuery();
            lista=FXCollections.observableArrayList();
            Usuario.llenarTablaUsu(lista);
            tablaUs.setItems(lista);
            usuarioTabla.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuario"));
            adminTabla.setCellValueFactory(new PropertyValueFactory<Usuario, String>("admin"));
        assert adminTabla != null : "fx:id=\"contrasenyaTabla\" was not injected: check your FXML file 'sextuary.fxml'.";
        assert tablaUs != null : "fx:id=\"tablaUs\" was not injected: check your FXML file 'sextuary.fxml'.";
        assert usuarioTabla != null : "fx:id=\"usuarioTabla\" was not injected: check your FXML file 'sextuary.fxml'.";
        assert volver != null : "fx:id=\"volver\" was not injected: check your FXML file 'sextuary.fxml'.";

    }

}