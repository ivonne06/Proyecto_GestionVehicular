package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BD_GestionVehicular;encrypt=false";
    private static final String USER = "sa";       // usuario SQL Server
    private static final String PASSWORD = "1234"; // tu contraseña
    
    public static Connection getConexion() {
        Connection con = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Conectar
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Conectado a SQL Server correctamente");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Error de conexión");
            e.printStackTrace();
        }

        return con;
    }
}
