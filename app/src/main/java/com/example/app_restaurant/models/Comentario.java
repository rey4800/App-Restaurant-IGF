package com.example.app_restaurant.models;

public class Comentario {

    private String id;
    private String id_Usu;
    private String usuario;
    private String comentario;
    private String id_restaurante;
    private String fecha;
    private String filtro;

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getId_restaurante() {
        return id_restaurante;
    }

    public void setId_restaurante(String id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    @Override
    public String toString() {
        return "Comentario{" +
                "id='" + id + '\'' +
                ", id_Usu='" + id_Usu + '\'' +
                ", usuario='" + usuario + '\'' +
                ", comentario='" + comentario + '\'' +
                ", id_restaurante='" + id_restaurante + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
