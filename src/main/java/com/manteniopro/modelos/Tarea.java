package com.manteniopro.model;

import java.io.Serializable;
import java.util.Date;

public class Tarea implements Serializable {
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private String trabajadorAsignado;
    private Date fechaAsignacion;
    private String estado; // Opcional: Pendiente, En Progreso, Completada

    public Tarea(String descripcion, String trabajadorAsignado, Date fechaAsignacion, String estado1) {
        this.descripcion = descripcion;
        this.trabajadorAsignado = trabajadorAsignado;
        this.fechaAsignacion = fechaAsignacion;
        this.estado = "Pendiente"; // Por defecto
    }

    // Getters y setters para todos los atributos

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTrabajadorAsignado() {
        return trabajadorAsignado;
    }

    public void setTrabajadorAsignado(String trabajadorAsignado) {
        this.trabajadorAsignado = trabajadorAsignado;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Tarea{" +
               "descripcion='" + descripcion + '\'' +
               ", trabajadorAsignado='" + trabajadorAsignado + '\'' +
               ", fechaAsignacion=" + fechaAsignacion +
               ", estado='" + estado + '\'' +
               '}';
    }
}