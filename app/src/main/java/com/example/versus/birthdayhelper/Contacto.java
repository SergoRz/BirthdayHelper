package com.example.versus.birthdayhelper;

import java.util.Date;

/**
 * Created by EmilioCB on 17/01/2017.
 */

public class Contacto {

    private String nombre;
    private long telefono;
    private String fechaNacimiento;
    private char tipoNotif;
    private String mensaje;

    public Contacto(String nombre, long telefono, String fechaNacimiento, char tipoNotif, String mensaje) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoNotif = tipoNotif;
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public long getTelefono() {
        return telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public char getTipoNotif() {
        return tipoNotif;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setTipoNotif(char tipoNotif) {
        this.tipoNotif = tipoNotif;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
