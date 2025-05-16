// VistaAsignarTareas.java
package com.manteniopro.vistas;

import com.manteniopro.componentes.PanelFondo;
import com.manteniopro.model.Tarea;
import com.manteniopro.model.Usuario;
import com.manteniopro.util.UtilidadesArchivo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VistaAsignarTareas extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(VistaAsignarTareas.class.getName());

    private List<Usuario> listaUsuariosDisponibles; // Lista de todos los usuarios disponibles
    private List<Tarea> listaTareasExistentes; // Ahora la lista es de objetos Tarea
    private JComboBox<String> comboUsuarios; // Cambiado a comboUsuarios para reflejar todos los roles
    private JTextField txtDescripcionTarea;

    public VistaAsignarTareas(List<Usuario> listaUsuarios, List<Tarea> listaTareasAntiguas) {
        this.listaUsuariosDisponibles = listaUsuarios;
        this.listaTareasExistentes = listaTareasAntiguas != null ? new ArrayList<>(listaTareasAntiguas) : new ArrayList<>();
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Asignar Tareas");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el PanelFondo
        PanelFondo panelFondo = new PanelFondo("/Imagenes/Inicio_de_sesion.jpg");
        panelFondo.setLayout(new GridLayout(3, 2, 10, 10));
        setContentPane(panelFondo);

        JLabel lblUsuario = new JLabel("Seleccionar Usuario:"); // Cambiado la etiqueta
        lblUsuario.setForeground(Color.WHITE);
        comboUsuarios = new JComboBox<>(); // Cambiado el nombre del combo
        for (Usuario u : listaUsuariosDisponibles) {
            comboUsuarios.addItem(u.getNombre() + " (" + u.getRol() + ")"); // Mostramos nombre y rol
        }
        comboUsuarios.setOpaque(false); // Hacer transparente para ver el fondo
        lblUsuario.setOpaque(false);

        JLabel lblDescripcion = new JLabel("Descripción de la Tarea:");
        lblDescripcion.setForeground(Color.WHITE);
        txtDescripcionTarea = new JTextField(20);
        txtDescripcionTarea.setOpaque(false);
        lblDescripcion.setOpaque(false);
        txtDescripcionTarea.setForeground(Color.WHITE);
        txtDescripcionTarea.setCaretColor(Color.WHITE);


        JButton btnAsignar = new JButton("Asignar Tarea");
        btnAsignar.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                asignarNuevaTarea();
            }
        });
        btnAsignar.setOpaque(false);

        panelFondo.add(lblUsuario);
        panelFondo.add(comboUsuarios);
        panelFondo.add(lblDescripcion);
        panelFondo.add(txtDescripcionTarea);
        panelFondo.add(new JLabel("")); // Espacio
        panelFondo.add(btnAsignar);

        // Hacer los componentes transparentes para que se vea el fondo
        setOpaqueComponent(lblUsuario);
        setOpaqueComponent(comboUsuarios);
        setOpaqueComponent(lblDescripcion);
        setOpaqueComponent(txtDescripcionTarea);
        setOpaqueComponent(btnAsignar);
    }

    private void setOpaqueComponent(JComponent component) {
        component.setOpaque(false);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                if (child instanceof JComponent) {
                    setOpaqueComponent((JComponent) child);
                }
            }
        }
    }

    private void asignarNuevaTarea() {
        String usuarioSeleccionadoCompleto = (String) comboUsuarios.getSelectedItem();
        String descripcionTarea = txtDescripcionTarea.getText().trim();
        Date fechaAsignacion = new Date(); // Puedes formatear la fecha si lo deseas
        String estado = "Pendiente"; // Estado inicial de la tarea

        if (usuarioSeleccionadoCompleto != null && !descripcionTarea.isEmpty()) {
            String[] partes = usuarioSeleccionadoCompleto.split(" \\(");
            String nombreUsuario = partes[0]; // Obtenemos solo el nombre del usuario

            Tarea nuevaTarea = new Tarea(descripcionTarea, nombreUsuario, fechaAsignacion, estado);
            listaTareasExistentes.add(nuevaTarea);
            guardarTareas();
            JOptionPane.showMessageDialog(this, "Tarea asignada a " + nombreUsuario);
            txtDescripcionTarea.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario y describa la tarea.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarTareas() {
        try {
            UtilidadesArchivo.guardarEnArchivo(new ArrayList<>(listaTareasExistentes), "tareas.dat");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar las tareas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error al guardar las tareas: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }
}