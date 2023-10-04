package biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;

public class Usuario{
    String usuario;
    String contrasenya;
    String admin;
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContrasenya() {
        return contrasenya;
    }
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
    public String getAdmin() {
        return admin;
    }
    public void setAdmin(String admin) {
        this.admin = admin;
    }
    public Usuario(String usuario, String contrasenya, String admin){
        this.usuario=usuario;
        this.contrasenya=contrasenya;
        this.admin=admin;
    }
    public Usuario(String usuario, String contrasenya){
        this.usuario=usuario;
        this.contrasenya=contrasenya;
    }
    public static void llenarTablaUsu(ObservableList<Usuario> listaLibros) throws SQLException{
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        ResultSet rs;
        try{
        PreparedStatement stmt=c.prepareStatement("select * from usuarios", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs=stmt.executeQuery();
        while(rs.next()){
            listaLibros.add(new Usuario(rs.getString("usuario"), rs.getString("contrasenya"), rs.getString("adm")));
        }
        } catch(Exception e){
            System.out.println(e);
        }
    }
    public static void llenarTablaUsu2(ObservableList<Usuario> listaLibros) throws SQLException{
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admini1234");
        ResultSet rs;
        try{
        PreparedStatement stmt=c.prepareStatement("select usuario, contrasenya from usuarios", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs=stmt.executeQuery();
        while(rs.next()){
            listaLibros.add(new Usuario(rs.getString("usuario"), rs.getString("contrasenya")));
        }
        } catch(Exception e){
            System.out.println(e);
        }
    }
}