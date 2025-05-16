package com.manteniopro.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UtilidadesArchivo {

    public static <T extends Serializable> void guardarEnArchivo(List<T> lista, String archivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(new ArrayList<>(lista)); // Guardamos una nueva ArrayList para evitar problemas de modificación externa
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> leerDesdeArchivo(String archivo) throws IOException, ClassNotFoundException {
        File file = new File(archivo);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                return (List<T>) obj;
            } else {
                return new ArrayList<>();
            }
        }
    }
}