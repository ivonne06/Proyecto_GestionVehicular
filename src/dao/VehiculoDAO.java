package dao;

import conexion.Conexion;
import modelo.Vehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    // INSERTAR
    public boolean insertar(Vehiculo v) {

        String sql = "INSERT INTO Vehiculos (marca, modelo, placa, pasajeros, tipo, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getMarca());
            ps.setString(2, v.getModelo());
            ps.setString(3, v.getPlaca());
            ps.setInt(4, v.getPasajeros());
            ps.setString(5, v.getTipo());
            ps.setString(6, v.getEstado());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error insertar: " + e.getMessage());
            return false;
        }
    }

    // LISTAR
    public List<Vehiculo> listar() {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Vehiculos";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setId(rs.getInt("id_vehiculo"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setPlaca(rs.getString("placa"));
                v.setPasajeros(rs.getInt("pasajeros"));
                v.setTipo(rs.getString("tipo"));
                v.setEstado(rs.getString("estado"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error listar: " + e.getMessage());
        }

        return lista;
    }

    // ACTUALIZAR
    public boolean actualizar(Vehiculo v) {
        String sql = "UPDATE Vehiculos SET marca=?, modelo=?, placa=?, pasajeros=?, tipo=?, estado=? "
                   + "WHERE id_vehiculo=? AND estado IN ('DISPONIBLE', 'MANTENIMIENTO')";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (placaExiste(v.getId(), v.getPlaca())) {
                throw new SQLException("La placa ya está registrada");
            }

            ps.setString(1, v.getMarca());
            ps.setString(2, v.getModelo());
            ps.setString(3, v.getPlaca());
            ps.setInt(4, v.getPasajeros());
            ps.setString(5, v.getTipo());
            ps.setString(6, v.getEstado());
            ps.setInt(7, v.getId());

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se puede actualizar este vehículo");
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error actualizar: " + e.getMessage());
            return false;
        }
    }

    // INHABILITAR
    public boolean inhabilitar(int id) {
        String sql = "UPDATE Vehiculos SET estado='INHABILITADO' WHERE id_vehiculo=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se puede inhabilitar");
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error inhabilitar: " + e.getMessage());
            return false;
        }
    }

    // HABILITAR
    public boolean habilitar(int id) {
        String sql = "UPDATE Vehiculos SET estado='DISPONIBLE' WHERE id_vehiculo=? AND estado='INHABILITADO'";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se puede habilitar");
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error habilitar: " + e.getMessage());
            return false;
        }
    }

    // BUSCAR POR PLACA
    public List<Vehiculo> buscarPorPlaca(String placa) {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Vehiculos WHERE placa LIKE ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + placa + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setId(rs.getInt("id_vehiculo"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setPlaca(rs.getString("placa"));
                v.setPasajeros(rs.getInt("pasajeros"));
                v.setTipo(rs.getString("tipo"));
                v.setEstado(rs.getString("estado"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error buscar: " + e.getMessage());
        }

        return lista;
    }

    // VALIDAR PLACA ÚNICA
    public boolean placaExiste(int id, String placa) {
        String sql = "SELECT COUNT(*) FROM Vehiculos WHERE placa=? AND id_vehiculo<>?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, placa);
            ps.setInt(2, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error validar placa: " + e.getMessage());
        }

        return false;
    }
    
    public Vehiculo buscarPorId(int id) {
        String sql = "SELECT * FROM Vehiculos WHERE id_vehiculo = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setId(rs.getInt("id_vehiculo"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setPlaca(rs.getString("placa"));
                v.setPasajeros(rs.getInt("pasajeros"));
                v.setTipo(rs.getString("tipo"));
                v.setEstado(rs.getString("estado"));
                return v;
            }

        } catch (SQLException e) {
            System.out.println("Error buscar por id: " + e.getMessage());
        }

        return null;
    }
  
    public List<Vehiculo> listarDisponibles(java.util.Date salida, java.util.Date regreso, int pasajeros) {

    List<Vehiculo> lista = new ArrayList<>();

  String sql = "{CALL sp_vehiculos_disponibles_v2(?, ?, ?)}";

    try (Connection con = Conexion.getConexion();
         CallableStatement cs = con.prepareCall(sql)) {

        cs.setDate(1, new java.sql.Date(salida.getTime()));
        cs.setDate(2, new java.sql.Date(regreso.getTime()));
        cs.setInt(3, pasajeros);

        ResultSet rs = cs.executeQuery();

        while (rs.next()) {
            Vehiculo v = new Vehiculo();
            v.setId(rs.getInt("id_vehiculo"));
            v.setMarca(rs.getString("marca"));
            v.setModelo(rs.getString("modelo"));
            v.setPlaca(rs.getString("placa"));
            v.setPasajeros(rs.getInt("pasajeros"));
            v.setTipo(rs.getString("tipo"));

            lista.add(v);
        }

    } catch (SQLException e) {
        System.out.println("Error listar disponibles: " + e.getMessage());
    }

    return lista;
}
    public boolean estaEnUso(int idVehiculo) {

    String sql = """
        SELECT COUNT(*)
        FROM Asignaciones a
        JOIN Solicitudes s ON a.id_solicitud = s.id_solicitud
        WHERE a.id_vehiculo = ?
        AND CAST(GETDATE() AS DATE) BETWEEN s.fecha_salida AND s.fecha_regreso
    """;

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idVehiculo);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (SQLException e) {
        System.out.println("Error validar uso: " + e.getMessage());
    }

    return false;
}
  
}
