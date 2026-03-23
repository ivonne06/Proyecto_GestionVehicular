/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import conexion.Conexion;
import modelo.Usuario;
import java.sql.*;

/**
 *
 * @author walte
 */
public class UsuarioDAO {
    public Usuario login(String user, String pass) {
        Usuario usr = null;
        // Consulta a tabla usuarios
        String sql = "SELECT id_usuario, username, rol, estado, debe_cambiar_password " +
                     "FROM Usuarios WHERE username = ? AND password = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, user);
            ps.setString(2, pass);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usr = new Usuario();
                    usr.setIdUsuario(rs.getInt("id_usuario"));
                    usr.setUsername(rs.getString("username"));
                    usr.setRol(rs.getString("rol"));
                    // verificar el estado del usuario activo 1, inactivo 0
                    usr.setEstado(rs.getBoolean("estado")); 
                    usr.setDebeCambiarPassword(rs.getBoolean("debe_cambiar_password"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en el método login: " + e.getMessage());
        }
        return usr;
    }
    
}


