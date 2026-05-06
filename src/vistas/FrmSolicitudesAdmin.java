package vistas;

import dao.SolicitudDao;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import modelo.Usuario;

public class FrmSolicitudesAdmin extends javax.swing.JInternalFrame {
    
    private Usuario usuario;
    private SolicitudDao dao = new SolicitudDao();
    
    public FrmSolicitudesAdmin(Usuario usr) {
        initComponents();
        this.usuario = usr;
        validarRol();
        cargarTabla();
    }
    
    public void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblSolicitudes.getModel();
        modelo.setRowCount(0);

        for (Object[] fila : dao.listarTodas()) {
            modelo.addRow(fila);
        }
        
        btnAprovar.setEnabled(false);
        btnRechazar.setEnabled(false);
        btnCancelar.setEnabled(false);

    }
    
    private void validarRol() {
        if (!usuario.getRol().equalsIgnoreCase("ADMIN") &&
            !usuario.getRol().equalsIgnoreCase("ENCARGADO")) {

            JOptionPane.showMessageDialog(this,
                    "No tiene permisos para acceder a este módulo");
            this.dispose();
        }
    }
    
    private int obtenerIdSeleccionado() {
        int fila = tblSolicitudes.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una solicitud de la tabla");
            return -1;
        }

        String estado = tblSolicitudes.getValueAt(fila, 7).toString();

        return Integer.parseInt(tblSolicitudes.getValueAt(fila, 0).toString());
    }
    
    private void procesarSolicitud(String accion) {

        int id = obtenerIdSeleccionado();
        if (id == -1) return;

        String estado = tblSolicitudes.getValueAt(tblSolicitudes.getSelectedRow(), 7).toString();

        String motivo = JOptionPane.showInputDialog(this,
                "Ingrese motivo de " + accion.toLowerCase() + ":");

        if (motivo == null || motivo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un motivo");
            return;
        }

        boolean ok = false;

        switch (accion) {

            case "APROBAR":
                if (!estado.equalsIgnoreCase("PENDIENTE")) {
                    JOptionPane.showMessageDialog(this, "Solo se pueden aprobar solicitudes pendientes");
                    return;
                }
                ok = dao.aprobar(id, motivo);
                break;

            case "RECHAZAR":
                if (!estado.equalsIgnoreCase("PENDIENTE")) {
                    JOptionPane.showMessageDialog(this, "Solo se pueden rechazar solicitudes pendientes");
                    return;
                }
                ok = dao.rechazar(id, motivo);
                break;

            case "CANCELAR":
                if (estado.equalsIgnoreCase("FINALIZADA") || estado.equalsIgnoreCase("CANCELADA")) {
                    JOptionPane.showMessageDialog(this, "No se puede cancelar esta solicitud");
                    return;
                }
                ok = dao.cancelarAdmin(id, motivo); // el método unificado que te dije
                break;
        }

        if (ok) {
            JOptionPane.showMessageDialog(this, "Solicitud " + accion.toLowerCase() + " correctamente");
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al " + accion.toLowerCase());
        }
    }
    
    private void controlarBotones() {

        int fila = tblSolicitudes.getSelectedRow();

        if (fila == -1) {
            btnAprovar.setEnabled(false);
            btnRechazar.setEnabled(false);
            btnCancelar.setEnabled(false);
            return;
        }

        String estado = tblSolicitudes.getValueAt(fila, 7).toString();

        switch (estado.toUpperCase()) {

            case "PENDIENTE":
                btnAprovar.setEnabled(true);
                btnRechazar.setEnabled(true);
                btnCancelar.setEnabled(false);
                break;

            case "APROBADA":
                btnAprovar.setEnabled(false);
                btnRechazar.setEnabled(false);
                btnCancelar.setEnabled(true);
                break;

            case "RECHAZADA":
                btnAprovar.setEnabled(false);
                btnRechazar.setEnabled(false);
                btnCancelar.setEnabled(false);
                break;

            case "FINALIZADA":
                btnAprovar.setEnabled(false);
                btnRechazar.setEnabled(false);
                btnCancelar.setEnabled(false);
                break;

            case "CANCELADA":
                btnAprovar.setEnabled(false);
                btnRechazar.setEnabled(false);
                btnCancelar.setEnabled(false);
                break;

            default:
                btnAprovar.setEnabled(false);
                btnRechazar.setEnabled(false);
                btnCancelar.setEnabled(false);
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblSolicitudes = new javax.swing.JTable();
        btnAprovar = new javax.swing.JButton();
        btnRechazar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        tblSolicitudes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Empleado", "Destino", "Motivo", "Pasajeros", "Salida", "Regreso", "Estado"
            }
        ));
        tblSolicitudes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSolicitudesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSolicitudes);

        btnAprovar.setText("Aprobar");
        btnAprovar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAprovarActionPerformed(evt);
            }
        });

        btnRechazar.setText("Rechazar");
        btnRechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRechazarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("GESTIÓN DE SOLICITUDES");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(btnAprovar)
                        .addGap(49, 49, 49)
                        .addComponent(btnRechazar)
                        .addGap(54, 54, 54)
                        .addComponent(btnCancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAprovar)
                    .addComponent(btnRechazar)
                    .addComponent(btnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAprovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAprovarActionPerformed
        procesarSolicitud("APROBAR");
    }//GEN-LAST:event_btnAprovarActionPerformed

    private void btnRechazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRechazarActionPerformed
         procesarSolicitud("RECHAZAR");
    }//GEN-LAST:event_btnRechazarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
      procesarSolicitud("CANCELAR");
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tblSolicitudesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSolicitudesMouseClicked
         controlarBotones();
    }//GEN-LAST:event_tblSolicitudesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAprovar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRechazar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSolicitudes;
    // End of variables declaration//GEN-END:variables
}
