package com.example.versus.birthdayhelper;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Contacto implements Parcelable {
    @Override
    public String toString() {
        return "Contacto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", tipoNotif=" + tipoNotif +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }

    private int id;
    private String nombre;
    private String telefono;
    private String fechaNacimiento;
    private char tipoNotif;
    private String mensaje;

    public Contacto(int id,String nombre, String telefono, String fechaNacimiento, char tipoNotif, String mensaje) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected Contacto(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        telefono = in.readString();
        fechaNacimiento = in.readString();
        tipoNotif = (char) in.readValue(char.class.getClassLoader());
        mensaje = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(telefono);
        dest.writeString(fechaNacimiento);
        dest.writeValue(tipoNotif);
        dest.writeString(mensaje);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Contacto> CREATOR = new Parcelable.Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };
}