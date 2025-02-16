/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USER-LENOVO
 */
import java.util.Date;

public class MascotasModelo {
    private int id; 
    private ClientesModelo cliente; 
    private String nombre;
    private String tipoAnimal; 
    private Date fechaNacimiento; 
    private double peso;
    private String raza;
    private boolean vacunas;
    private String observaciones;

    public MascotasModelo(int id, ClientesModelo cliente, String nombre, String tipoAnimal, Date fechaNacimiento, double peso, String raza, boolean vacunas, String observaciones) {
        this.id = id;
        this.cliente = cliente;
        this.nombre = nombre;
        this.tipoAnimal = tipoAnimal;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.raza = raza;
        this.vacunas = vacunas;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClientesModelo getCliente() {
        return cliente;
    }

    public void setCliente(ClientesModelo cliente) {
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public boolean isVacunas() {
        return vacunas;
    }

    public void setVacunas(boolean vacunas) {
        this.vacunas = vacunas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
