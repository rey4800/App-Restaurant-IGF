package com.example.app_restaurant.models;

import java.io.Serializable;

public class Coordenada implements Serializable {

    public double latitud;
    public double longitud;

    public Coordenada(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Coordenada() {

    }
}
