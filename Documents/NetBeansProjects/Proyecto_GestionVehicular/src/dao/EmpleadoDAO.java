package dao;

import conexion.Conexion;
import modelo.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    //INSERTAR (NO necesitas id_usuario, el trigger lo hace)
    public boolean insertar(Empleado emp) {
        String sql = "INSERT INTO Empleados (nombres, apellidos, dui, telefono, cargo, departamento, licencia) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);

            ps.setString(1, emp.getNombres());
            ps.setString(2, emp.getApellidos());
            ps.setString(3, emp.getDui());
            ps.setString(4, emp.getTelefono());
            ps.setString(5, emp.getCargo());
            ps.setString(6, emp.getDepartamento());
            ps.setString(7, emp.getLicencia());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error al insertar empleado: " + e);
            return false;
        }
    }
    
    // 🔹 LISTAR
    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();

        String sql = "SELECT e.*, u.username " +
                     "FROM Empleados e " +
                     "LEFT JOIN Usuarios u ON e.id_usuario = u.id_usuario";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Empleado emp = new Empleado();

                emp.setIdEmpleado(rs.getInt("id_empleado"));
                emp.setNombres(rs.getString("nombres"));
                emp.setApellidos(rs.getString("apellidos"));
                emp.setDui(rs.getString("dui"));
                emp.setTelefono(rs.getString("telefono"));
                emp.setCargo(rs.getString("cargo"));
                emp.setDepartamento(rs.getString("departamento"));
                emp.setEstado(rs.getString("estado"));
                emp.setLicencia(rs.getString("licencia"));

                // 🔥 nuevo
                emp.setUsername(rs.getString("username"));

                lista.add(emp);
            }

        } catch (Exception e) {
            System.out.println("Error listar: " + e);
        }

        return lista;
    }
    
    // ACTUALIZAR (sin tocar id_usuario)
    public boolean actualizar(Empleado emp) {
        String sql = "UPDATE Empleados "
                + "SET nombres=?, apellidos=?, dui=?, telefono=?, cargo=?, departamento=?, licencia=? "
                + "WHERE id_empleado=?";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);

            ps.setString(1, emp.getNombres());
            ps.setString(2, emp.getApellidos());
            ps.setString(3, emp.getDui());
            ps.setString(4, emp.getTelefono());
            ps.setString(5, emp.getCargo());
            ps.setString(6, emp.getDepartamento());
            ps.setString(7, emp.getEstado());
            ps.setString(8, emp.getLicencia());
            ps.setInt(9, emp.getIdEmpleado());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar empleado: " + e);
            return false;
        }
    }
    
    //ELIMINAR o DESACTIVAR
    public boolean desactivar(int id) {

        String sql = "UPDATE Empleados SET estado='INACTIVO' WHERE id_empleado=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error desactivar: " + ex.getMessage());
            return false;
        }
    }
    
    public boolean activar(int id) {

        String sql = "UPDATE Empleados SET estado='ACTIVO' WHERE id_empleado=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error activar: " + ex.getMessage());
            return false;
        }
    }
    
    public boolean duiExiste(int id, String dui) {

        String sql = "SELECT 1 FROM Empleados WHERE dui=? AND id_empleado<>?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dui);
            ps.setInt(2, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error DUI: " + e.getMessage());
        }

        return false;
    }
    
    /*//LISTAR
    public List<Empleado> listar() {

        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empleados";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Empleado e = new Empleado();

                e.setId(rs.getInt("id_empleado"));
                e.setNombres(rs.getString("nombres"));
                e.setApellidos(rs.getString("apellidos"));
                e.setDui(rs.getString("dui"));
                e.setTelefono(rs.getString("telefono"));
                e.setCargo(rs.getString("cargo"));
                e.setDepartamento(rs.getString("departamento"));
                e.setEstado(rs.getString("estado"));
                e.setFechaRegistro(rs.getDate("fecha_registro"));
                e.setLicencia(rs.getString("licencia"));
                e.setIdUsuario(rs.getInt("id_usuario"));

                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error listar: " + e.getMessage());
        }

        return lista;
    }

    //INSERTAR
    public boolean insertar(Empleado e) {

        String sql = "INSERT INTO Empleados(nombres, apellidos, dui, telefono, cargo, departamento, estado, licencia, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNombres());
            ps.setString(2, e.getApellidos());
            ps.setString(3, e.getDui());
            ps.setString(4, e.getTelefono());
            ps.setString(5, e.getCargo());
            ps.setString(6, e.getDepartamento());
            ps.setString(7, e.getEstado());
            ps.setString(8, e.getLicencia());
            ps.setInt(9, e.getIdUsuario());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error insertar: " + ex.getMessage());
            return false;
        }
    }

    //ACTUALIZAR
    public boolean actualizar(Empleado e) {

        String sql = "UPDATE Empleados SET nombres=?, apellidos=?, dui=?, telefono=?, cargo=?, departamento=?, estado=?, licencia=? WHERE id_empleado=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNombres());
            ps.setString(2, e.getApellidos());
            ps.setString(3, e.getDui());
            ps.setString(4, e.getTelefono());
            ps.setString(5, e.getCargo());
            ps.setString(6, e.getDepartamento());
            ps.setString(7, e.getEstado());
            ps.setString(8, e.getLicencia());
            ps.setInt(9, e.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error actualizar: " + ex.getMessage());
            return false;
        }
    }

    */
}