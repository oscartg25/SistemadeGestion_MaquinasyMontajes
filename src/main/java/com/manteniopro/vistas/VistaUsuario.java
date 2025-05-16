package com.manteniopro.vistas;

import com.manteniopro.model.Usuario; // Importa la clase Usuario
import javax.swing.*; // Importa las bibliotecas para componentes gráficos
import java.awt.*; // Importa bibliotecas de diseño
import java.util.ArrayList; // Importa ArrayList

public class VistaUsuario extends JFrame {
    private ArrayList<Usuario> usuarios;

    public VistaUsuario(String rol, ArrayList<Usuario> listaUsuarios) {
        this.usuarios = listaUsuarios;

        setTitle("Panel de Usuario - Rol: " + rol);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        JButton btnReporteMantenimientos = new JButton("Reporte de Mantenimientos");
        JButton btnInspeccionarEquipos = new JButton("Inspeccionar Equipos");
        JButton btnVolver = new JButton("Volver");

        add(btnReporteMantenimientos);
        add(btnInspeccionarEquipos);
        add(btnVolver);

        btnReporteMantenimientos.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Funcionalidad no implementada aún.")
        );

        btnInspeccionarEquipos.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Funcionalidad no implementada aún.")
        );

        btnVolver.addActionListener(e -> {
            dispose();
            new VistaPrincipal(usuarios).setVisible(true); // Regresa al menú principal
        });
    }
}