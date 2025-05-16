package com.manteniopro.vistas;

import com.manteniopro.model.EquipoIzaje;
import com.manteniopro.util.UtilidadesArchivo;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VistaAgregarEquipoIzaje extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(VistaAgregarEquipoIzaje.class.getName());

    private List<EquipoIzaje> listaEquipos; // Recibe la lista existente

    private JTextField txtNombre = new JTextField(20);
    private JTextField txtModelo = new JTextField(20);
    private JTextField txtCapacidad = new JTextField(20);

    public VistaAgregarEquipoIzaje(List<EquipoIzaje> equiposExistentes) {
        this.listaEquipos = equiposExistentes;
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Agregar Equipo de Izaje");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Modelo:"));
        add(txtModelo);
        add(new JLabel("Capacidad:"));
        add(txtCapacidad);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarEquipo());
        add(new JLabel()); // Para espaciado
        add(btnGuardar);
    }

    private void guardarEquipo() {
        String nombre = txtNombre.getText();
        String modelo = txtModelo.getText();
        String capacidadStr = txtCapacidad.getText();

        if (nombre.isEmpty() || modelo.isEmpty() || capacidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double capacidad = Double.parseDouble(capacidadStr);
            // Crear un nuevo EquipoIzaje (asumiendo que tienes un constructor adecuado)
            // Corregido el error aquí: convertir el ID a String
            String nuevoId = String.valueOf(listaEquipos.size() + 1);
            EquipoIzaje nuevoEquipo = new EquipoIzaje(nuevoId, nombre, modelo, capacidad); // Genera un nuevo ID

            listaEquipos.add(nuevoEquipo); // Añade el nuevo equipo a la lista

            try {
                UtilidadesArchivo.guardarEnArchivo(new ArrayList<>(listaEquipos), "equipos.dat"); // Guarda la lista completa
                JOptionPane.showMessageDialog(this, "Equipo guardado correctamente.");
                dispose(); // Cierra la ventana
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el equipo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.SEVERE, "Error al guardar el equipo: " + ex.getMessage(), ex);
                ex.printStackTrace();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Capacidad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}