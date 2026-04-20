package dao;
import conexion.Conexion;
import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.HashUtil;

public class UsuarioDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
        
    public Usuario login(String user, String pass) {
        Usuario usr = null;
        
        // Consulta a tabla usuarios
        String sql = "SELECT u.id_usuario, u.username, u.rol, u.estado, u.debe_cambiar_password, " +
             "e.id_empleado " +
             "FROM Usuarios u " +
             "LEFT JOIN Empleados e ON e.id_usuario = u.id_usuario " +
             "WHERE u.username = ? AND u.password = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, user);
            ps.setString(2, HashUtil.sha256(pass).toUpperCase());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usr = new Usuario();
                    usr.setIdUsuario(rs.getInt("id_usuario"));
                    usr.setUsername(rs.getString("username"));
                    usr.setRol(rs.getString("rol"));
                    // verificar el estado del usuario activo 1, inactivo 0
                    usr.setEstado(rs.getBoolean("estado")); 
                    
                    // verifica si el usuario debe cambiar contraseña al primmer loggin si 1, no 0
                    usr.setDebeCambiarPassword(rs.getBoolean("debe_cambiar_password"));
                    
                    usr.setIdEmpleado(rs.getInt("id_empleado"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en el método login: " + e.getMessage());
        }
        return usr;
    }
    
    // LISTAR USUARIOS
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT u.id_usuario, u.username, u.rol, u.estado, " +
                    "e.nombres, e.apellidos " +
                    "FROM Usuarios u " +
                    "LEFT JOIN Empleados e ON e.id_usuario = u.id_usuario;";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
               
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setUsername(rs.getString("username"));
                u.setRol(rs.getString("rol"));
                u.setEstado(rs.getBoolean("estado"));
                
                String nombres = rs.getString("nombres");
                String apellidos = rs.getString("apellidos");

                String nombreCompleto = (nombres != null ? nombres : "") + " " +
                        (apellidos != null ? apellidos : "");
                u.setNombreCompleto(nombreCompleto);
                
                lista.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ACTIVAR
    public boolean activar(int id) {
        String sql = "UPDATE Usuarios SET estado = 1 WHERE id_usuario = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error activar: " + ex.getMessage());
            return false;
        }
    }

    // DESACTIVAR
    public boolean desactivar(int id) {
        String sql = "UPDATE Usuarios SET estado = 0 WHERE id_usuario = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error desactivar: " + ex.getMessage());
            return false;
        }
    }

    // EDITAR USUARIO
    public boolean actualizarRol(int id, String rol) {
        String sql = "UPDATE Usuarios SET rol=? WHERE id_usuario=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, rol);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actulizar el rol: " + e.getMessage());
        }
        return false;
    }
    
    public boolean cambiarPassword(int idUsuario, String nuevaPass) {
        boolean resp = false;

        try {
            Connection con = Conexion.getConexion();
            String sql = "UPDATE Usuarios SET password = ?, debe_cambiar_password = 0 WHERE id_usuario = ?";
            
            String passHash = HashUtil.sha256(nuevaPass).toUpperCase();
             
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, passHash);
            ps.setInt(2, idUsuario);

            resp = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resp;
    }
    
    public String obtenerPasswordActual(int idUsuario) {
        String pass = null;

        try {
            Connection con = Conexion.getConexion();
            String sql = "SELECT password FROM Usuarios WHERE id_usuario = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pass = rs.getString("password");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pass;
    }
}


