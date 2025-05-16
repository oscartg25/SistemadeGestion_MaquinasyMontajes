package com.manteniopro;

import com.manteniopro.vistas.VistaPrincipal;
import com.manteniopro.model.Usuario;
import com.manteniopro.util.PersistenciaUsuarios;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.lang.ClassNotFoundException;

public class Manteniopro {

    private static final Logger LOGGER = Logger.getLogger(Manteniopro.class.getName());

    public static void main(String[] args) {
        String archivoUsuarios = "usuarios.dat";
        List<Usuario> usuarios = new ArrayList<>();
        boolean errorCarga = false;

        try {
            usuarios = PersistenciaUsuarios.cargarUsuarios(archivoUsuarios);
            LOGGER.log(Level.INFO, "Usuarios cargados al inicio. Cantidad: {0}", usuarios.size());
            for (Usuario u : usuarios) {
                LOGGER.log(Level.FINE, "  Usuario cargado: {0}", u.getNombre());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar usuarios (IOException): " + e.getMessage(), e);
            errorCarga = true;
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar usuarios (ClassNotFoundException): " + e.getMessage(), e);
        }

        if (errorCarga) {
            LOGGER.log(Level.WARNING, "No se pudieron cargar los usuarios desde el archivo. Se inicializará con valores predeterminados.");
            usuarios.add(new Usuario("admin", "Administrador", "admin@example.com", "admin123", "Administrador"));
            LOGGER.log(Level.INFO, "Usuario administrador predeterminado añadido.");
        } else if (usuarios.isEmpty()) {
            LOGGER.log(Level.INFO, "No se encontraron usuarios en el archivo. Se agregará el administrador predeterminado.");
            usuarios.add(new Usuario("admin", "Administrador", "admin@example.com", "admin123", "Administrador"));
            LOGGER.log(Level.INFO, "Usuario administrador predeterminado añadido.");
        }

        VistaPrincipal vistaPrincipal = new VistaPrincipal(usuarios); // Pasar la lista cargada
        vistaPrincipal.setVisible(true);

        final List<Usuario> usuariosFinal = new ArrayList<>(usuarios);
        final String archivoUsuariosFinal = archivoUsuarios;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                PersistenciaUsuarios.guardarUsuarios(usuariosFinal, archivoUsuariosFinal);
                LOGGER.log(Level.INFO, "Usuarios guardados al cerrar el programa. Cantidad: {0}", usuariosFinal.size());
                for (Usuario u : usuariosFinal) {
                    LOGGER.log(Level.FINE, "  Usuario guardado al cierre: {0}", u.getNombre());
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error al guardar usuarios al cerrar: " + e.getMessage(), e);
            }
        }));
    }
}