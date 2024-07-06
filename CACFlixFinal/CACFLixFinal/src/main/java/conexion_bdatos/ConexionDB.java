package conexion_bdatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionDB {

    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/cacflix";
    private static final String user = "root";  
    private static final String password = "codoacodo1";

    private static Connection cx;

    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver JDBC", e);
        }
        cx = DriverManager.getConnection(url, user, password);
        return cx;
    }

    public static void cerrarConexion() throws SQLException {
        String mensaje = "";

        try {
            mensaje = "desconectado correctamente de :" + url + " (" + cx + ")";
            cx.close();
        } catch (SQLException ex) {
            mensaje = "desconectado con errores de :" + url + " (" + cx + ")" + ex;
            cx.close();
        } finally {
            JOptionPane.showMessageDialog(null, mensaje);
        }
    }

    //Prueba de conexion
    public static void main(String[] args) throws SQLException {
        try {
            Connection con = ConexionDB.obtenerConexion();
            if (con != null) {
                System.out.println("Conexion exitosa");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ConexionDB.cerrarConexion();
    }
}
