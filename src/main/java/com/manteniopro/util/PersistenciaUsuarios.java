package com.manteniopro.util;

import com.manteniopro.model.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenciaUsuarios {

    private static final Logger LOGGER = Logger.getLogger(PersistenciaUsuarios.class.getName());

    public static List<Usuario> cargarUsuarios(String archivo) throws IOException, ClassNotFoundException {
        List<Usuario> usuarios = new ArrayList<>();
        File file = new File(archivo);
        if (!file.exists() || file.length() == 0) {
            return usuarios; // Retorna una lista vacía si el archivo no existe o está vacío
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                usuarios = (List<Usuario>) obj;
                LOGGER.log(Level.INFO, "Usuarios cargados desde {0}: {1}", new Object[]{archivo, usuarios.size()});
                for (Usuario u : usuarios) {
                    LOGGER.log(Level.FINE, "  Usuario: {0}, Contraseña: {1}", new Object[]{u.getNombre(), u.getContraseña()});
                }
            } else {
                LOGGER.warning("El archivo no contenía una lista de usuarios válida.");
            }
        }
        return usuarios;
    }

    public static void guardarUsuarios(List<Usuario> usuarios, String archivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(usuarios);
            LOGGER.log(Level.INFO, "Usuarios guardados en {0}: {1}", new Object[]{archivo, usuarios.size()});
            for (Usuario u : usuarios) {
                LOGGER.log(Level.FINE, "  Usuario: {0}, Contraseña: {1}", new Object[]{u.getNombre(), u.getContraseña()});
            }
        }
    }
}