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

public class TerciaryController {
    ResultSet rs;
    ObservableList<Libros> lista;
    String id3;
    String id2;
    String id4;
    String id5;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField autor;

    @FXML
    private TableColumn<Libros, String> autorTabla;

    @FXML
    private TextField cantidad;

    @FXML
    private TableColumn<Libros, Integer> cantidadTabla;

    @FXML
    private TextField editorial;

    @FXML
    private TableColumn<Libros, String> editorialTabla;

    @FXML
    private Button eliminar;

    @FXML
    private TextField fecha;

    @FXML
    private TableColumn<Libros, String> fechaTabla;

    @FXML
    private TableColumn<Libros, Integer> idTabla;

    @FXML
    private Button insertar;

    @FXML
    private Button limpiar;

    @FXML
    private Button modificar;

    @FXML
    private TableView<Libros> tabla;

    @FXML
    private TextField titulo;

    @FXML
    private TableColumn<Libros, String> tituloTabla;

    @FXML
    private Button volver;

    private final ListChangeListener<Libros> selectorTablaClientes = new ListChangeListener<Libros>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Libros> c) {
            try {

                ponerClienteSeleccionado();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
    public Libros getTablaClientesSeleccionado() {
        if (tabla != null) {
            List<Libros> tabla2 = tabla.getSelectionModel().getSelectedItems();
            if (tabla2.size() == 1) {
                final Libros clienteSeleccionado = tabla2.get(0);
                return clienteSeleccionado;
            }
        }
        return null;
    }

    public void ponerClienteSeleccionado() throws SQLException {
        final Libros cliente = getTablaClientesSeleccionado();
        int posicionCliente = lista.indexOf(cliente);
        if (cliente != null) {
            id2=Integer.toString(cliente.getId());
            titulo.setText(cliente.getTitulo());
            autor.setText(cliente.getAutor());
            editorial.setText(cliente.getEditorial());
            fecha.setText(cliente.getFecha());
            cantidad.setText(Integer.toString(cliente.getCantidad()));
        }
    }

    @FXML
    void limpiar(ActionEvent event) {
        titulo.clear();
        autor.clear();
        editorial.clear();
        fecha.clear();
        cantidad.clear();
    }

        @FXML
        void eliminar(ActionEvent event) throws SQLException {
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        PreparedStatement stmt=c.prepareStatement("select distinct L.id, L.titulo, A.nombre, L.editorial, L.fecha, C.cantidad from libros L inner join autor A on L.id_autor=A.id inner join copias C on L.id=C.id_libro", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        stmt.executeQuery();
        rs=stmt.executeQuery();
        Libros pos=tabla.getSelectionModel().getSelectedItem();
        stmt=c.prepareStatement("delete from libros where id='"+pos.getId()+"'", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        stmt.executeUpdate();
        PreparedStatement updateStmt = c.prepareStatement("UPDATE libros SET id = id - 1 WHERE id > ?");
        updateStmt.setInt(1, pos.getId());
        updateStmt.executeUpdate();
        titulo.clear();
        autor.clear();
        editorial.clear();
        fecha.clear();
        cantidad.clear();
        lista=FXCollections.observableArrayList();
            Libros.llenarTabla(lista);
            tabla.setItems(lista);
        }

    @FXML
    void modificar(ActionEvent event) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        if(!titulo.getText().equals("") && !autor.getText().equals("") && !editorial.getText().equals("") && !fecha.getText().equals("") && !cantidad.getText().equals("")){
            PreparedStatement stmt5=c.prepareStatement("select * from libros where id='"+id2+"'", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs5=stmt5.executeQuery();
            rs5.first();
            do{
            rs5.updateString("titulo", titulo.getText());
            rs5.updateString("editorial", editorial.getText());
            rs5.updateString("fecha", fecha.getText());
            rs5.updateRow();
            titulo.clear();
            editorial.clear();
            fecha.clear();
            rs5.moveToCurrentRow();
            id5=rs5.getString(String.valueOf("id_autor"));
            }while(rs5.next());
        PreparedStatement stmt4=c.prepareStatement("select * from copias where id_libro='"+id2+"'", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs4=stmt4.executeQuery();
        rs4.first();
        do{
        rs4.updateString("cantidad", cantidad.getText());
        rs4.updateRow();
        cantidad.clear();
        rs4.moveToCurrentRow();
        }while(rs4.next());
        PreparedStatement stmt6=c.prepareStatement("select * from autor where id='"+id5+"'", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs6=stmt6.executeQuery();
        rs6.first();
        do{
        rs6.updateString("nombre", autor.getText());
        rs6.updateRow();
        autor.clear();
        rs6.moveToCurrentRow();
        }while(rs6.next());
        lista=FXCollections.observableArrayList();
        Libros.llenarTabla(lista);
        tabla.setItems(lista);
    }else{
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Porfavor rellene los campos vacios");
        alert.showAndWait();
    }
    }

    @FXML
    void volver(ActionEvent event) throws IOException {
        App.changeScene("secondary.fxml", 1250, 680);
    }


    @FXML
    void insertar(ActionEvent event) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        PreparedStatement stmt1 = c.prepareStatement("SELECT MAX(id) FROM libros");
        ResultSet rs1 = stmt1.executeQuery();
        int lastId = 0;
        if (rs1.next()) {
        lastId = rs1.getInt(1);
        }
        int newId=lastId + 1;
        if(!autor.getText().equals("")){
            PreparedStatement stmt3=c.prepareStatement("select * from autor A", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs3=stmt3.executeQuery();
            rs3.moveToInsertRow();
            rs3.updateString("nombre", autor.getText());
            rs3.insertRow();
            autor.clear();
            rs3.last();
            id3=rs3.getString(String.valueOf("A.id"));
        }
        if(!titulo.getText().equals("") && !editorial.getText().equals("") && !fecha.getText().equals("")){
            PreparedStatement stmt2=c.prepareStatement("select * from libros L", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs2=stmt2.executeQuery();
        rs2.moveToInsertRow();
        rs2.updateString("id", Integer.toString(newId));
        rs2.updateString("titulo", titulo.getText());
        rs2.updateString("editorial", editorial.getText());
        rs2.updateString("fecha", fecha.getText());
        rs2.updateString("id_autor", id3);
        rs2.insertRow();
        titulo.clear();
        editorial.clear();
        fecha.clear();
        rs2.last();
        id2=rs2.getString(String.valueOf("L.id"));
        }
        if(!cantidad.getText().equals("")){
            PreparedStatement stmt4=c.prepareStatement("select * from copias", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs4=stmt4.executeQuery();
        rs4.moveToInsertRow();
        rs4.updateString("cantidad", cantidad.getText());
        rs4.updateString("id_libro", id2);
        rs4.insertRow();
        cantidad.clear();
}
lista=FXCollections.observableArrayList();
            Libros.llenarTabla(lista);
            tabla.setItems(lista);
    }

    @FXML
    void initialize() throws SQLException {
        ObservableList<Libros> tabla3=tabla.getSelectionModel().getSelectedItems();
        tabla3.addListener(selectorTablaClientes);
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
        assert autor != null : "fx:id=\"autor\" was not injected: check your FXML file 'terciary.fxml'.";
        assert cantidad != null : "fx:id=\"cantidad\" was not injected: check your FXML file 'terciary.fxml'.";
        assert editorial != null : "fx:id=\"editorial\" was not injected: check your FXML file 'terciary.fxml'.";
        assert fecha != null : "fx:id=\"fecha\" was not injected: check your FXML file 'terciary.fxml'.";
        assert insertar != null : "fx:id=\"insertar\" was not injected: check your FXML file 'terciary.fxml'.";
        assert titulo != null : "fx:id=\"titulo\" was not injected: check your FXML file 'terciary.fxml'.";

    }

}
