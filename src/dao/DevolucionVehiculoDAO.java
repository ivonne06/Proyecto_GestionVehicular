package dao;

import conexion.Conexion;
import modelo.DevolucionVehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DevolucionVehiculoDAO {

    // LISTAR VEHICULOS ASIGNADOS
    public List<Object[]> listarAsignados() {

        List<Object[]> lista = new ArrayList<>();

        String sql = """
            SELECT 
                a.id_asignacion,
                v.marca + ' ' + v.modelo AS vehiculo,
                v.placa,
                e.nombres + ' ' + e.apellidos AS empleado,
                c.nombres + ' ' + c.apellidos AS conductor,
                s.destino,
                s.fecha_salida,
                s.fecha_regreso
            FROM Asignaciones a
            INNER JOIN Vehiculos v
                ON a.id_vehiculo = v.id_vehiculo
            INNER JOIN Solicitudes s
                ON a.id_solicitud = s.id_solicitud
            INNER JOIN Empleados e
                ON s.id_empleado = e.id_empleado
            INNER JOIN Empleados c
                ON s.id_conductor = c.id_empleado
            WHERE s.estado = 'ASIGNADA'
        """;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id_asignacion"),
                    rs.getString("vehiculo"),
                    rs.getString("placa"),
                    rs.getString("empleado"),
                    rs.getString("conductor"),
                    rs.getString("destino"),
                    rs.getDate("fecha_salida"),
                    rs.getDate("fecha_regreso")
                });
            }

        } catch (SQLException e) {
            System.out.println("Error listar asignados: " + e.getMessage());
        }

        return lista;
    }

    // DEVOLVER VEHICULO

    public boolean devolver(DevolucionVehiculo d) {

        String sql = "INSERT INTO DevolucionVehiculo ( id_asignacion, kilometraje_salida, "
                + "kilometraje_regreso, observaciones) VALUES (?, ?, ?, ?)";

        try (
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, d.getIdAsignacion());
            ps.setDouble(2, d.getKilometrajeSalida());
            ps.setDouble(3, d.getKilometrajeRegreso());
            ps.setString(4, d.getObservaciones());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Error devolver vehículo: "
                    + e.getMessage()
            );

            return false;
        }
    }
}