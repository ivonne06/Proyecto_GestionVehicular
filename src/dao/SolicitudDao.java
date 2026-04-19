package dao;

import modelo.Solicitud;
import conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDao {
    // ROL EMPLEADO
    
    public List<Object[]> listarPorEmpleado(int idEmpleado) {

        List<Object[]> lista = new ArrayList<>();

        String sql = "SELECT id_solicitud, destino, motivo_viaje, motivo_respuesta, pasajeros, fecha_salida, fecha_regreso, estado " +
                     "FROM Solicitudes WHERE id_empleado=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String motivo = rs.getString("motivo_viaje");
                String respuesta = rs.getString("motivo_respuesta");
                String estado = rs.getString("estado");

                if (respuesta != null && !respuesta.trim().isEmpty()) {
                    motivo += " | (" + estado + ") " + respuesta;
                }

                lista.add(new Object[]{
                    rs.getInt("id_solicitud"),
                    rs.getString("destino"),
                    motivo,
                    rs.getInt("pasajeros"),
                    rs.getDate("fecha_salida"),
                    rs.getDate("fecha_regreso"),
                    estado
                });
            }

        } catch (SQLException e) {
            System.out.println("Error listar: " + e.getMessage());
        }

        return lista;
    }
    
    public boolean insertar(Solicitud s) {

        String sql = "INSERT INTO Solicitudes (id_empleado, fecha_salida, fecha_regreso, destino, motivo_viaje, pasajeros) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, s.getIdEmpleado());
            ps.setDate(2, new java.sql.Date(s.getFechaSalida().getTime()));
            ps.setDate(3, new java.sql.Date(s.getFechaRegreso().getTime()));
            ps.setString(4, s.getDestino());
            ps.setString(5, s.getMotivoViaje());
            ps.setInt(6, s.getPasajeros());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error insertar: " + e.getMessage());
            return false;
        }
    }
    
    // EDITAR SOLO SI PENDIENTE
    public boolean actualizar(Solicitud s) {

        String sql = "UPDATE Solicitudes SET destino=?, motivo_viaje=?, pasajeros=?, fecha_salida=?, fecha_regreso=? " +
                     "WHERE id_solicitud=? AND id_empleado=? AND estado='PENDIENTE'";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getDestino());
            ps.setString(2, s.getMotivoViaje());
            ps.setInt(3, s.getPasajeros());
            ps.setDate(4, new java.sql.Date(s.getFechaSalida().getTime()));
            ps.setDate(5, new java.sql.Date(s.getFechaRegreso().getTime()));
            ps.setInt(6, s.getId());
            ps.setInt(7, s.getIdEmpleado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error actualizar: " + e.getMessage());
            return false;
        }
    }
    
    public boolean cancelar(int id, int idEmpleado, String motivoCancelacion) {

        String sql = "UPDATE Solicitudes " +
                     "SET estado = 'CANCELADA', motivo_respuesta = ?, fecha_estado = GETDATE() " +
                     "WHERE id_solicitud = ? AND id_empleado = ? AND estado = 'PENDIENTE'";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, motivoCancelacion);
            ps.setInt(2, id);
            ps.setInt(3, idEmpleado);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error cancelar: " + e.getMessage());
            return false;
        }
    }

    
    // ROL ENCARGADO

    // LISTAR TODAS
    public List<Object[]> listarTodas() {

        List<Object[]> lista = new ArrayList<>();

        String sql = "SELECT s.id_solicitud, e.nombres + ' ' + e.apellidos AS empleado, " +
                 "s.destino, s.motivo_viaje, s.motivo_respuesta, s.pasajeros, " +
                 "s.fecha_salida, s.fecha_regreso, s.estado " +
                 "FROM Solicitudes s INNER JOIN Empleados e ON s.id_empleado = e.id_empleado";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String motivo = rs.getString("motivo_viaje");
                String respuesta = rs.getString("motivo_respuesta");
                String estado = rs.getString("estado");

                if (respuesta != null && !respuesta.trim().isEmpty()) {
                    motivo += " | (" + estado + ") " + respuesta;
                }

                lista.add(new Object[]{
                    rs.getInt("id_solicitud"),
                    rs.getString("empleado"),
                    rs.getString("destino"),
                    motivo,
                    rs.getInt("pasajeros"),
                    rs.getDate("fecha_salida"),
                    rs.getDate("fecha_regreso"),
                    estado
                });
            }

        } catch (SQLException e) {
            System.out.println("Error listar todas: " + e.getMessage());
        }

        return lista;
    }
    
    private boolean cambiarEstado(int id, String estado, String motivo) {

        String sql = "UPDATE Solicitudes SET estado=?, motivo_respuesta=?, fecha_estado=GETDATE() WHERE id_solicitud=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setString(2, motivo);
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    // APROBAR O RECHAZAR SOLICITUD
    public boolean aprobar(int id, String motivo) {
        return cambiarEstado(id, "APROBADA", motivo);
    }

    public boolean rechazar(int id, String motivo) {
        return cambiarEstado(id, "RECHAZADA", motivo);
    }
   
}
