/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion_bdatos.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Pelicula;

public class PeliculaDAO {

    public boolean insertarPel(Pelicula pelicula) {
        try (Connection conn = ConexionDB.obtenerConexion(); 
            PreparedStatement pstmt = conn.prepareStatement("insert into pelis_usuario (poster_path,email) values(?,?)")) {
            pstmt.setString(1, pelicula.getPoster_path());
            pstmt.setString(2, pelicula.getEmail());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
        }
    }

    public boolean eliminarPel(Pelicula pelicula) {
        try (Connection conn = ConexionDB.obtenerConexion(); 
            PreparedStatement pstmt = conn.prepareStatement("delete from pelis_usuario where poster_path = ? and email = ?")) {
            pstmt.setString(1, pelicula.getPoster_path());
            pstmt.setString(2, pelicula.getEmail());
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public List<String> obtenerPelis(String email) {
        List<String> pelis = new ArrayList<>();
        try (Connection conn = ConexionDB.obtenerConexion(); 
                PreparedStatement pstmt = conn.prepareStatement("select * from pelis_usuario where email = ?")) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    pelis.add(rs.getString("poster_path"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pelis;
    }
}
