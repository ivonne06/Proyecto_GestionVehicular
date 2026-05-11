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
}