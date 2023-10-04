package biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;

public class Libros {
    int id;
    String titulo;
    String autor;
    String editorial;
    String fecha;
    int cantidad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Libros(int id, String titulo, String autor, String editorial, String fecha, int cantidad){
        this.id=id;
        this.titulo=titulo;
        this.autor=autor;
        this.editorial=editorial;
        this.fecha=fecha;
        this.cantidad=cantidad;
    }
    public static void llenarTabla(ObservableList<Libros> listaLibros) throws SQLException{
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        ResultSet rs;
        try{
        PreparedStatement stmt=c.prepareStatement("select L.id, L.titulo, A.nombre,L.editorial, L.fecha, C.cantidad from libros L inner join autor A on L.id_autor=A.id inner join copias C on L.id=C.id_libro;", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs=stmt.executeQuery();
        while(rs.next()){
            listaLibros.add(new Libros(rs.getInt("L.id"), rs.getString("L.titulo"), rs.getString("A.nombre"), rs.getString("L.editorial"), rs.getString("L.fecha"), rs.getInt("C.cantidad")));
        }
        } catch(Exception e){
            System.out.println(e);
        }
    }
}