package com.manteniopro.vistas;

import com.manteniopro.componentes.PanelFondo;
import com.manteniopro.model.EquipoIzaje;
import com.manteniopro.util.UtilidadesArchivo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VistaEquipoIzaje extends JFrame {

    private List<EquipoIzaje> equipos = new ArrayList<>();
    private DefaultTableModel modeloTabla;

    public VistaEquipoIzaje(List<EquipoIzaje> listaEquipos) {
        this.equipos.addAll(listaEquipos);
        initComponents();
        actualizarTabla();
        setVisible(true);
    }

    public VistaEquipoIzaje() {
        this.equipos = new ArrayList<>();
        initComponents();
        cargarEquipos();
        actualizarTabla();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Equipos de Izaje");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el PanelFondo
        PanelFondo panelFondo = new PanelFondo("/Imagenes/Inicio_de_sesion.jpg");
        panelFondo.setLayout(new BorderLayout());
        setContentPane(panelFondo);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Modelo");
        modeloTabla.addColumn("Capacidad");
        modeloTabla.addColumn("Estado");

        JTable tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); // Hacer el viewport también transparente
        panelFondo.add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> {
            dispose();
            guardarEquipos();
        });
        btnCerrar.setOpaque(false); // Hacer el botón transparente
        panelFondo.add(btnCerrar, BorderLayout.SOUTH);
    }

    private void cargarEquipos() {
        try {
            List<?> tempEquipos = UtilidadesArchivo.leerDesdeArchivo("equipos.dat");
            if (tempEquipos != null) {
                equipos = tempEquipos.stream()
                        .filter(EquipoIzaje.class::isInstance)
                        .map(EquipoIzaje.class::cast)
                        .collect(Collectors.toList());
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los equipos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void guardarEquipos() {
        try {
            UtilidadesArchivo.guardarEnArchivo(new ArrayList<>(equipos), "equipos.dat");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar los equipos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (EquipoIzaje equipo : equipos) {
            modeloTabla.addRow(new Object[]{equipo.getId(), equipo.getNombre(), equipo.getModelo(), equipo.getCapacidad(), equipo.getEstado()});
        }
    }
}