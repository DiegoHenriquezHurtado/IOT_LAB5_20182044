package com.example.appcalorias.model;

import java.io.Serializable;

public class ComidaBebida implements Serializable {
    private String nombre;
    private String cantidadCalorias;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidadCalorias() {
        return cantidadCalorias;
    }

    public void setCantidadCalorias(String cantidadCalorias) {
        this.cantidadCalorias = cantidadCalorias;
    }
}
