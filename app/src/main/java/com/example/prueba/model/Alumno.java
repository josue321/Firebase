package com.example.prueba.model;

public class Alumno {

    private  String uid;
    private  String Nombre;
    private  String Apellido;
    private  String Codigo;

    public Alumno() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}


