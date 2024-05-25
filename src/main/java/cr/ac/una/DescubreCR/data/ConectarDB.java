package cr.ac.una.DescubreCR.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarDB {
    private static String id_album;
    private static String id_imagen;
    private static String id_ubicacion;
    
    private final static String databaseName = "bd_descubrecr";
    private final static String user = "root";
    private final static String pass = "";

    private final static int port = 3306;
    private final static String host = "localhost";

    private final static String url = ""
            + "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
    private static Connection conexion; //java.sql connection variable de librería

    public static Connection conectar() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Class for name not found" + e.getMessage());
        }
        try {
            conexion = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
        }
        return conexion;
    }
    
    public void closeConnection(Connection cn){
        try{
            if(cn != null){
                cn.close();
            }
        }catch(SQLException e){
        }
    }
    
    public String getId_album() {
        return id_album;
    }

    public void setId_album(String id_album) {
        ConectarDB.id_album = id_album;
    }

    public String getId_imagen() {
        return id_imagen;
    }

    public void setId_imagen(String id_imagen) {
        ConectarDB.id_imagen = id_imagen;
    }
    
    public String getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(String id_ubicacion) {
        ConectarDB.id_ubicacion = id_ubicacion;
    }
}