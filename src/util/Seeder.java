package util;

import conexion.Conexion;
import java.sql.*;
import util.HashUtil;

public class Seeder {
     public static void seedAdmin() {
        Connection con = Conexion.getConexion();

        try {
            // Verificar si ya existe
            String checkQuery = "SELECT COUNT(*) FROM Usuarios WHERE username = ?";
            PreparedStatement psCheck = con.prepareStatement(checkQuery);
            psCheck.setString(1, "admin");

            ResultSet rs = psCheck.executeQuery();
            rs.next();

            if (rs.getInt(1) == 0) {
                // Insertar admin
                String insertQuery = "INSERT INTO Usuarios (username, password, rol, estado, debe_cambiar_password) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psInsert = con.prepareStatement(insertQuery);

                psInsert.setString(1, "admin");
                psInsert.setString(2, HashUtil.sha256("1234").toUpperCase());
                psInsert.setString(3, "ADMIN");
                psInsert.setInt(4, 1);
                psInsert.setInt(5, 0);

                psInsert.executeUpdate();

                System.out.println("Usuario admin creado");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
