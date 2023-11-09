package com.example.proyecto_ecorecolect_aedii.Entidades;

import java.io.Serializable;

public class ServiciosMS implements Serializable {
    private int idFoto;
    private String nomSer,razon,prec;

    public ServiciosMS() {
    }

    public ServiciosMS(int idFoto, String nomSer, String razon, String prec) {
        this.idFoto = idFoto;
        this.nomSer = nomSer;
        this.razon = razon;
        this.prec = prec;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public String getNomSer() {
        return nomSer;
    }

    public void setNomSer(String nomSer) {
        this.nomSer = nomSer;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getPrec() {
        return prec;
    }

    public void setPrec(String prec) {
        this.prec = prec;
    }
}
