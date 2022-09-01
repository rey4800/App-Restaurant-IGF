package com.example.app_restaurant.models;

public class User {

    private String id_Usu;
    private String nombre;
    private String email;
    private String password;
    private int id_tipo;
    private String created_at;
    private int puntaje;


    public String getId_Usu() {
        return id_Usu;
    }

    public void setId_Usu(String id_Usuario) {
        this.id_Usu = id_Usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }


    @Override
    public String toString() {
        return "User{" +
                "id_Usuario='" + id_Usu + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", id_tipo='" + id_tipo + '\'' +
                ", created_at='" + created_at + '\'' +
                ", puntaje=" + puntaje + '}';
    }
}
