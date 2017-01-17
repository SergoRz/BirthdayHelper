package com.example.versus.birthdayhelper;

import java.util.Date;

/**
 * Created by EmilioCB on 17/01/2017.
 */

public class Contacto {
    private long telefono;
    private Date fechaNacimiento;
    private String nombre;

    public Contacto(long telefono, Date fechaNacimiento, String nombre){
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
    }

    public long getTelefono() {
        return telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
