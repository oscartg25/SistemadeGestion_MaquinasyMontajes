package com.manteniopro.vistas;

import com.manteniopro.model.Usuario;
import com.manteniopro.componentes.PanelFondo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VistaPrincipal extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(VistaPrincipal.class.getName());
    private List<Usuario> listaUsuarios;
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;

    public VistaPrincipal(List<Usuario> usuarios) {
        this.listaUsuarios = usuarios;

        setTitle("Inicio de Sesión - Manteniopro");
        setSize(600, 400); // Tamaño de ventana más grande
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panelFondo = new PanelFondo("/Imagenes/Inicio_de_sesion.jpg");
        panelFondo.setLayout(new BorderLayout()); // Volvemos a BorderLayout
        panelFondo.setOpaque(true);

      JPanel panelCampos = new JPanel(new GridBagLayout()); // GridBagLayout para los campos
        panelCampos.setOpaque(false);
        Font fuentePequeña = new Font("Arial", Font.PLAIN, 14);
        GridBagConstraints gbcCampos = new GridBagConstraints();
        gbcCampos.insets = new Insets(2, 5, 2, 5); // Reducimos los insets verticales y mantenemos horizontales
        gbcCampos.fill = GridBagConstraints.HORIZONTAL;
        gbcCampos.weightx = 0.0; // Las etiquetas no se expanden

        // Etiqueta Usuario
        gbcCampos.gridx = 0;
        gbcCampos.gridy = 0;
        gbcCampos.anchor = GridBagConstraints.LINE_START; // Alineamos al inicio de la línea
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(fuentePequeña);
        panelCampos.add(lblUsuario, gbcCampos);
        
        // Campo de texto Usuario
        gbcCampos.gridx = 1;
        gbcCampos.gridy = 0;
        gbcCampos.weightx = 0.0; // No se expande tanto
        gbcCampos.anchor = GridBagConstraints.WEST; // Alineamos a la izquierda
        txtUsuario = new JTextField(15); // Establecemos un ancho preferido de 15 columnas
        txtUsuario.setFont(fuentePequeña);
        panelCampos.add(txtUsuario, gbcCampos);

        // Etiqueta Contraseña
        gbcCampos.gridx = 0;
        gbcCampos.gridy = 1;
        gbcCampos.anchor = GridBagConstraints.LINE_START; // Alineamos al inicio de la línea
        gbcCampos.weightx = 0.0; // La etiqueta no se expande
        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setForeground(Color.WHITE);
        lblContraseña.setFont(fuentePequeña);
        panelCampos.add(lblContraseña, gbcCampos);

        // Campo de texto Contraseña
        gbcCampos.gridx = 1;
        gbcCampos.gridy = 1;
        gbcCampos.weightx = 0.0; // No se expande tanto
        gbcCampos.anchor = GridBagConstraints.WEST; // Alineamos a la izquierda
        txtContraseña = new JPasswordField(15); // Establecemos un ancho preferido de 15 columnas
        txtContraseña.setFont(fuentePequeña);
        panelCampos.add(txtContraseña, gbcCampos);
        
        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        panelBotones.setOpaque(false);
        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setFont(fuentePequeña);
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(fuentePequeña);
        panelBotones.add(btnIniciarSesion);
        panelBotones.add(btnRegistrar);

        // Añadimos panelCampos al centro del panelFondo
        panelFondo.add(panelCampos, BorderLayout.CENTER);

        // Añadimos panelBotones a la parte SOUTH del panelFondo
        panelFondo.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panelFondo);

        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuarioIngresado = txtUsuario.getText().trim().toLowerCase();
                char[] contraseña = txtContraseña.getPassword();
                iniciarSesion(usuarioIngresado, contraseña);
            }
        });

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VistaRegistro(listaUsuarios).setVisible(true);
            }
        });
    }

    private void iniciarSesion(String usuarioIngresado, char[] contraseña) {
        String contraseñaIngresada = new String(contraseña);
        boolean encontrado = false;

        if (listaUsuarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay usuarios registrados en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Usuario u : listaUsuarios) {
            String usuarioGuardado = u.getNombre().toLowerCase().trim();
            String contraseñaGuardada = u.getContraseña();

            if (usuarioIngresado.equals(usuarioGuardado) && contraseñaIngresada.equals(contraseñaGuardada)) {
                encontrado = true;
                JOptionPane.showMessageDialog(this, "Inicio de sesión correcto.");

                if ("Administrador".equalsIgnoreCase(u.getRol())) {
                    new VistaAdministrador(listaUsuarios).setVisible(true);
                    this.dispose();
                } else {
                    new VistaTrabajador(u, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()).setVisible(true);
                    this.dispose();
                }
                break;
            }
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}