package dao;

import conexion_bdatos.ConexionDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;

public class UsuarioDAO {

    private Usuario extraerUsuarioDeResultSet(ResultSet rs) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setDocumento(rs.getLong("documento"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setNombres(rs.getString("nombres"));
        usuario.setDireccion(rs.getString("direccion"));
        usuario.setEmail(rs.getString("email"));
        usuario.setPassword(rs.getString("password"));
        usuario.setEstado(rs.getInt("estado"));
        usuario.setTipo(rs.getString("tipo"));
        return usuario;
    }

    public boolean insertarUs(Usuario usuario) {
        try (Connection conn = ConexionDB.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement("insert into usuarios (documento,apellido,nombres,direccion,email,password,estado,tipo) values(?,?,?,?,?,?,?,?)")) {
            pstmt.setLong(1, usuario.getDocumento());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getNombres());
            pstmt.setString(4, usuario.getDireccion());
            pstmt.setString(5, usuario.getEmail());
            pstmt.setString(6, usuario.getPassword());
            pstmt.setInt(7, usuario.getEstado());
            pstmt.setString(8, usuario.getTipo());
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {

        }
    }

    public boolean actualizarUs(Usuario usuario) {
        try (Connection conn = ConexionDB.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement("update usuarios set apellido = ?, nombres = ?, direccion = ?, email = ?, password = ?, estado = ?, tipo = ? where documento=?")) {
            pstmt.setString(1, usuario.getApellido());
            pstmt.setString(2, usuario.getNombres());
            pstmt.setString(3, usuario.getDireccion());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getPassword());
            pstmt.setInt(6, usuario.getEstado());
            pstmt.setString(7, usuario.getTipo());
            pstmt.setLong(8, usuario.getDocumento());
            pstmt.executeUpdate();
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUs(Long documento) {
        try (Connection conn = ConexionDB.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement("delete from usuarios where documento = ?")) {
            pstmt.setLong(1, documento);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Usuario obtenerUs(Long documento) {
        try (Connection conn = ConexionDB.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement("select * from usuarios where documento = ?")) {
            pstmt.setLong(1, documento);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerUsuarioDeResultSet(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = ConexionDB.obtenerConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("select * from usuarios")) {
            while (rs.next()) {
                Usuario usuario = extraerUsuarioDeResultSet(rs);
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public int validarUs(String email, String password) {
        int tipous = 2;
        try {
            Connection conexion = ConexionDB.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM usuarios WHERE email = ? AND password = ? and tipo = 'A'");
            consulta.setString(1, email);
            consulta.setString(2, password);
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next() == false) {
                consulta = conexion.prepareStatement("SELECT * FROM usuarios WHERE email = ? AND password = ? and tipo = 'C'");
                consulta.setString(1, email);
                consulta.setString(2, password);
                resultado = consulta.executeQuery();
                if (resultado.next()) {
                    tipous = 1;
                }
            } else {
                tipous = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipous;
    }
}
