package biblioteca;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class QuintuaryController {
    ResultSet rs;
    static String admin;
    public static void setAdmin(String admin) {
        QuintuaryController.admin = admin;
    }

    static String usuario;
    public static void setUsuario(String usuario) {
        QuintuaryController.usuario = usuario;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button creditos;

    @FXML
    private Button libros;

    @FXML
    private Button usuarios2;
    
    @FXML
    private Button volver;

    @FXML
    private Button usuarios;

    @FXML
    void creditos(ActionEvent event) throws IOException {
        App.changeScene("septuary.fxml", 800, 500);
    }

    @FXML
    void libros(ActionEvent event) throws IOException {
        App.changeScene("secondary.fxml", 1250, 680);
    }

    @FXML
    void usuarios2(ActionEvent event) throws IOException {
        App.changeScene("sextuary.fxml", 500, 480);
    }

    @FXML
    void volver(ActionEvent event) throws IOException {
        App.changeScene("primary.fxml", 600, 400);
    }

    @FXML
    void usuarios(ActionEvent event) throws IOException {
        App.changeScene("cuartiary.fxml", 700, 580);
    }

    @FXML
    void initialize() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        try{
            PreparedStatement stmt1=c.prepareStatement("select adm from usuarios where usuario='"+usuario+"'", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt1.executeQuery();
        if(!admin.equals("Si")){
            usuarios.setDisable(true);
        }
        }catch(Exception e){
            System.out.println(e);
        }
        assert libros != null : "fx:id=\"libros\" was not injected: check your FXML file 'quintuary.fxml'.";
        assert usuarios2 != null : "fx:id=\"usuario\" was not injected: check your FXML file 'quintuary.fxml'.";
        assert usuarios != null : "fx:id=\"usuarios\" was not injected: check your FXML file 'quintuary.fxml'.";

    }

}
