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

        String sql = "SELECT s.id_solicitud, s.destino, s.motivo_viaje, s.motivo_respuesta, " +
             "s.pasajeros, s.fecha_salida, s.fecha_regreso, s.estado, " +
             "c.id_empleado AS id_conductor, " +
             "c.nombres + ' ' + c.apellidos AS conductor " +
             "FROM Solicitudes s " +
             "LEFT JOIN Empleados c ON s.id_conductor = c.id_empleado " +
             "WHERE s.id_empleado=?";

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
                    estado,
                    rs.getInt("id_conductor"),
                    rs.getString("conductor") 
                });
            }

        } catch (SQLException e) {
            System.out.println("Error listar: " + e.getMessage());
        }

        return lista;
    }
    
    public boolean insertar(Solicitud s) {

        String sql = "INSERT INTO Solicitudes " +
                "(id_empleado, id_conductor, fecha_salida, fecha_regreso, destino, motivo_viaje, pasajeros) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, s.getIdEmpleado());

            if (s.getIdConductor() == 0) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, s.getIdConductor());
            }

            ps.setDate(3, new java.sql.Date(s.getFechaSalida().getTime()));
            ps.setDate(4, new java.sql.Date(s.getFechaRegreso().getTime()));
            ps.setString(5, s.getDestino());
            ps.setString(6, s.getMotivoViaje());
            ps.setInt(7, s.getPasajeros());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error insertar: " + e.getMessage());
            return false;
        }
    }
    
    // EDITAR SOLO SI PENDIENTE
    public boolean actualizar(Solicitud s) {

        String sql = "UPDATE Solicitudes SET destino=?, motivo_viaje=?, pasajeros=?, " +
             "fecha_salida=?, fecha_regreso=?, id_conductor=? " +
             "WHERE id_solicitud=? AND id_empleado=? AND estado='PENDIENTE'";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getDestino());
            ps.setString(2, s.getMotivoViaje());
            ps.setInt(3, s.getPasajeros());
            ps.setDate(4, new java.sql.Date(s.getFechaSalida().getTime()));
            ps.setDate(5, new java.sql.Date(s.getFechaRegreso().getTime()));
            ps.setInt(6, s.getIdConductor());
            ps.setInt(7, s.getId());
            ps.setInt(8, s.getIdEmpleado());

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
    
    public String obtenerNombreEmpleado(int idEmpleado) {
        String sql = "SELECT nombres, apellidos FROM Empleados WHERE id_empleado = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nombres") + " " + rs.getString("apellidos");
            }

        } catch (SQLException e) {
            System.out.println("Error nombre empleado: " + e.getMessage());
        }

        return "";
    }
    
    //VERIFICA SI EMPLEADO TIENE LICENCIA
    public boolean empleadoTieneLicencia(int idEmpleado) {
        String sql = "SELECT licencia FROM Empleados WHERE id_empleado = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String licencia = rs.getString("licencia");
                return licencia != null && !licencia.equalsIgnoreCase("SIN LICENCIA");
            }

        } catch (SQLException e) {
            System.out.println("Error licencia: " + e.getMessage());
        }

        return false;
    }
    
    //para buscar el conductor en txtBuscar
    public Object[] buscarConductor(String filtro) {

        String sql = "SELECT id_empleado, nombres, apellidos FROM Empleados " +
                     "WHERE (nombres LIKE ? OR dui LIKE ?) " +
                     "AND licencia IS NOT NULL AND licencia <> 'SIN LICENCIA'";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + filtro + "%");
            ps.setString(2, "%" + filtro + "%");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Object[]{
                    rs.getInt("id_empleado"),
                    rs.getString("nombres") + " " + rs.getString("apellidos")
                };
            }

        } catch (SQLException e) {
            System.out.println("Error buscar conductor: " + e.getMessage());
        }

        return null;
    }
    
    public List<Object[]> listarConductores(String filtro) {

        List<Object[]> lista = new ArrayList<>();

        String sql = "SELECT id_empleado, nombres + ' ' + apellidos AS nombre, dui " +
                     "FROM Empleados " +
                     "WHERE licencia IS NOT NULL AND licencia <> 'SIN LICENCIA' " +
                     "AND (nombres LIKE ? OR dui LIKE ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + filtro + "%");
            ps.setString(2, "%" + filtro + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id_empleado"),
                    rs.getString("nombre"),
                    rs.getString("dui")
                });
            }

        } catch (SQLException e) {
            System.out.println("Error listar conductores: " + e.getMessage());
        }

        return lista;
    }
    
    public boolean conductorDisponible(int idConductor, Date salida, Date regreso, int idSolicitudActual) {

        String sql = """
            SELECT COUNT(*)
            FROM Solicitudes
            WHERE id_conductor = ?
            AND estado IN ('APROBADA', 'ASIGNADA')
            AND id_solicitud <> ?
            AND NOT (
                fecha_regreso < ?
                OR fecha_salida > ?
            )
        """;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idConductor);
            ps.setInt(2, idSolicitudActual); // para edición
            ps.setDate(3, new java.sql.Date(salida.getTime()));
            ps.setDate(4, new java.sql.Date(regreso.getTime()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) == 0; // TRUE = disponible
            }

        } catch (SQLException e) {
            System.out.println("Error validar conductor: " + e.getMessage());
        }

        return false;
    }
        
    //validar que el empleado no tenga otra solicitud que choque en fechas 
    public boolean empleadoDisponible(int idEmpleado, Date salida, Date regreso, int idSolicitudActual) {

        String sql = """
            SELECT COUNT(*)
            FROM Solicitudes
            WHERE id_empleado = ?
            AND estado IN ('PENDIENTE', 'APROBADA', 'ASIGNADA')
            AND id_solicitud <> ?
            AND NOT (
                fecha_regreso < ?
                OR fecha_salida > ?
            )
        """;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            ps.setInt(2, idSolicitudActual);

            ps.setDate(3, new java.sql.Date(salida.getTime()));
            ps.setDate(4, new java.sql.Date(regreso.getTime()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) == 0;
            }

        } catch (SQLException e) {
            System.out.println("Error validar empleado: " + e.getMessage());
        }

        return false;
    }

    //==============================
    //= ROL ENCARGADO              =
    //==============================
    
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

        String sql = "UPDATE Solicitudes SET estado=?, motivo_respuesta=?, fecha_estado=GETDATE() "
                +"WHERE id_solicitud=?";

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
    
    public boolean cancelarAdmin(int idSolicitud, String motivo) {

        String sql = "UPDATE Solicitudes " +
                     "SET estado = 'CANCELADA', motivo_respuesta = ?, fecha_estado = GETDATE() " +
                     "WHERE id_solicitud = ? AND estado = 'APROBADA'";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, motivo);
            ps.setInt(2, idSolicitud);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error cancelar por encargado: " + e.getMessage());
            return false;
        }
    }
}
