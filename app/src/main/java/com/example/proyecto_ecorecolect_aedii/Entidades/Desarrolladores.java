package com.example.proyecto_ecorecolect_aedii.Entidades;

public class Desarrolladores {

    private String dni_Des;
    private int imagenDesarrollador;
    private String nombreDesarrollador;
    private String apellidoPat_Des;
    private String apellidoMat_Des;
    private String email_Des;
    private String celular_Des;
    private String genero_Des;
    private String profesion_Des;

    public Desarrolladores(String dni_Des, int imagenDesarrollador, String nombreDesarrollador,
                         String apellidoPat_Des, String apellidoMat_Des, String email_Des,
                         String celular_Des, String genero_Des, String profesion_Des) {
        this.dni_Des = dni_Des;
        this.imagenDesarrollador = imagenDesarrollador;
        this.nombreDesarrollador = nombreDesarrollador;
        this.apellidoPat_Des = apellidoPat_Des;
        this.apellidoMat_Des = apellidoMat_Des;
        this.email_Des = email_Des;
        this.celular_Des = celular_Des;
        this.genero_Des = genero_Des;
        this.profesion_Des = profesion_Des;
    }

    public Desarrolladores() {
    }

    public String getDni_Des() {
        return dni_Des;
    }

    public void setDni_Des(String dni_Des) {
        this.dni_Des = dni_Des;
    }

    public int getImagenDesarrollador() {
        return imagenDesarrollador;
    }

    public void setImagenDesarrollador(int imagenDesarrollador) {
        this.imagenDesarrollador = imagenDesarrollador;
    }

    public String getNombreDesarrollador() {
        return nombreDesarrollador;
    }

    public void setNombreDesarrollador(String nombreDesarrollador) {
        this.nombreDesarrollador = nombreDesarrollador;
    }

    public String getApellidoPat_Des() {
        return apellidoPat_Des;
    }

    public void setApellidoPat_Des(String apellidoPat_Des) {
        this.apellidoPat_Des = apellidoPat_Des;
    }

    public String getApellidoMat_Des() {
        return apellidoMat_Des;
    }

    public void setApellidoMat_Des(String apellidoMat_Des) {
        this.apellidoMat_Des = apellidoMat_Des;
    }

    public String getEmail_Des() {
        return email_Des;
    }

    public void setEmail_Des(String email_Des) {
        this.email_Des = email_Des;
    }

    public String getCelular_Des() {
        return celular_Des;
    }

    public void setCelular_Des(String celular_Des) {
        this.celular_Des = celular_Des;
    }

    public String getGenero_Des() {
        return genero_Des;
    }

    public void setGenero_Des(String genero_Des) {
        this.genero_Des = genero_Des;
    }

    public String getProfesion_Des() {
        return profesion_Des;
    }

    public void setProfesion_Des(String profesion_Des) {
        this.profesion_Des = profesion_Des;
    }
}
