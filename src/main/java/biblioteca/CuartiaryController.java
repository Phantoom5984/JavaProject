package biblioteca;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CuartiaryController {
    static String usuario;
    public static void setUsuario(String usuario) {
        CuartiaryController.usuario = usuario;
    }
    ResultSet rs;
    ObservableList<Usuario> lista;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField admin1;

    @FXML
    private TableColumn<Usuario, String> adminTabla;

    @FXML
    private TextField contrasenya1;

    @FXML
    private TableColumn<Usuario, String> contrasenyaTabla;

    @FXML
    private Button eliminar;

    @FXML
    private Button insertar;

    @FXML
    private Button limpiar;

    @FXML
    private Button modificar;

    @FXML
    private TableView<Usuario> tablaUs;

    @FXML
    private TextField usuario1;

    @FXML
    private TableColumn<Usuario, String> usuarioTabla;

    @FXML
    private Button volver;

    private final ListChangeListener<Usuario> selectorTablaClientes = new ListChangeListener<Usuario>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Usuario> c) {
            try {

                ponerClienteSeleccionado();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
    public Usuario getTablaClientesSeleccionado() {
        if (tablaUs != null) {
            List<Usuario> tabla2 = tablaUs.getSelectionModel().getSelectedItems();
            if (tabla2.size() == 1) {
                final Usuario clienteSeleccionado = tabla2.get(0);
                return clienteSeleccionado;
            }
        }
        return null;
    }

    public void ponerClienteSeleccionado() throws SQLException {
        final Usuario cliente = getTablaClientesSeleccionado();
        int posicionCliente = lista.indexOf(cliente);
        if (cliente != null) {
            usuario1.setText(cliente.getUsuario());
            contrasenya1.setText(cliente.getContrasenya());
            admin1.setText(cliente.getAdmin());
        }
    }

    @FXML
    void limpiar(ActionEvent event) {
        usuario1.clear();
        contrasenya1.clear();
        admin1.clear();
    }

    @FXML
    void eliminar(ActionEvent event) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        Usuario pos=tablaUs.getSelectionModel().getSelectedItem();
        if(!pos.getUsuario().equals(usuario)){
        PreparedStatement stmt=c.prepareStatement("delete from usuarios where usuario='"+pos.getUsuario()+"'", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        stmt.executeUpdate();
        usuario1.clear();
        contrasenya1.clear();
        admin1.clear();
        lista=FXCollections.observableArrayList();
            Usuario.llenarTablaUsu(lista);
            tablaUs.setItems(lista);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No puedes borrar tu usuario si estas conectado");
            alert.showAndWait();
        }
    }

    @FXML
    void insertar(ActionEvent event) throws SQLException {
        rs.first();
        do{
        if(admin1.getText().equals("Si") && !rs.getString("usuario").equals(usuario1.getText()) || admin1.getText().equals("No") && !rs.getString("usuario").equals(usuario1.getText())){
        rs.moveToInsertRow();
        rs.updateString("usuario", usuario1.getText());
        rs.updateString("contrasenya", contrasenya1.getText());
        rs.updateString("adm", admin1.getText());
        rs.insertRow();
        rs.moveToCurrentRow();
        usuario1.clear();
        contrasenya1.clear();
        admin1.clear();
        rs.last();
        lista = FXCollections.observableArrayList();
        Usuario.llenarTablaUsu(lista);
        tablaUs.setItems(lista);
    }else if(rs.getString("usuario").equals(usuario1.getText())){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Ese nombre de Usuario ya existe");
        alert.showAndWait();
    }else{
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("El campo de admin debe de ser 'Si' o 'No'");
        alert.showAndWait();
        return;
    }
}while(rs.next());

    }

    @FXML
    void modificar(ActionEvent event) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        Usuario pos=tablaUs.getSelectionModel().getSelectedItem();
        if(!usuario1.getText().equals("") && !contrasenya1.getText().equals("") && !admin1.getText().equals("")){
        PreparedStatement stmt4=c.prepareStatement("select * from usuarios where usuario='"+pos.getUsuario()+"'", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs4=stmt4.executeQuery();
        rs4.first();
        do{
            if(admin1.getText().equals("Si") || admin1.getText().equals("No")){
        rs4.updateString("usuario", usuario1.getText());
        rs4.updateString("contrasenya", contrasenya1.getText());
        rs4.updateString("adm", admin1.getText());
        rs4.updateRow();
        usuario1.clear();
        contrasenya1.clear();
        admin1.clear();
        rs4.moveToCurrentRow();
        lista = FXCollections.observableArrayList();
    Usuario.llenarTablaUsu(lista);
    tablaUs.setItems(lista);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("El campo de admin debe de ser 'Si' o 'No'");
                alert.showAndWait();
            }
    }while(rs4.next());
}

    }

    @FXML
    void volver(ActionEvent event) throws IOException {
        App.changeScene("quintuary.fxml", 810, 500);
    }

    @FXML
    void initialize() throws SQLException {
        ObservableList<Usuario> tabla3=tablaUs.getSelectionModel().getSelectedItems();
        tabla3.addListener(selectorTablaClientes);
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
            PreparedStatement stmt=c.prepareStatement("select * from usuarios", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stmt.executeQuery();
            rs=stmt.executeQuery();
            lista=FXCollections.observableArrayList();
            Usuario.llenarTablaUsu(lista);
            tablaUs.setItems(lista);
            usuarioTabla.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuario"));
            contrasenyaTabla.setCellValueFactory(new PropertyValueFactory<Usuario, String>("contrasenya"));
            adminTabla.setCellValueFactory(new PropertyValueFactory<Usuario, String>("admin"));
        assert adminTabla != null : "fx:id=\"adminTabla\" was not injected: check your FXML file 'cuartiary.fxml'.";
        assert usuario1 != null : "fx:id=\"autor\" was not injected: check your FXML file 'cuartiary.fxml'.";
        assert contrasenyaTabla != null : "fx:id=\"contrasenyaTabla\" was not injected: check your FXML file 'cuartiary.fxml'.";
        assert contrasenya1 != null : "fx:id=\"editorial\" was not injected: check your FXML file 'cuartiary.fxml'.";
        assert eliminar != null : "fx:id=\"eliminar\" was not injected: check your FXML file 'cuartiary.fxml'.";
        assert insertar != null : "fx:id=\"insertar\" was not injected: check your FXML file 'cuartiary.fxml'.";
        assert modificar != null : "fx:id=\"modificar\" was not injected: check your FXML file 'cuartiary.fxml'.";
        assert admin1 != null : "fx:id=\"titulo\" was not injected: check your FXML file 'cuartiary.fxml'.";
        assert usuarioTabla != null : "fx:id=\"usuarioTabla\" was not injected: check your FXML file 'cuartiary.fxml'.";
        assert volver != null : "fx:id=\"volver\" was not injected: check your FXML file 'cuartiary.fxml'.";

    }

}
