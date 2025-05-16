package com.manteniopro.vistas;

import com.manteniopro.componentes.PanelFondo;
import com.manteniopro.model.EquipoIzaje;
import com.manteniopro.model.Usuario;
import com.manteniopro.util.UtilidadesArchivo;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime; // Importar para la fecha y hora
import java.time.format.DateTimeFormatter; // Importar para formatear fecha y hora

public class VistaReporteMantenimiento extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(VistaReporteMantenimiento.class.getName());

    private List<EquipoIzaje> listaEquipos;
    private List<Object[]> listaReportes;
    private Usuario trabajador;

    public VistaReporteMantenimiento(List<EquipoIzaje> listaEquipos, List<Object[]> listaReportes, Usuario trabajador) {
        this.listaEquipos = listaEquipos;
        this.listaReportes = listaReportes;
        this.trabajador = trabajador;

        setTitle("Nuevo Reporte de Mantenimiento");
        setSize(400, 350); // Aumentar el tamaño para la fecha
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el PanelFondo
        PanelFondo panelFondo = new PanelFondo("/Imagenes/Inicio_de_sesion.jpg");
        panelFondo.setLayout(new GridLayout(6, 2, 10, 10)); // Añadimos espacio entre componentes
        setContentPane(panelFondo);

        JComboBox<String> comboEquipos = new JComboBox<>();
        comboEquipos.setOpaque(false);
        for (EquipoIzaje equipo : listaEquipos) {
            comboEquipos.addItem(equipo.getNombre());
        }

        JComboBox<String> comboTipoReporte = new JComboBox<>(new String[]{"Reporte General", "Mantenimiento"});
        comboTipoReporte.setOpaque(false);
        JComboBox<String> comboMantenimiento = new JComboBox<>(new String[]{"Preventivo", "Correctivo"});
        comboMantenimiento.setEnabled(false);
        comboMantenimiento.setOpaque(false);

        JTextField txtDescripcion = new JTextField();
        txtDescripcion.setOpaque(false);
        txtDescripcion.setForeground(Color.WHITE);
        txtDescripcion.setCaretColor(Color.WHITE);

        JButton btnEnviar = new JButton("Enviar Reporte");
        btnEnviar.setOpaque(false);
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setOpaque(false);

        JLabel lblEquipo = new JLabel("Seleccionar Equipo:");
        lblEquipo.setForeground(Color.WHITE);
        lblEquipo.setOpaque(false);
        panelFondo.add(lblEquipo);
        panelFondo.add(comboEquipos);

        JLabel lblTipoReporte = new JLabel("Tipo de Reporte:");
        lblTipoReporte.setForeground(Color.WHITE);
        lblTipoReporte.setOpaque(false);
        panelFondo.add(lblTipoReporte);
        panelFondo.add(comboTipoReporte);

        JLabel lblMantenimiento = new JLabel("Tipo de Mantenimiento:");
        lblMantenimiento.setForeground(Color.WHITE);
        lblMantenimiento.setOpaque(false);
        panelFondo.add(lblMantenimiento);
        panelFondo.add(comboMantenimiento);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setForeground(Color.WHITE);
        lblDescripcion.setOpaque(false);
        panelFondo.add(lblDescripcion);
        panelFondo.add(txtDescripcion);

        panelFondo.add(new JLabel("")); // Espacio
        panelFondo.add(btnEnviar);
        panelFondo.add(new JLabel("")); // Espacio
        panelFondo.add(btnCancelar);

        comboTipoReporte.addActionListener(e -> {
            comboMantenimiento.setEnabled(comboTipoReporte.getSelectedItem().equals("Mantenimiento"));
        });

        btnEnviar.addActionListener(e -> {
            String equipoSeleccionado = comboEquipos.getSelectedItem().toString();
            String tipoReporte = comboTipoReporte.getSelectedItem().toString();
            String tipoMantenimiento = comboMantenimiento.isEnabled() ? comboMantenimiento.getSelectedItem().toString() : "";
            String descripcion = txtDescripcion.getText();

            // Obtener la fecha y hora actual
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHora = ahora.format(formateador);

            Object[] nuevoReporte = {trabajador.getNombre(), equipoSeleccionado, tipoReporte, tipoMantenimiento, descripcion, fechaHora};

            listaReportes.add(nuevoReporte);

            try {
                UtilidadesArchivo.guardarEnArchivo(new ArrayList<>(listaReportes), "reportes.dat");
                JOptionPane.showMessageDialog(this, "Reporte enviado correctamente.");
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el reporte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.SEVERE, "Error al guardar el reporte: " + ex.getMessage(), ex);
                ex.printStackTrace();
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}