package com.manteniopro.model;

import java.io.Serializable;

public class EquipoIzaje implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nombre;
    private String modelo;
    private double capacidad; // Cambiado a double
    private String estado;

    // Constructor
    public EquipoIzaje(String id, String nombre, String modelo, double capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.estado = "Disponible"; // Valor por defecto
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public double getCapacidad() { return capacidad; }
    public void setCapacidad(double capacidad) { this.capacidad = capacidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Método toString() para visualizar los datos

    
    public String toString() {
        return "EquipoIzaje{" + "id='" + id + '\'' + ", nombre='" + nombre + '\'' +
                ", modelo='" + modelo + '\'' + ", capacidad=" + capacidad +
                ", estado='" + estado + '\'' + '}';
    }
}