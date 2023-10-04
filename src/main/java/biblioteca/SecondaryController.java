package biblioteca;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class SecondaryController {
    static String admin;
    public static void setAdmin(String admin) {
        SecondaryController.admin = admin;
    }

    static String usuario;
    public static void setUsuario(String usuario) {
        SecondaryController.usuario = usuario;
    }

    ObservableList<Libros> lista;
    ResultSet rs;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> autor;

    @FXML
    private TableColumn<Libros, String> autorTabla;

    @FXML
    private TextField buscar;

    @FXML
    private Button buscarBoton;

    @FXML
    private TableColumn<Libros, Integer> cantidadTabla;

    @FXML
    private ComboBox<String> editorial;

    @FXML
    private TableColumn<Libros, String> editorialTabla;

    @FXML
    private Button eliminar;

    @FXML
    private TableColumn<Libros, String> fechaTabla;

    @FXML
    private TableColumn<Libros, Integer> idTabla;

    @FXML
    private Button insertar;

    @FXML
    private Button limpiar;

    @FXML
    private TableView<Libros> tabla;

    @FXML
    private TableColumn<Libros, String> tituloTabla;

    @FXML
    private Button volver;

    @FXML
    void limpiar(ActionEvent event) throws SQLException {
        lista = FXCollections.observableArrayList();
        Libros.llenarTabla(lista);
        tabla.setItems(lista);
    }

    @FXML
    void buscar(ActionEvent event) throws SQLException {
        if(buscar.getText().equals("")){
            lista = FXCollections.observableArrayList();
            Libros.llenarTabla(lista);
            tabla.setItems(lista);
        }else{
            String filtroTexto = buscar.getText().toLowerCase();
            FilteredList<Libros> filtro = new FilteredList<>(lista);
            filtro.setPredicate(item ->  String.valueOf(item.getTitulo()).toLowerCase().startsWith(filtroTexto));
            buscar.clear();
            tabla.setItems(filtro);
            }
    }

    @FXML
    void eliminar(ActionEvent event) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        PreparedStatement stmt=c.prepareStatement("select distinct L.id, L.titulo, A.nombre, L.editorial, L.fecha, C.cantidad from libros L inner join autor A on L.id_autor=A.id inner join copias C on L.id=C.id_libro", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        stmt.executeQuery();
        rs=stmt.executeQuery();
        Libros pos=tabla.getSelectionModel().getSelectedItem();
        stmt=c.prepareStatement("delete from libros where id='"+pos.getId()+"'");
        stmt.executeUpdate();
        PreparedStatement updateStmt = c.prepareStatement("UPDATE libros SET id = id - 1 WHERE id > ?");
        updateStmt.setInt(1, pos.getId());
        updateStmt.executeUpdate();
        lista=FXCollections.observableArrayList();
            Libros.llenarTabla(lista);
            tabla.setItems(lista);
    }

    @FXML
    void insertar(ActionEvent event) throws IOException {
        App.changeScene("terciary.fxml", 1000, 600);
    }

    @FXML
    void volver(ActionEvent event) throws IOException {
        App.changeScene("quintuary.fxml", 810, 500);
    }

    @FXML
    void autor(ActionEvent event) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        PreparedStatement stmt=c.prepareStatement("select L.id, L.titulo, A.nombre, L.editorial, L.fecha, C.cantidad from libros L inner join autor A on L.id_autor=A.id inner join copias C on L.id=C.id_libro", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        stmt.executeQuery();
        rs=stmt.executeQuery();
        if(autor.getValue().equals("Ninguno")){
            lista = FXCollections.observableArrayList();
            Libros.llenarTabla(lista);
            tabla.setItems(lista);
        }else{
            String filtroTexto = autor.getValue();
            FilteredList<Libros> filtro = new FilteredList<>(lista);
            filtro.setPredicate(item ->  String.valueOf(item.getAutor()).contains(filtroTexto));
            tabla.setItems(filtro);
            }
    }

    @FXML
    void editorial(ActionEvent event) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        PreparedStatement stmt=c.prepareStatement("select L.id, L.titulo, A.nombre, L.editorial, L.fecha, C.cantidad from libros L inner join autor A on L.id_autor=A.id inner join copias C on L.id=C.id_libro", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        stmt.executeQuery();
        rs=stmt.executeQuery();
        if(editorial.getValue().equals("Ninguna")){
            lista = FXCollections.observableArrayList();
            Libros.llenarTabla(lista);
            tabla.setItems(lista);
        }else{
            String filtroTexto = editorial.getValue();
            FilteredList<Libros> filtro = new FilteredList<>(lista);
            filtro.setPredicate(item ->  String.valueOf(item.getEditorial()).contains(filtroTexto));
            tabla.setItems(filtro);
            }
    }

    private void CambioEditorial() throws SQLException{
        rs.first();
        ObservableList<String> list = FXCollections.observableArrayList();
        do {
        String addList=rs.getString("L.editorial");
        if(!list.contains(addList)){
        list.add(addList);
            }
        } while (rs.next());
        Collections.sort(list);
        list.add("Ninguna");
        editorial.setItems(list);
    }

    private void CambioAutor() throws SQLException{
        rs.first();
        ObservableList<String> list = FXCollections.observableArrayList();
        do {
        String addList=rs.getString("A.nombre");
        if(!list.contains(addList)){
        list.add(addList);
            }
    } while (rs.next());
    Collections.sort(list);
        list.add("Ninguno");
        autor.setItems(list);
    }

    @FXML
    void initialize() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
            PreparedStatement stmt=c.prepareStatement("select L.id, L.titulo, A.nombre, L.editorial, L.fecha, C.cantidad from libros L inner join autor A on L.id_autor=A.id inner join copias C on L.id=C.id_libro", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt.executeQuery();
            lista=FXCollections.observableArrayList();
            Libros.llenarTabla(lista);
            tabla.setItems(lista);
            idTabla.setCellValueFactory(new PropertyValueFactory<Libros, Integer>("id"));
            tituloTabla.setCellValueFactory(new PropertyValueFactory<Libros, String>("titulo"));
            autorTabla.setCellValueFactory(new PropertyValueFactory<Libros, String>("autor"));
            editorialTabla.setCellValueFactory(new PropertyValueFactory<Libros, String>("editorial"));
            fechaTabla.setCellValueFactory(new PropertyValueFactory<Libros, String>("fecha"));
            cantidadTabla.setCellValueFactory(new PropertyValueFactory<Libros, Integer>("cantidad"));
            CambioEditorial();
            CambioAutor();
            try{
                PreparedStatement stmt1=c.prepareStatement("select adm from usuarios", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs=stmt1.executeQuery();
            if(!admin.equals("Si")){
                eliminar.setDisable(true);
                insertar.setDisable(true);
            }
            }catch(Exception e){
                System.out.println(e);
            }
        assert autor != null : "fx:id=\"autor\" was not injected: check your FXML file 'secondary.fxml'.";
        assert autorTabla != null : "fx:id=\"autorTabla\" was not injected: check your FXML file 'secondary.fxml'.";
        assert editorial != null : "fx:id=\"editorial\" was not injected: check your FXML file 'secondary.fxml'.";
        assert editorialTabla != null : "fx:id=\"editorialTabla\" was not injected: check your FXML file 'secondary.fxml'.";
        assert fechaTabla != null : "fx:id=\"fechaTabla\" was not injected: check your FXML file 'secondary.fxml'.";
        assert idTabla != null : "fx:id=\"idTabla\" was not injected: check your FXML file 'secondary.fxml'.";
        assert tabla != null : "fx:id=\"tabla\" was not injected: check your FXML file 'secondary.fxml'.";
        assert tituloTabla != null : "fx:id=\"tituloTabla\" was not injected: check your FXML file 'secondary.fxml'.";

    }

}
