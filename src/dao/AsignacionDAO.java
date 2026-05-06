package dao;

import conexion.Conexion;
import java.sql.*;

public class AsignacionDAO {

    public boolean asignarVehiculo(int idSolicitud, int idVehiculo, int idUsuario) {

        String sql = "{CALL dbo.sp_asignar_vehiculo(?, ?, ?)}";

        try (Connection con = Conexion.getConexion();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idSolicitud);
            cs.setInt(2, idVehiculo);
            cs.setInt(3, idUsuario);

            cs.execute();

            return true;

        } catch (SQLException e) {

            System.out.println("Error asignación: " + e.getMessage());

            return false;
        }
    }
}