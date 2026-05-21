package dao;

import conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    //==================================
    // RESUMEN SOLICITUDES
    //==================================
    public Object[] resumenSolicitudes() {

        String sql = """
            SELECT
            COUNT(*) total,

            SUM(CASE WHEN estado='APROBADA' THEN 1 ELSE 0 END) aprobadas,

            SUM(CASE WHEN estado='RECHAZADA' THEN 1 ELSE 0 END) rechazadas,

            SUM(CASE WHEN estado='CANCELADA' THEN 1 ELSE 0 END) canceladas,

            SUM(CASE WHEN estado='FINALIZADA' THEN 1 ELSE 0 END) finalizadas

            FROM Solicitudes
        """;

        try (
                Connection con=Conexion.getConexion();
                Statement st=con.createStatement();
                ResultSet rs=st.executeQuery(sql)
        ){

            if(rs.next()){

                return new Object[]{
                    rs.getInt("total"),
                    rs.getInt("aprobadas"),
                    rs.getInt("rechazadas"),
                    rs.getInt("canceladas"),
                    rs.getInt("finalizadas")
                };

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


    //==================================
    // VEHICULOS MAS USADOS
    //==================================

    public List<Object[]> vehiculosMasUsados(){

        List<Object[]> lista=new ArrayList<>();

        String sql="""
            SELECT
            v.placa,
            v.modelo,
            COUNT(*) viajes

            FROM Asignaciones a

            JOIN Vehiculos v
            ON a.id_vehiculo=v.id_vehiculo

            GROUP BY
            v.placa,
            v.modelo

            ORDER BY viajes DESC
        """;

        try(
                Connection con=Conexion.getConexion();
                Statement st=con.createStatement();
                ResultSet rs=st.executeQuery(sql)
        ){

            while(rs.next()){

                lista.add(new Object[]{

                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getInt("viajes")

                });

            }

        }catch(Exception e){

            System.out.println(e.getMessage());

        }

        return lista;

    }


    //==================================
    // CONDUCTORES CON MAS VIAJES
    //==================================

    public List<Object[]> conductoresMasViajes(){

        List<Object[]> lista=new ArrayList<>();

        String sql="""
            SELECT
            e.nombres+' '+e.apellidos conductor,

            COUNT(*) viajes

            FROM Solicitudes s

            JOIN Empleados e

            ON s.id_conductor=e.id_empleado

            WHERE s.estado='FINALIZADA'

            GROUP BY
            e.nombres,
            e.apellidos

            ORDER BY viajes DESC
        """;

        try(
                Connection con=Conexion.getConexion();
                Statement st=con.createStatement();
                ResultSet rs=st.executeQuery(sql)
        ){

            while(rs.next()){

                lista.add(new Object[]{

                        rs.getString("conductor"),
                        rs.getInt("viajes")

                });

            }

        }catch(Exception e){

        }

        return lista;

    }


    //==================================
    // DESTINOS MAS FRECUENTES
    //==================================

    public List<Object[]> destinosFrecuentes(){

        List<Object[]> lista=new ArrayList<>();

        String sql="""
            SELECT
            destino,
            COUNT(*) cantidad

            FROM Solicitudes

            GROUP BY destino

            ORDER BY cantidad DESC
        """;

        try(
                Connection con=Conexion.getConexion();
                Statement st=con.createStatement();
                ResultSet rs=st.executeQuery(sql)
        ){

            while(rs.next()){

                lista.add(new Object[]{

                        rs.getString("destino"),
                        rs.getInt("cantidad")

                });

            }

        }catch(Exception e){}

        return lista;

    }

}
