/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto_gestionvehicular;

import conexion.Conexion;
import java.sql.Connection;
import util.Seeder;
import vistas.FrmLogin;

public class Proyecto_GestionVehicular {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection con = Conexion.getConexion();
        
        if (con != null) {
            System.out.println("TODO FUNCIONA");

            // seeder para usuario admin
            Seeder.seedAdmin();

            java.awt.EventQueue.invokeLater(() -> {
                new FrmLogin().setVisible(true);
            });

        } else {
            System.out.println("NO CONECTA");
        }
    }
    
}
