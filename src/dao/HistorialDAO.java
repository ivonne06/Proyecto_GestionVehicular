package dao;

import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

public class HistorialDAO {

    public List<Object[]> historialSolicitudesEmpleado(int idEmpleado, String filtro,String estado) {

        List<Object[]> lista = new ArrayList<>();

        String sql = """
            SELECT
                s.id_solicitud,
                e.nombres + ' ' + e.apellidos AS empleado,
                c.nombres + ' ' + c.apellidos AS conductor,
                s.destino,
                s.motivo_respuesta,
                s.fecha_salida,
                s.fecha_regreso,
                s.estado
            FROM Solicitudes s
            INNER JOIN Empleados e
                ON s.id_empleado = e.id_empleado
           
            INNER JOIN Empleados c
                ON s.id_conductor = c.id_empleado
                     
            WHERE s.id_empleado = ?

            AND (
                c.nombres LIKE ?
                OR s.destino LIKE ?
            )

            AND (
                ? = 'TODOS'
                OR s.estado = ?
            )

            AND s.estado IN (
                'FINALIZADA',
                'RECHAZADA',
                'CANCELADA'
            )

            ORDER BY s.fecha_estado DESC
        """;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);

            ps.setString(2, "%" + filtro + "%");
            ps.setString(3, "%" + filtro + "%");

            ps.setString(4, estado);
            ps.setString(5, estado);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                lista.add(new Object[]{
                    rs.getInt("id_solicitud"),
                    rs.getString("empleado"),
                    rs.getString("conductor"),
                    rs.getString("destino"),
                    rs.getString("motivo_respuesta"),
                    rs.getDate("fecha_salida"),
                    rs.getDate("fecha_regreso"),
                    rs.getString("estado")
                });
            }

        } catch (SQLException e) {
            System.out.println("Error historial empleado: " + e.getMessage());
        }

        return lista;
    }

    public List<Object[]> historialSolicitudesAdmin(String filtro, String estado) {

        List<Object[]> lista = new ArrayList<>();

        String sql = """
            SELECT
                s.id_solicitud,
                e.nombres + ' ' + e.apellidos AS empleado,
                c.nombres + ' ' + c.apellidos AS conductor,
                s.destino,
                s.motivo_respuesta,
                s.fecha_salida,
                s.fecha_regreso,
                s.estado
            FROM Solicitudes s

            INNER JOIN Empleados e
                ON s.id_empleado = e.id_empleado

            INNER JOIN Empleados c
                ON s.id_conductor = c.id_empleado

            WHERE (
                e.nombres LIKE ?
                OR c.nombres LIKE ?
                OR s.destino LIKE ?
            )

            AND (
                ? = 'TODOS'
                OR s.estado = ?
            )

            AND s.estado IN (
                'FINALIZADA',
                'RECHAZADA',
                'CANCELADA'
            )

            ORDER BY s.fecha_estado DESC
        """;

        try (Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + filtro + "%");
            ps.setString(2, "%" + filtro + "%");
            ps.setString(3, "%" + filtro + "%");

            ps.setString(4, estado);
            ps.setString(5, estado);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                lista.add(new Object[]{
                    rs.getInt("id_solicitud"),
                    rs.getString("empleado"),
                    rs.getString("conductor"),
                    rs.getString("destino"),
                    rs.getString("motivo_respuesta"),
                    rs.getDate("fecha_salida"),
                    rs.getDate("fecha_regreso"),
                    rs.getString("estado")
                });
            }

        } catch (SQLException e) {
            System.out.println("Error historial admin: " + e.getMessage());
        }

        return lista;
    }
    
    public List<Object[]> historialDevoluciones(String filtro) {
        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        List<Object[]> lista = new ArrayList<>();

        String sql = """
            SELECT
                d.id_devolucion,

                v.marca + ' ' + v.modelo AS vehiculo,
                v.placa,

                c.nombres + ' ' + c.apellidos AS conductor,

                s.fecha_salida,
                s.fecha_regreso,

                d.kilometraje_salida,
                d.kilometraje_regreso,

                d.fecha_devolucion,

                d.observaciones

            FROM DevolucionVehiculo d

            INNER JOIN Asignaciones a
                ON d.id_asignacion = a.id_asignacion

            INNER JOIN Vehiculos v
                ON a.id_vehiculo = v.id_vehiculo

            INNER JOIN Solicitudes s
                ON a.id_solicitud = s.id_solicitud

            INNER JOIN Empleados c
                ON s.id_conductor = c.id_empleado

            WHERE (
                v.marca + ' ' + v.modelo LIKE ?
                OR v.placa LIKE ?
                OR c.nombres + ' ' + c.apellidos LIKE ?
                OR ISNULL(d.observaciones, '') LIKE ?
            )

            ORDER BY d.fecha_devolucion DESC
        """;

        try (
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            String busqueda = "%" + filtro + "%";

            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);
            ps.setString(4, busqueda);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id_devolucion"),
                    rs.getString("vehiculo"),
                    rs.getString("placa"),
                    rs.getString("conductor"),

                    sdfFecha.format(rs.getDate("fecha_salida")),
                    sdfFecha.format(rs.getDate("fecha_regreso")),

                    rs.getDouble("kilometraje_salida"),
                    rs.getDouble("kilometraje_regreso"),

                    sdfFechaHora.format(
                            rs.getTimestamp("fecha_devolucion")
                    ),

                    rs.getString("observaciones")
                });
            }
        } catch (SQLException e) {

            System.out.println(
                "Error historial devoluciones: "
                + e.getMessage()
            );
        }

        return lista;
    }
}