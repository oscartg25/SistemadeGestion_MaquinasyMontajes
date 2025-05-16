package com.manteniopro.vistas;

import com.manteniopro.componentes.PanelFondo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VistaReportes extends JFrame {
    private ArrayList<Object[]> listaReportes;

    public VistaReportes(ArrayList<Object[]> listaReportes) {
        this.listaReportes = listaReportes;

        setTitle("Reportes de Mantenimiento");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el PanelFondo
        PanelFondo panelFondo = new PanelFondo("/Imagenes/Inicio_de_sesion.jpg");
        panelFondo.setLayout(new BorderLayout());
        setContentPane(panelFondo);

        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Trabajador");
        modeloTabla.addColumn("Equipo");
        modeloTabla.addColumn("Tipo de Reporte");
        modeloTabla.addColumn("Mantenimiento");
        modeloTabla.addColumn("Descripción");

        for (Object[] reporte : listaReportes) {
            modeloTabla.addRow(reporte);
        }

        JTable tablaReportes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaReportes);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); // Hacer el viewport también transparente
        panelFondo.add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        btnCerrar.setOpaque(false); // Hacer el botón transparente
        panelFondo.add(btnCerrar, BorderLayout.SOUTH);
    }
}