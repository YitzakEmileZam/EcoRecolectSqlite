package com.example.proyecto_ecorecolect_aedii.Entidades;

import java.io.Serializable;
import java.util.Date;

public class Servicios implements Serializable {
    private int id;
    private int idAdministrador;
    private String nombreServicio;
    private String imagen;
    private String descripcion;
    private Double precio;
    private Date fecha_registro;

    private Date fecha_actualizacion;

    public Servicios() {
    }

    public Servicios(int idAdministrador, String nombreServicio, String imagen, String descripcion, Double precio, Date fecha_registro, Date fecha_actualizacion) {
        this.idAdministrador = idAdministrador;
        this.nombreServicio = nombreServicio;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fecha_registro = fecha_registro;
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public Servicios(String nombreServicio, String imagen, String descripcion, Double precio, Date fecha_registro, Date fecha_actualizacion) {
        this.nombreServicio = nombreServicio;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fecha_registro = fecha_registro;
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Date getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(Date fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }
}
