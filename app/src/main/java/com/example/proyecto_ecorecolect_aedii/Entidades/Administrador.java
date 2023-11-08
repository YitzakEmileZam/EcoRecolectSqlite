package com.example.proyecto_ecorecolect_aedii.Entidades;

public class Administrador {

    private int id, idFoto;
    private String nombres, apellidos, dni, email, cel, genero, password, repassword;
    private String nivelAcceso;
    private Double salario;

    public Administrador(int id, int idFoto, String nombres, String apellidos, String dni, String email, String cel, String genero, String password, String repassword, String nivelAcceso, Double salario) {
        this.id = id;
        this.idFoto = idFoto;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.cel = cel;
        this.genero = genero;
        this.password = password;
        this.repassword = repassword;
        this.nivelAcceso = nivelAcceso;
        this.salario = salario;
    }

    public Administrador(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getNivelAcceso() {        return nivelAcceso;     }

    public void setNivelAcceso(String nivelAcceso) {        this.nivelAcceso = nivelAcceso;     }

    public Double getSalario() {    return salario;     }

    public void setSalario(Double salario) {    this.salario = salario;     }
}
