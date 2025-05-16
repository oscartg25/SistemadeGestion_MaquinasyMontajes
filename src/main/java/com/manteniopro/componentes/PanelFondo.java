package com.manteniopro.componentes;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class PanelFondo extends JPanel {
    private BufferedImage imagenFondo;

    public PanelFondo(String rutaImagen) {
        try (InputStream is = getClass().getResourceAsStream(rutaImagen)) {
            if (is == null) {
                System.err.println("No se pudo encontrar el recurso de la imagen: " + rutaImagen);
                return;
            }
            imagenFondo = ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen de fondo: " + rutaImagen);
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}