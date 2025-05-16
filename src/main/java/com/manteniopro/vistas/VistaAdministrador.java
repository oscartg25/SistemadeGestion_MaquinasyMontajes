package com.manteniopro.vistas;

import com.manteniopro.componentes.PanelFondo;
import com.manteniopro.model.EquipoIzaje;
import com.manteniopro.model.Tarea;
import com.manteniopro.model.Usuario;
import com.manteniopro.util.UtilidadesArchivo;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class VistaAdministrador extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(VistaAdministrador.class.getName());

    private List<Usuario> listaUsuarios;
    private List<Tarea> listaTareas = new ArrayList<>(); // Cambiamos a List<Tarea>
    private List<EquipoIzaje> listaEquipos = new ArrayList<>();
    private List<Object[]> listaReportes = new ArrayList<>();

    public VistaAdministrador(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
        initComponents();
        loadData();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Panel de Administrador");
        setSize(700, 550); // Aumentamos un poco más el tamaño
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el PanelFondo
        PanelFondo panelFondo = new PanelFondo("/Imagenes/Inicio_de_sesion.jpg");
        panelFondo.setLayout(new BorderLayout()); // Usamos BorderLayout para organizar los paneles

        // Panel de botones principales
        JPanel panelBotones = new JPanel(new GridLayout(7, 1, 10, 10)); // Una fila más para el botón Agregar Equipo
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBotones.setOpaque(false); // Hacer el panel de botones transparente

        JButton btnGestionUsuarios = new JButton("Gestionar Usuarios");
        JButton btnAsignarTareas = new JButton("Asignar Tareas");
        JButton btnVerReportes = new JButton("Ver Reportes de Mantenimiento");
        JButton btnVerEquipos = new JButton("Ver Equipos");
        JButton btnAgregarEquipo = new JButton("Agregar Nuevo Equipo");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        JButton btnCerrarAplicacion = new JButton("Cerrar Aplicación");

        panelBotones.add(btnGestionUsuarios);
        panelBotones.add(btnAsignarTareas);
        panelBotones.add(btnVerReportes);
        panelBotones.add(btnVerEquipos);
        panelBotones.add(btnAgregarEquipo);
        panelBotones.add(btnCerrarSesion);
        panelBotones.add(btnCerrarAplicacion);

        panelFondo.add(panelBotones, BorderLayout.WEST);

        // Panel para agregar equipos
        JPanel panelAgregarEquipo = new JPanel(new GridLayout(6, 2, 10, 10)); // Una fila más para el ID
        panelAgregarEquipo.setBorder(BorderFactory.createTitledBorder("Agregar Nuevo Equipo"));
        panelAgregarEquipo.setOpaque(false); // Hacer el panel de agregar equipo transparente
        JTextField txtIdEquipo = new JTextField(20); // Nuevo campo para el ID
        JTextField txtNombreEquipo = new JTextField(20);
        JTextField txtModeloEquipo = new JTextField(20);
        JTextField txtCapacidadEquipo = new JTextField(20);
        JButton btnGuardarEquipo = new JButton("Guardar Equipo");
        JButton btnCancelarAgregarEquipo = new JButton("Cancelar");

        panelAgregarEquipo.add(new JLabel("ID:")); // Etiqueta para el ID
        panelAgregarEquipo.add(txtIdEquipo); // Campo de texto para el ID
        panelAgregarEquipo.add(new JLabel("Nombre:"));
        panelAgregarEquipo.add(txtNombreEquipo);
        panelAgregarEquipo.add(new JLabel("Modelo:"));
        panelAgregarEquipo.add(txtModeloEquipo);
        panelAgregarEquipo.add(new JLabel("Capacidad:"));
        panelAgregarEquipo.add(txtCapacidadEquipo);
        panelAgregarEquipo.add(btnGuardarEquipo);
        panelAgregarEquipo.add(btnCancelarAgregarEquipo);

        panelAgregarEquipo.setVisible(false);
        panelFondo.add(panelAgregarEquipo, BorderLayout.CENTER);

        // Establecer el PanelFondo como el contentPane
        setContentPane(panelFondo);

        // Action Listeners
        btnGestionUsuarios.addActionListener(e -> new VistaGestionUsuario(new ArrayList<>(listaUsuarios)).setVisible(true));
        btnAsignarTareas.addActionListener(e -> {
            List<Usuario> trabajadores = listaUsuarios.stream()
                    .filter(u -> u.getRol().equalsIgnoreCase("aparejador") ||
                                 u.getRol().equalsIgnoreCase("operador") ||
                                 u.getRol().equalsIgnoreCase("aux mantenimiento") ||
                                 u.getRol().equalsIgnoreCase("mecanico") ||
                                 u.getRol().equalsIgnoreCase("hse"))
                    .collect(Collectors.toList());
            if (!trabajadores.isEmpty()) {
                new VistaAsignarTareas(trabajadores, new ArrayList<>(listaTareas)).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No hay trabajadores registrados para asignar tareas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        btnVerReportes.addActionListener(e -> new VistaReportes(new ArrayList<>(listaReportes)).setVisible(true));
        btnVerEquipos.addActionListener(e -> {
            try {
                List<EquipoIzaje> equiposActualizados = UtilidadesArchivo.leerDesdeArchivo("equipos.dat");
                if (equiposActualizados != null) {
                    new VistaEquipoIzaje(new ArrayList<>(equiposActualizados)).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudieron cargar los equipos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar los equipos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        btnAgregarEquipo.addActionListener(e -> {
            panelAgregarEquipo.setVisible(true);
            panelBotones.setVisible(false);
            revalidate();
            repaint();
        });
        btnGuardarEquipo.addActionListener(e -> {
            String id = txtIdEquipo.getText().trim(); // Recogemos el ID
            String nombre = txtNombreEquipo.getText().trim();
            String modelo = txtModeloEquipo.getText().trim();
            String capacidadStr = txtCapacidadEquipo.getText().trim();

            if (!id.isEmpty() && !nombre.isEmpty() && !modelo.isEmpty() && !capacidadStr.isEmpty()) {
                try {
                    double capacidad = Double.parseDouble(capacidadStr);
                    // Asegúrate de que el constructor de EquipoIzaje acepte el ID primero
                    EquipoIzaje nuevoEquipo = new EquipoIzaje(id, nombre, modelo, capacidad);
                    listaEquipos.add(nuevoEquipo);
                    guardarEquipos();
                    JOptionPane.showMessageDialog(this, "Equipo con ID " + id + " guardado correctamente.");
                    txtIdEquipo.setText("");
                    txtNombreEquipo.setText("");
                    txtModeloEquipo.setText("");
                    txtCapacidadEquipo.setText("");
                    panelAgregarEquipo.setVisible(false);
                    panelBotones.setVisible(true);
                    revalidate();
                    repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "La capacidad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelarAgregarEquipo.addActionListener(e -> {
            panelAgregarEquipo.setVisible(false);
            panelBotones.setVisible(true);
            revalidate();
            repaint();
        });
        btnCerrarSesion.addActionListener(e -> cerrarSesionAdmin());
        btnCerrarAplicacion.addActionListener(e -> cerrarAplicacion());
    }

    private void guardarEquipos() {
        try {
            UtilidadesArchivo.guardarEnArchivo(new ArrayList<>(listaEquipos), "equipos.dat");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar los equipos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error al guardar los equipos: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private void cerrarSesionAdmin() {
        dispose();
        new VistaInicio(new ArrayList<>(listaUsuarios)).setVisible(true);
    }

    private void loadData() {
        try {
            listaEquipos = UtilidadesArchivo.leerDesdeArchivo("equipos.dat");
            if (listaEquipos == null) {
                listaEquipos = new ArrayList<>();
            }
            listaReportes = UtilidadesArchivo.leerDesdeArchivo("reportes.dat");
            if (listaReportes == null) {
                listaReportes = new ArrayList<>();
            }
            List<?> tempListaTareas = UtilidadesArchivo.leerDesdeArchivo("tareas.dat");
            if (tempListaTareas != null) {
                for (Object obj : tempListaTareas) {
                    if (obj instanceof Tarea) {
                        listaTareas.add((Tarea) obj);
                    } else if (obj instanceof Object[]) {
                        Object[] tareaArray = (Object[]) obj;
                        // Asumiendo el orden: descripcion, trabajadorAsignado, fechaAsignacion (String), estado
                        if (tareaArray.length == 4 && tareaArray[0] instanceof String && tareaArray[1] instanceof String && tareaArray[2] instanceof String && tareaArray[3] instanceof String) {
                            try {
                                Date fecha = new Date(tareaArray[2].toString()); // Intenta convertir la fecha String a Date
                                listaTareas.add(new Tarea((String) tareaArray[0], (String) tareaArray[1], fecha, (String) tareaArray[3]));
                            } catch (IllegalArgumentException ex) {
                                LOGGER.log(Level.WARNING, "Error al convertir fecha de tarea: " + tareaArray[2], ex);
                                // Manejar el error de conversión de fecha si es necesario
                            }
                        }
                    }
                }
            } else {
                listaTareas = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar otros datos: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Error al cargar datos adicionales.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cerrarAplicacion() {
        try {
            UtilidadesArchivo.guardarEnArchivo(new ArrayList<>(listaUsuarios), "usuarios.dat");
            UtilidadesArchivo.guardarEnArchivo(new ArrayList<>(listaTareas), "tareas.dat");
            UtilidadesArchivo.guardarEnArchivo(new ArrayList<>(listaEquipos), "equipos.dat");
            UtilidadesArchivo.guardarEnArchivo(new ArrayList<>(listaReportes), "reportes.dat");
            JOptionPane.showMessageDialog(this, "Datos guardados correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error al guardar datos: " + ex.getMessage(), ex);
            ex.printStackTrace();
        } finally {
            dispose();
        }
    }
}