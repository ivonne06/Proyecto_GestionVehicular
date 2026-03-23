package dao;

import conexion.Conexion;
import modelo.Vehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    // INSERTAR
    public boolean insertar(Vehiculo v) {
        String sql = "INSERT INTO Vehiculos (marca, modelo, placa, tipo, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getMarca());
            ps.setString(2, v.getModelo());
            ps.setString(3, v.getPlaca());
            ps.setString(4, v.getTipo());
            ps.setString(5, v.getEstado());

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
                v.setTipo(rs.getString("tipo"));
                v.setEstado(rs.getString("estado"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error listar: " + e.getMessage());
        }

        return lista;
    }

    // ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Vehiculos WHERE id_vehiculo=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error eliminar: " + e.getMessage());
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(Vehiculo v) {
        String sql = "UPDATE Vehiculos SET marca=?, modelo=?, placa=?, tipo=?, estado=? WHERE id_vehiculo=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getMarca());
            ps.setString(2, v.getModelo());
            ps.setString(3, v.getPlaca());
            ps.setString(4, v.getTipo());
            ps.setString(5, v.getEstado());
            ps.setInt(6, v.getId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error actualizar: " + e.getMessage());
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
                v.setTipo(rs.getString("tipo"));
                v.setEstado(rs.getString("estado"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error buscar: " + e.getMessage());
        }

        return lista;
    }
    
    //Verioficar que la placa sea unica 
    public boolean placaExiste(int id, String placa) {
        String sql = "SELECT COUNT(*) FROM Vehiculos WHERE placa = ? AND id_vehiculo <> ?";

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
}
