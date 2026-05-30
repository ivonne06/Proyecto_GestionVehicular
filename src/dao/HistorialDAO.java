package dao;

import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<Object[]> historialAsignaciones() {

    List<Object[]> lista = new ArrayList<>();

    String sql = """
        SELECT

            a.id_asignacion,

            v.marca + ' ' +
            v.modelo + ' (' +
            v.placa + ')' AS vehiculo,

            es.nombres + ' ' + es.apellidos AS solicitante,

            ec.nombres + ' ' + ec.apellidos AS conductor,

            u.username AS asignado_por,

            s.fecha_salida,
            s.fecha_regreso,

            a.fecha_asignacion

        FROM Asignaciones a

        INNER JOIN Solicitudes s
            ON a.id_solicitud = s.id_solicitud

        INNER JOIN Vehiculos v
            ON a.id_vehiculo = v.id_vehiculo

        INNER JOIN Empleados es
            ON s.id_empleado = es.id_empleado

        INNER JOIN Empleados ec
            ON s.id_conductor = ec.id_empleado

        INNER JOIN Usuarios u
            ON a.id_usuario_asigno = u.id_usuario

        ORDER BY a.fecha_asignacion DESC
    """;

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            lista.add(new Object[]{

                rs.getInt("id_asignacion"),
                rs.getString("vehiculo"),
                rs.getString("solicitante"),
                rs.getString("conductor"),
                rs.getString("asignado_por"),
                rs.getDate("fecha_salida"),
                rs.getDate("fecha_regreso"),
                rs.getTimestamp("fecha_asignacion")

            });

        }

    } catch (SQLException e) {

        System.out.println(
            "Error historial asignaciones: "
            + e.getMessage()
        );

    }

    return lista;
}
}