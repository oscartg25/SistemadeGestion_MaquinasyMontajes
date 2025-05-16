// VistaInicio.java
package com.manteniopro.vistas;

import com.manteniopro.componentes.PanelFondo;
import com.manteniopro.model.EquipoIzaje;
import com.manteniopro.model.Tarea; // Importamos la clase Tarea
import com.manteniopro.model.Usuario;
import com.manteniopro.util.UtilidadesArchivo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class VistaInicio extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(VistaInicio.class.getName());

    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<EquipoIzaje> listaEquipos = new ArrayList<>();
    private List<Tarea> listaTareas = new ArrayList<>(); // Cambiamos a List<Tarea>
    private List<Object[]> listaReportes = new ArrayList<>();

    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JButton btnIniciar;
    private JButton btnRegistrar;

    public VistaInicio() {
        initComponents();
        loadData();
        setupListeners();
        setVisible(true);
    }

    public VistaInicio(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios != null ? new ArrayList<>(listaUsuarios) : new ArrayList<>();
        initComponents();
        setupListeners();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Inicio de Sesión");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el PanelFondo
        PanelFondo panelFondo = new PanelFondo("/Imagenes/Inicio_de_sesion.jpg");
        panelFondo.setLayout(new GridBagLayout());
        setContentPane(panelFondo);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Etiqueta Usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setOpaque(false);
        panelFondo.add(lblUsuario, gbc);

        // Campo de texto Usuario
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtUsuario = new JTextField(15);
        txtUsuario.setOpaque(false);
        txtUsuario.setForeground(Color.WHITE);
        txtUsuario.setCaretColor(Color.WHITE);
        panelFondo.add(txtUsuario, gbc);
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;

        // Etiqueta Contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setForeground(Color.WHITE);
        lblContraseña.setOpaque(false);
        panelFondo.add(lblContraseña, gbc);

        // Campo de texto Contraseña
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtContraseña = new JPasswordField(15);
        txtContraseña.setOpaque(false);
        txtContraseña.setForeground(Color.WHITE);
        txtContraseña.setCaretColor(Color.WHITE);
        panelFondo.add(txtContraseña, gbc);
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;

        // Botón Iniciar Sesión
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        btnIniciar = new JButton("Iniciar Sesión");
        btnIniciar.setOpaque(false);
        panelFondo.add(btnIniciar, gbc);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        // Botón Registrar
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setOpaque(false);
        panelFondo.add(btnRegistrar, gbc);
        gbc.gridwidth = 1;

        pack(); // Ajustar el tamaño de la ventana
    }

    private void loadData() {
        try {
            listaUsuarios = cargarDatos("usuarios.dat", Usuario.class);
            listaEquipos = cargarDatos("equipos.dat", EquipoIzaje.class);
            listaTareas = cargarDatos("tareas.dat", Tarea.class); // Cargamos como List<Tarea>
            listaReportes = cargarDatosGenerico("reportes.dat");

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private <T> List<T> cargarDatos(String archivo, Class<T> clase) throws IOException, ClassNotFoundException {
        List<?> tempLista = UtilidadesArchivo.leerDesdeArchivo(archivo);
        if (tempLista != null) {
            return tempLista.stream()
                    .filter(clase::isInstance)
                    .map(clase::cast)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private List<Object[]> cargarDatosGenerico(String archivo) throws IOException, ClassNotFoundException {
        List<?> tempLista = UtilidadesArchivo.leerDesdeArchivo(archivo);
        if (tempLista != null) {
            return tempLista.stream()
                    .filter(Object[].class::isInstance)
                    .map(Object[].class::cast)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private void setupListeners() {
        btnIniciar.addActionListener(e -> iniciarSesion(txtUsuario.getText().trim(), new String(txtContraseña.getPassword()).toCharArray()));
        btnRegistrar.addActionListener(e -> {
            dispose();
            new VistaRegistro(listaUsuarios).setVisible(true);
        });
    }

    private void iniciarSesion(String usuario, char[] contraseña) {
        if (listaUsuarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay usuarios registrados en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Usuario u : listaUsuarios) {
            if (u.getNombre().trim().equalsIgnoreCase(usuario) && Arrays.equals(u.getContraseña().toCharArray(), contraseña)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión correcto.");
                dispose();
                try {
                    if ("Administrador".equalsIgnoreCase(u.getRol())) {
                        new VistaAdministrador(new ArrayList<>(listaUsuarios)).setVisible(true);
                    } else {
                        List<EquipoIzaje> equiposParaTrabajador = new ArrayList<>(listaEquipos);
                        List<Tarea> tareasParaTrabajador = listaTareas.stream()
                                .filter(tarea -> tarea.getTrabajadorAsignado().equalsIgnoreCase(u.getNombre()))
                                .collect(Collectors.toList());
                        List<Object[]> reportesParaTrabajador = new ArrayList<>(listaReportes);

                        VistaTrabajador vistaTrabajador = new VistaTrabajador(u, equiposParaTrabajador, tareasParaTrabajador, reportesParaTrabajador);
                        vistaTrabajador.setVisible(true);
                    }
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Error al abrir la ventana del usuario: " + ex.getMessage(), ex);
                    JOptionPane.showMessageDialog(VistaInicio.this, "Error al abrir la ventana del usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VistaInicio::new);
    }
}