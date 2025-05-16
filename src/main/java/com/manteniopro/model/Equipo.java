package com.manteniopro.model;

public class Equipo {
    private String idEquipo;
    private String modelo;
    private String fabricante;

    // Constructor
    public Equipo(String idEquipo, String modelo, String fabricante) {
        this.idEquipo = idEquipo;
        this.modelo = modelo;
        this.fabricante = fabricante;
    }

    // Getters y Setters
    public String getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(String idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}