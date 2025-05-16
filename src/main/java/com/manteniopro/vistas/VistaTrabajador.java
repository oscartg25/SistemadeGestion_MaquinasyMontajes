package com.manteniopro.vistas;

import com.manteniopro.componentes.PanelFondo;
import com.manteniopro.model.EquipoIzaje;
import com.manteniopro.model.Tarea;
import com.manteniopro.model.Usuario;
import com.manteniopro.util.UtilidadesArchivo;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VistaTrabajador extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(VistaTrabajador.class.getName());
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private List<EquipoIzaje> listaEquipos = new ArrayList<>();
    private List<Tarea> listaTareasTrabajador = new ArrayList<>();
    private List<Object[]> listaReportes = new ArrayList<>();
    private Usuario trabajador;
    private JTextArea areaTareas;

    public VistaTrabajador(Usuario trabajador, List<EquipoIzaje> listaEquipos, List<Tarea> listaTareasAntiguo, List<Object[]> listaReportes) {
        this.trabajador = trabajador;
        this.listaEquipos.addAll(listaEquipos);
        this.listaReportes.addAll(listaReportes);
        cargarTareasTrabajador(listaTareasAntiguo);

        setTitle("Panel del Trabajador - " + trabajador.getNombre());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el PanelFondo
        PanelFondo panelFondo = new PanelFondo("/Imagenes/Inicio_de_sesion.jpg");
        panelFondo.setLayout(new BorderLayout());
        setContentPane(panelFondo);

        JLabel lblBienvenida = new JLabel("Bienvenido, " + trabajador.getNombre(), SwingConstants.CENTER);
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setOpaque(false);
        panelFondo.add(lblBienvenida, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(4, 1, 10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCentral.setOpaque(false);

        JButton btnRealizarReporte = new JButton("Realizar Reporte de Mantenimiento");
        btnRealizarReporte.addActionListener(e -> new VistaReporteMantenimiento(new ArrayList<>(listaEquipos), new ArrayList<>(listaReportes), trabajador).setVisible(true));
        btnRealizarReporte.setOpaque(false);
        panelCentral.add(btnRealizarReporte);

        JButton btnVerTareas = new JButton("Ver Tareas Asignadas");
        btnVerTareas.addActionListener(e -> mostrarTareasPendientes());
        btnVerTareas.setOpaque(false);
        panelCentral.add(btnVerTareas);

        JButton btnCambiarContraseña = new JButton("Cambiar Contraseña");
        btnCambiarContraseña.addActionListener(e -> cambiarContraseña());
        btnCambiarContraseña.setOpaque(false);
        panelCentral.add(btnCambiarContraseña);

        JButton btnVerEquipos = new JButton("Ver Equipos");
        btnVerEquipos.addActionListener(e -> verEquipos());
        btnVerEquipos.setOpaque(false);
        panelCentral.add(btnVerEquipos);

        panelFondo.add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setOpaque(false);
        JButton btnCerrar = new JButton("Cerrar Sesión");
        btnCerrar.addActionListener(e -> cerrarSesion());
        btnCerrar.setOpaque(false);
        panelInferior.add(btnCerrar);
        panelFondo.add(panelInferior, BorderLayout.SOUTH);

        // Inicializar el área de texto para mostrar las tareas (inicialmente oculta)
        areaTareas = new JTextArea();
        areaTareas.setEditable(false);
        areaTareas.setOpaque(false);
        areaTareas.setForeground(Color.WHITE);
        areaTareas.setCaretColor(Color.WHITE);
        JScrollPane scrollPaneTareas = new JScrollPane(areaTareas);
        scrollPaneTareas.setVisible(false); // Ocultar al inicio
        scrollPaneTareas.setOpaque(false);
        scrollPaneTareas.getViewport().setOpaque(false);
        panelFondo.add(scrollPaneTareas, BorderLayout.EAST); // Mostrar a la derecha cuando se soliciten

        // Hacer los componentes transparentes
        setOpaqueComponent(lblBienvenida);
        setOpaqueComponent(btnRealizarReporte);
        setOpaqueComponent(btnVerTareas);
        setOpaqueComponent(btnCambiarContraseña);
        setOpaqueComponent(btnVerEquipos);
        setOpaqueComponent(btnCerrar);
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

    private void cargarTareasTrabajador(List<Tarea> listaTareasAntiguo) {
        listaTareasTrabajador.clear();
        if (listaTareasAntiguo != null) {
            for (Tarea tarea : listaTareasAntiguo) {
                if (tarea.getTrabajadorAsignado().equalsIgnoreCase(trabajador.getNombre())) {
                    listaTareasTrabajador.add(tarea);
                }
            }
        }
    }

    private void cerrarSesion() {
        dispose();
        new VistaInicio(null).setVisible(true);
    }

    private void mostrarTareasPendientes() {
        List<Tarea> tareasActualizadas = obtenerTareasActualizadas();
        cargarTareasTrabajador(tareasActualizadas);
        if (!listaTareasTrabajador.isEmpty()) {
            StringBuilder tareasPendientes = new StringBuilder("Tareas Asignadas:\n");
            for (Tarea tarea : listaTareasTrabajador) {
                tareasPendientes.append("- ").append(tarea.getDescripcion())
                        .append(" (Asignada: ").append(DATE_FORMAT.format(tarea.getFechaAsignacion()))
                        .append(", Estado: ").append(tarea.getEstado()).append(")\n");
            }
            areaTareas.setText(tareasPendientes.toString());
            JScrollPane scrollPaneTareas = buscarScrollPaneTareas();
            if (scrollPaneTareas != null) {
                scrollPaneTareas.setVisible(true);
                revalidate();
                repaint();
            } else {
                JScrollPane nuevoScrollPane = crearScrollPaneTareas();
                add(nuevoScrollPane, BorderLayout.EAST);
                nuevoScrollPane.setVisible(true);
                revalidate();
                repaint();
            }
        } else {
            areaTareas.setText("No tienes tareas asignadas.");
            JScrollPane scrollPaneTareas = buscarScrollPaneTareas();
            if (scrollPaneTareas != null) {
                scrollPaneTareas.setVisible(true);
                revalidate();
                repaint();
            } else {
                JScrollPane nuevoScrollPane = crearScrollPaneTareas();
                add(nuevoScrollPane, BorderLayout.EAST);
                nuevoScrollPane.setVisible(true);
                revalidate();
                repaint();
            }
        }
    }

    private JScrollPane buscarScrollPaneTareas() {
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JScrollPane && ((JScrollPane) comp).getViewport().getView() == areaTareas) {
                return (JScrollPane) comp;
            }
        }
        return null;
    }

    private JScrollPane crearScrollPaneTareas() {
        JScrollPane nuevoScrollPane = new JScrollPane(areaTareas);
        nuevoScrollPane.setOpaque(false);
        nuevoScrollPane.getViewport().setOpaque(false);
        areaTareas.setForeground(Color.WHITE);
        areaTareas.setCaretColor(Color.WHITE);
        nuevoScrollPane.setPreferredSize(new Dimension(250, getHeight()));
        return nuevoScrollPane;
    }

    private List<Tarea> obtenerTareasActualizadas() {
        try {
            List<Tarea> tempLista = UtilidadesArchivo.leerDesdeArchivo("tareas.dat");
            System.out.println("Tareas leídas en VistaTrabajador: " + tempLista); // Línea de depuración
            return tempLista != null ? tempLista : new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar las tareas: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Error al cargar las tareas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return new ArrayList<>();
    }

    private void cambiarContraseña() {
        JPasswordField nuevaClave = new JPasswordField();
        nuevaClave.setOpaque(false);
        nuevaClave.setForeground(Color.WHITE);
        nuevaClave.setCaretColor(Color.WHITE);
        int opcion = JOptionPane.showConfirmDialog(this, nuevaClave, "Nueva Contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION) {
            String nuevaContraseña = new String(nuevaClave.getPassword());
            if (nuevaContraseña.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La contraseña no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            trabajador.setContraseña(nuevaContraseña);
            actualizarUsuarioEnArchivo(); // Llamamos al nuevo método para guardar
            JOptionPane.showMessageDialog(this, "Contraseña actualizada correctamente.");
        }
    }

    private void actualizarUsuarioEnArchivo() {
        try {
            List<Usuario> listaUsuarios = UtilidadesArchivo.leerDesdeArchivo("usuarios.dat");
            if (listaUsuarios != null) {
                for (Usuario u : listaUsuarios) {
                    if (u.getCedula().equals(trabajador.getCedula())) {
                        u.setContraseña(trabajador.getContraseña());
                        break;
                    }
                }
                UtilidadesArchivo.guardarEnArchivo(listaUsuarios, "usuarios.dat");
            } else {
                JOptionPane.showMessageDialog(this, "Error al cargar los usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la contraseña: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error al actualizar la contraseña: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private void verEquipos() {
        try {
            List<EquipoIzaje> equiposActualizados = UtilidadesArchivo.leerDesdeArchivo("equipos.dat");
            if (equiposActualizados != null) {
                new VistaEquipoIzaje(new ArrayList<>(equiposActualizados)).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudieron cargar los equipos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los equipos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error al cargar los equipos: " + ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }
}