package com.manteniopro.vistas;

import com.manteniopro.model.Usuario;
import com.manteniopro.util.PersistenciaUsuarios;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VistaRegistro extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(VistaRegistro.class.getName());
    private List<Usuario> usuarios;

    public VistaRegistro(List<Usuario> listaUsuarios) {
        this.usuarios = listaUsuarios;

        setTitle("Registro de Usuario - Manteniopro");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        JLabel lblCedula = new JLabel("Cédula:");
        JTextField txtCedula = new JTextField();
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();
        JLabel lblCorreo = new JLabel("Correo:");
        JTextField txtCorreo = new JTextField();
        JLabel lblContraseña = new JLabel("Contraseña:");
        JPasswordField txtContraseña = new JPasswordField();
        JLabel lblRol = new JLabel("Rol:");
        JTextField txtRol = new JTextField(); 
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnVolver = new JButton("Volver");

        add(lblCedula);
        add(txtCedula);
        add(lblNombre);
        add(txtNombre);
        add(lblCorreo);
        add(txtCorreo);
        add(lblContraseña);
        add(txtContraseña);
        add(lblRol);
        add(txtRol);
        add(btnRegistrar);
        add(btnVolver);

        btnRegistrar.addActionListener(e -> {
            if (txtCedula.getText().isEmpty() || txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty()
                    || txtContraseña.getPassword().length == 0 || txtRol.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String contraseña = new String(txtContraseña.getPassword());
                String rol = txtRol.getText().trim(); 

                Usuario nuevoUsuario = new Usuario(
                        txtCedula.getText(),
                        txtNombre.getText(),
                        txtCorreo.getText(),
                        contraseña,
                        rol 
                );

                LOGGER.log(Level.INFO, "Nuevo usuario creado: {0}", nuevoUsuario);
                usuarios.add(nuevoUsuario);
                LOGGER.log(Level.INFO, "Usuario añadido a la lista. Tamaño actual de la lista: {0}", usuarios.size());

                try {
                    PersistenciaUsuarios.guardarUsuarios(usuarios, "usuarios.dat"); // Guardar inmediatamente
                    JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
                    LOGGER.log(Level.INFO, "Guardado de usuarios exitoso.");

                    txtCedula.setText("");
                    txtNombre.setText("");
                    txtCorreo.setText("");
                    txtContraseña.setText("");
                    txtRol.setText("");

                    dispose(); // Cierra la ventana de registro después de guardar
                    new VistaInicio().setVisible(true); // Vuelve a la ventana de inicio

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al guardar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    LOGGER.log(Level.SEVERE, "Error al guardar usuario: " + ex.getMessage(), ex);
                }
            }
        });

        btnVolver.addActionListener(e -> {
            dispose();
            new VistaInicio().setVisible(true); // Asegúrate de volver a la VistaInicio
        });
    }
}