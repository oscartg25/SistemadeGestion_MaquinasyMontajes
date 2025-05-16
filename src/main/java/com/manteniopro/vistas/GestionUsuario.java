package com.manteniopro.vistas;

import com.manteniopro.model.Usuario;
import javax.swing.*;
import java.util.ArrayList;

public class GestionUsuario {
    private ArrayList<Usuario> usuarios; // Lista de usuarios gestionados

    public GestionUsuario(ArrayList<Usuario> listaUsuarios) {
        this.usuarios = listaUsuarios; // Se pasa la lista desde el constructor
    }

    // Método para agregar un nuevo usuario
    public boolean agregarUsuario(Usuario nuevoUsuario) {
        // Verificar si ya existe un usuario con la misma cédula
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(nuevoUsuario.getCedula())) {
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con esta cédula.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        usuarios.add(nuevoUsuario); // Agregar el nuevo usuario a la lista
        JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente.");
        return true;
    }

    // Método para editar un usuario existente
    public boolean editarUsuario(String cedula, Usuario usuarioEditado) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            if (usuario.getCedula().equals(cedula)) {
                usuarios.set(i, usuarioEditado); // Actualizar los datos del usuario
                JOptionPane.showMessageDialog(null, "Usuario editado exitosamente.");
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Usuario con la cédula especificada no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Método para eliminar un usuario existente
    public boolean eliminarUsuario(String cedula) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCedula().equals(cedula)) {
                usuarios.remove(i); // Eliminar el usuario de la lista
                JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente.");
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Usuario con la cédula especificada no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Método para obtener la lista actualizada de usuarios
    public ArrayList<Usuario> getUsuarios() {
        return usuarios; // Retornar la lista actual de usuarios
    }

    // Método para buscar un usuario por su cédula
    public Usuario buscarUsuario(String cedula) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula)) {
                return usuario; // Retornar el usuario encontrado
            }
        }
        JOptionPane.showMessageDialog(null, "Usuario con la cédula especificada no encontrado.", "Información", JOptionPane.INFORMATION_MESSAGE);
        return null;
    }
}