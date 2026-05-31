/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vistas;

import dao.HistorialDAO;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.itextpdf.text.BaseColor;

/**
 *
 * @author ivone
 */
public class FrmHistorialDevoluciones extends javax.swing.JInternalFrame {
    
    /**
     * Creates new form FrmHistorialSolicitudes
     */
    public FrmHistorialDevoluciones() {
        initComponents();
        cargarTabla();
        
        btnPDF = new javax.swing.JButton();

btnPDF.setText("Exportar PDF");
btnPDF.addActionListener(this::btnPDFActionPerformed);
    }
    
   private void cargarTabla() {

        DefaultTableModel modelo = (DefaultTableModel) tblHistorial.getModel();

        modelo.setRowCount(0);

        HistorialDAO dao = new HistorialDAO();

        for (Object[] fila :
                dao.historialDevoluciones(txtBuscar.getText())) {

            modelo.addRow(fila);
        }

        configurarColumnas();
    }
   
    private void configurarColumnas() {

        tblHistorial.getColumnModel().getColumn(0)
                .setPreferredWidth(35);   // ID

        tblHistorial.getColumnModel().getColumn(1)
                .setPreferredWidth(120);  // Vehiculo

        tblHistorial.getColumnModel().getColumn(2)
                .setPreferredWidth(70);   // Placa

        tblHistorial.getColumnModel().getColumn(3)
                .setPreferredWidth(120);  // Conductor

        tblHistorial.getColumnModel().getColumn(4)
                .setPreferredWidth(75);   // Salida

        tblHistorial.getColumnModel().getColumn(5)
                .setPreferredWidth(75);   // Regreso

        tblHistorial.getColumnModel().getColumn(6)
                .setPreferredWidth(60);   // Km salida

        tblHistorial.getColumnModel().getColumn(7)
                .setPreferredWidth(60);   // Km regreso

        tblHistorial.getColumnModel().getColumn(8)
                .setPreferredWidth(120);  // Devolución

        tblHistorial.getColumnModel().getColumn(9)
                .setPreferredWidth(250);  // Observaciones
    }
    
    
    private void generarPDF() {

    JFileChooser chooser = new JFileChooser();

    chooser.setSelectedFile(
            new File("Historial_Devoluciones.pdf"));

    int opcion = chooser.showSaveDialog(this);

    if (opcion != JFileChooser.APPROVE_OPTION) {
        return;
    }

    String ruta = chooser.getSelectedFile().getAbsolutePath();

    if (!ruta.toLowerCase().endsWith(".pdf")) {
        ruta += ".pdf";
    }

    try {

        Document documento =
                new Document(PageSize.A4.rotate());

        PdfWriter.getInstance(
                documento,
                new FileOutputStream(ruta));

        documento.open();

        // ==========================
        // LOGO
        // ==========================

        try {

            InputStream is =
                    getClass().getResourceAsStream(
                            "/img/logo.png");

            if (is != null) {

                byte[] bytes = is.readAllBytes();

                Image logo =
                        Image.getInstance(bytes);

                logo.scaleToFit(90, 90);
                logo.setAlignment(Image.ALIGN_CENTER);

                documento.add(logo);
            }

        } catch (Exception e) {

            System.out.println(
                    "Logo no encontrado");
        }

        // ==========================
        // TITULO
        // ==========================

        Font fuenteTitulo =
                new Font(
                        Font.FontFamily.HELVETICA,
                        18,
                        Font.BOLD,
                        new BaseColor(41, 128, 185));

        Paragraph titulo =
                new Paragraph(
                        "SISTEMA DE GESTIÓN VEHICULAR\n"
                        + "HISTORIAL DE DEVOLUCIONES",
                        fuenteTitulo);

        titulo.setAlignment(
                Element.ALIGN_CENTER);

        documento.add(titulo);

        documento.add(new Paragraph(" "));

        Paragraph fecha =
                new Paragraph(
                        "Fecha de generación: "
                        + new java.text.SimpleDateFormat(
                                "dd/MM/yyyy HH:mm:ss")
                                .format(
                                        new java.util.Date()));

        fecha.setAlignment(
                Element.ALIGN_RIGHT);

        documento.add(fecha);

        documento.add(new Paragraph(" "));
        documento.add(new Paragraph(" "));

        // ==========================
        // TABLA
        // ==========================

        PdfPTable tabla =
                new PdfPTable(
                        tblHistorial.getColumnCount());

        tabla.setWidthPercentage(100);

float[] anchos = new float[
        tblHistorial.getColumnCount()
];

for (int i = 0;
        i < tblHistorial.getColumnCount();
        i++) {

    anchos[i] =
            tblHistorial
                    .getColumnModel()
                    .getColumn(i)
                    .getPreferredWidth();
}

tabla.setWidths(anchos);

        // ==========================
        // ENCABEZADOS
        // ==========================

        Font fuenteEncabezado =
                new Font(
                        Font.FontFamily.HELVETICA,
                        10,
                        Font.BOLD,
                        BaseColor.WHITE);

        for (int i = 0;
                i < tblHistorial.getColumnCount();
                i++) {

            PdfPCell celda =
                    new PdfPCell(
                            new Phrase(
                                    tblHistorial.getColumnName(i),
                                    fuenteEncabezado));

            celda.setBackgroundColor(
                    new BaseColor(52, 73, 94));

            celda.setHorizontalAlignment(
                    Element.ALIGN_CENTER);

            celda.setVerticalAlignment(
                    Element.ALIGN_MIDDLE);

            celda.setPadding(8);

            tabla.addCell(celda);
        }

        // ==========================
        // DATOS
        // ==========================

        for (int fila = 0;
                fila < tblHistorial.getRowCount();
                fila++) {

            for (int col = 0;
                    col < tblHistorial.getColumnCount();
                    col++) {

                Object valor =
                        tblHistorial.getValueAt(
                                fila,
                                col);

                PdfPCell celda =
                        new PdfPCell(
                                new Phrase(
                                        valor == null
                                        ? ""
                                        : valor.toString()));

                celda.setPadding(5);

                if (fila % 2 == 0) {

                    celda.setBackgroundColor(
                            new BaseColor(245, 245, 245));
                }

                if (col == 0) {

                    celda.setHorizontalAlignment(
                            Element.ALIGN_CENTER);
                }

                tabla.addCell(celda);
            }
        }

        documento.add(tabla);

        documento.add(new Paragraph(" "));
        documento.add(new Paragraph(" "));

        Paragraph pie =
                new Paragraph(
                        "Reporte generado automáticamente por el Sistema de Gestión Vehicular.");

        pie.setAlignment(
                Element.ALIGN_CENTER);

        documento.add(pie);

        documento.close();

        JOptionPane.showMessageDialog(
                this,
                "PDF generado correctamente.");

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Error al generar PDF:\n"
                + e.getMessage());
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
        tblHistorial = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnPDF = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        tblHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Vehículo", "Placa", "Conductor", "Salida", "Regreso", "Km Sal.", "Km Reg.", "Devolución", "Observaciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblHistorial);

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel7.setText("Buscar vehículo, placa, conductor u observación:");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("HISTORIAL DE DEVOLUCIONES");

        btnPDF.setText("Generar PDF");
        btnPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(348, 348, 348))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtBuscar)
                        .addGap(309, 309, 309))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(btnPDF))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPDF)
                .addGap(7, 7, 7))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        cargarTabla();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        // TODO add your handling code here:
        generarPDF();
    }//GEN-LAST:event_btnPDFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPDF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblHistorial;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
