package com.example.app_restaurant.models;

public class Like {
    private String id;
    private String id_Usu;
    private String id_restaurante;
    private String liked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_Usu() {
        return id_Usu;
    }

    public void setId_Usu(String id_Usu) {
        this.id_Usu = id_Usu;
    }

    public String getId_restaurante() {
        return id_restaurante;
    }

    public void setId_restaurante(String id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id='" + id + '\'' +
                ", id_Usu='" + id_Usu + '\'' +
                ", id_restaurante='" + id_restaurante + '\'' +
                ", liked='" + liked + '\'' +
                '}';
    }
}
