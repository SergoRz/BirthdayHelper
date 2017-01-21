package com.example.versus.birthdayhelper;

import java.util.Date;

/**
 * Created by EmilioCB on 17/01/2017.
 */

public class Contacto {

    private String nombre;
    private String telefono;
    private String fechaNacimiento;
    private char tipoNotif;
    private String mensaje;

    public Contacto(String nombre, String telefono, String fechaNacimiento, char tipoNotif, String mensaje) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoNotif = tipoNotif;
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
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

    public void setTelefono(String telefono) {
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
