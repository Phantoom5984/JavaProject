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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController {
    ResultSet rs;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField contrasenya;

    @FXML
    private Button eliminar;

    @FXML
    private Button login;

    @FXML
    private Button registrarse;

    @FXML
    private TextField usuario;

    @FXML
    void eliminar(ActionEvent event) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        PreparedStatement stmt=c.prepareStatement("select * from usuarios", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs=stmt.executeQuery();
        boolean bo=false;
        rs.first();
        do{ 
        if(rs.getString("usuario").equals(usuario.getText()) && rs.getString("contrasenya").equals(contrasenya.getText())){
            bo=true;
        }
    }
        while(rs.next());
        System.out.println(usuario);
        System.out.println(contrasenya);
        if(bo==true){
            stmt=c.prepareStatement("delete from usuarios where usuario='"+usuario.getText()+"'and contrasenya='"+contrasenya.getText()+"'");
            stmt.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmacion");
            alert.setContentText("Usuario eliminado con exito");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Usuario o contraseña incorrecto");
            alert.showAndWait();
        }
    }

    @FXML
    void registrar(ActionEvent event) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        PreparedStatement stmt=c.prepareStatement("select * from usuarios", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs=stmt.executeQuery();
        boolean bo=false;
        rs.first();
        do{ 
        if(rs.getString("usuario").equals(usuario.getText())){
            bo=true;
        }
    }
        while(rs.next());
        if(bo==true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Usuario ya registrado");
            alert.showAndWait();
        }else{
            rs.moveToInsertRow();
            rs.updateString("usuario", usuario.getText());
            rs.updateString("contrasenya", contrasenya.getText());
            rs.insertRow();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmacion");
            alert.setContentText("Usuario registrado con exito");
            alert.showAndWait();
        }
    }
    @FXML
    void switchToSecondary(ActionEvent event) throws SQLException, IOException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        boolean bo=false;
        rs.first();
        do{
            if(rs.getString("usuario").equals(usuario.getText()) && rs.getString("contrasenya").equals(contrasenya.getText())){
                bo=true;
            }
        }
        while(rs.next());
        PreparedStatement stmt2=c.prepareStatement("select * from usuarios where usuario='"+usuario.getText()+"'", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs2=stmt2.executeQuery();
        rs2.first();
        do{
            if(bo==true && rs2.getString("adm").equals("Si")){
                QuintuaryController.setAdmin(rs2.getString("adm"));
                SecondaryController.setAdmin(rs2.getString("adm"));
                SecondaryController.setUsuario(usuario.getText());
                QuintuaryController.setUsuario(usuario.getText());
                CuartiaryController.setUsuario(usuario.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("ACCESO CONCEDIDO");
                alert.showAndWait();
                App.changeScene("quintuary.fxml", 810, 500);
            }
            else if(bo==true && rs2.getString("adm").equals("No")){
                QuintuaryController.setAdmin(rs2.getString("adm"));
                SecondaryController.setAdmin(rs2.getString("adm"));
                SecondaryController.setUsuario(usuario.getText());
                QuintuaryController.setUsuario(usuario.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("ACCESO CONCEDIDO");
                alert.showAndWait();
                App.changeScene("quintuary.fxml", 810, 500);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Usuario o contraseña incorrecto");
                alert.showAndWait();
            }
        }while(rs2.next());
        }
    @FXML
    void initialize() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        PreparedStatement stmt=c.prepareStatement("select usuario, contrasenya from usuarios", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs=stmt.executeQuery();
        assert contrasenya != null : "fx:id=\"contrasenya\" was not injected: check your FXML file 'primary.fxml'.";
        assert login != null : "fx:id=\"login\" was not injected: check your FXML file 'primary.fxml'.";
        assert usuario != null : "fx:id=\"usuario\" was not injected: check your FXML file 'primary.fxml'.";

    }

}
