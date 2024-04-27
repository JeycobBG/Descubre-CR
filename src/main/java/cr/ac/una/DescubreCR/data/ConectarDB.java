/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.DescubreCR.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author JEYCOB
 */
public class ConectarDB {
    private static String id_album;
    private static String id_imagen;
    
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
            System.out.println("Se estableció conexión con la base de datos");
        } catch (SQLException e) {
            System.out.println("Error de conexión" + e.getMessage());
        }
        return conexion;
    }
    
    public void closeConnection(Connection cn){
        try{
            if(cn != null){
                cn.close();
            }
        }catch(SQLException e){
            System.out.println("Error al cerrar la conexion!!!");
        }
    }
    
    public String getId_album() {
        return id_album;
    }

    public void setId_album(String id_album) {
        this.id_album = id_album;
    }

    public String getId_imagen() {
        return id_imagen;
    }

    public void setId_imagen(String id_imagen) {
        this.id_imagen = id_imagen;
    }
}