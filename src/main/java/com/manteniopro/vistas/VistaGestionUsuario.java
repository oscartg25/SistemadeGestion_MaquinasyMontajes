package com.manteniopro.vistas;

import com.manteniopro.componentes.PanelFondo;
import com.manteniopro.model.Usuario;
import com.manteniopro.util.UtilidadesArchivo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VistaGestionUsuario extends JFrame {

    private List<Usuario> usuarios = new ArrayList<>();
    private DefaultTableModel modeloTabla;

    public VistaGestionUsuario(List<Usuario> listaUsuarios) {
        this.usuarios.addAll(listaUsuarios);
        initComponents();
        actualizarTabla();
        setVisible(true);
    }

    public VistaGestionUsuario() {
        this.usuarios = new ArrayList<>();
        initComponents();
        cargarUsuarios();
        actualizarTabla();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Gestión de Usuarios");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el PanelFondo
        PanelFondo panelFondo = new PanelFondo("/Imagenes/Inicio_de_sesion.jpg");
        panelFondo.setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Cédula");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Correo");
        modeloTabla.addColumn("Rol");

        JTable tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); // Hacer el viewport también transparente
        panelFondo.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));
        panelBotones.setOpaque(false); // Hacer el panel de botones transparente
        JButton btnAgregar = new JButton("Agregar Usuario");
        JButton btnEditar = new JButton("Editar Usuario");
        JButton btnEliminar = new JButton("Eliminar Usuario");
        JButton btnVolver = new JButton("Volver");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);
        panelFondo.add(panelBotones, BorderLayout.SOUTH);

        // Establecer el PanelFondo como contentPane
        setContentPane(panelFondo);

        btnAgregar.addActionListener(e -> agregarUsuario());
        btnEditar.addActionListener(e -> editarUsuario(tablaUsuarios.getSelectedRow()));
        btnEliminar.addActionListener(e -> eliminarUsuario(tablaUsuarios.getSelectedRow()));
        btnVolver.addActionListener(e -> dispose());
    }

    private void cargarUsuarios() {
        try {
            List<?> tempUsuarios = UtilidadesArchivo.leerDesdeArchivo("usuarios.dat");
            if (tempUsuarios != null) {
                usuarios = tempUsuarios.stream()
                        .filter(Usuario.class::isInstance)
                        .map(Usuario.class::cast)
                        .collect(Collectors.toList());
            } else {
                usuarios = new ArrayList<>(); // Inicializar la lista si no se carga nada
            }
            actualizarTabla(); // Asegurar que la tabla se actualice después de la carga inicial
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void guardarUsuarios() {
        try {
            UtilidadesArchivo.guardarEnArchivo(new ArrayList<>(usuarios), "usuarios.dat");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar los usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void agregarUsuario() {
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField txtCedula = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtRol = new JTextField();

        panelFormulario.add(new JLabel("Cédula:"));
        panelFormulario.add(txtCedula);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(txtCorreo);
        panelFormulario.add(new JLabel("Rol:"));
        panelFormulario.add(txtRol);

        int resultado = JOptionPane.showConfirmDialog(this, panelFormulario, "Agregar Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            if (txtCedula.getText().isEmpty() || txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty() || txtRol.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                usuarios.add(new Usuario(txtCedula.getText(), txtNombre.getText(), txtCorreo.getText(), "contraseña123", txtRol.getText()));
                actualizarTabla(); // Actualizar la tabla inmediatamente después de agregar
                guardarUsuarios();
                JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente.");
            }
        }
    }

    private void editarUsuario(int filaSeleccionada) {
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para editar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = usuarios.get(filaSeleccionada);
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField txtCedula = new JTextField(usuario.getCedula());
        JTextField txtNombre = new JTextField(usuario.getNombre());
        JTextField txtCorreo = new JTextField(usuario.getCorreo());
        JTextField txtRol = new JTextField(usuario.getRol());

        panelFormulario.add(new JLabel("Cédula:"));
        panelFormulario.add(txtCedula);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(txtCorreo);
        panelFormulario.add(new JLabel("Rol:"));
        panelFormulario.add(txtRol);

        int resultado = JOptionPane.showConfirmDialog(this, panelFormulario, "Editar Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            if (txtCedula.getText().isEmpty() || txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty() || txtRol.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                usuario.setCedula(txtCedula.getText());
                usuario.setNombre(txtNombre.getText());
                usuario.setCorreo(txtCorreo.getText());
                usuario.setRol(txtRol.getText());
                actualizarTabla();
                guardarUsuarios();
                JOptionPane.showMessageDialog(this, "Usuario editado exitosamente.");
            }
        }
    }

    private void eliminarUsuario(int filaSeleccionada) {
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        usuarios.remove(filaSeleccionada);
        actualizarTabla();
        guardarUsuarios();
        JOptionPane.showMessageDialog(this, "Usuario eliminado exitosamente.");
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Usuario usuario : usuarios) {
            modeloTabla.addRow(new Object[]{usuario.getCedula(), usuario.getNombre(), usuario.getCorreo(), usuario.getRol()});
        }
    }
}