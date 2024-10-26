package com.example.appcalorias.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
    private String nombre;
    private String tmb;
    private ArrayList<ComidaBebida> listaComidasBebidas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTmb() {
        return tmb;
    }

    public void setTmb(String tmb) {
        this.tmb = tmb;
    }

    public ArrayList<ComidaBebida> getListaComidasBebidas() {
        return listaComidasBebidas;
    }

    public void setListaComidasBebidas(ArrayList<ComidaBebida> listaComidasBebidas) {
        this.listaComidasBebidas = listaComidasBebidas;
    }
}
