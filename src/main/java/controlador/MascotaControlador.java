/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.MascotasModelo;

/**
 *
 * @author USER-LENOVO
 */
public class MascotaControlador {
    ClientesControlador cc = new ClientesControlador();
    ConexionBDD conectar = new ConexionBDD();
    Connection conectado = (Connection) conectar.conectar();
    PreparedStatement ejecutar;
    ResultSet resultado;
    
    public void insertarMascota(MascotasModelo mm) {
        try {
            String sentenciaSQL = "INSERT INTO mascotas (cliente_cedula, nombre, tipo_animal, fecha_nacimiento, peso, raza, vacunas, observaciones) VALUES ('" +
                mm.getCliente().getCedula() + "', '" +
                mm.getNombre() + "', '" +
                mm.getTipoAnimal() + "', '" +
                mm.getFechaNacimiento().toString() + "', " + 
                mm.getPeso() + ", '" +
                mm.getRaza() + "', " +
                mm.isVacunas() + ", '" +
                mm.getObservaciones() + "');";
            
            ejecutar = conectado.prepareCall(sentenciaSQL);
            int res = ejecutar.executeUpdate();
            if(res > 0 ) {
                System.out.println("Se ha añadido la persona");
                ejecutar.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public MascotasModelo buscarMascota(int id) {
        MascotasModelo mm = null;
        try {
            String sentenciaSQL = "SELECT id, cliente_cedula, nombre, tipo_animal, fecha_nacimiento, peso, raza, vacunas, observaciones " +
                          "FROM mascotas WHERE id = " + id;
            
            ejecutar = (PreparedStatement) conectado.prepareCall(sentenciaSQL);
            resultado = ejecutar.executeQuery();
            
            while (resultado.next()) {
                    mm = new MascotasModelo(
                            resultado.getInt("id"),
                            cc.buscarCliente(resultado.getString("cliente_cedula")),
                            resultado.getString("nombre"),
                            resultado.getString("tipo_animal"),
                            resultado.getDate("fecha_nacimiento"),
                            resultado.getDouble("peso"),
                            resultado.getString("raza"),
                            resultado.getBoolean("vacunas"),
                            resultado.getString("observaciones"));
                System.out.println();
            }
            ejecutar.close();
            return mm;
        } catch (SQLException e) {
            System.out.println("ERROR SQL: "+e);
        }
        return null;
    }
    
    public ArrayList<MascotasModelo> listarMascotas() {
        ArrayList<MascotasModelo> listaMascotas = new ArrayList<>();
        
        try {
            String sentenciaSQL = "SELECT id, cliente_cedula, nombre, tipo_animal, fecha_nacimiento, peso, raza, vacunas, observaciones " +
                          "FROM mascotas";
            
            ejecutar = (PreparedStatement) conectado.prepareCall(sentenciaSQL);
            resultado = ejecutar.executeQuery();
            
            while (resultado.next()) {
                listaMascotas.add(new MascotasModelo(
                        resultado.getInt("id"),
                        cc.buscarCliente(resultado.getString("cliente_cedula")),
                        resultado.getString("nombre"),
                        resultado.getString("tipo_animal"),
                        resultado.getDate("fecha_nacimiento"),
                        resultado.getDouble("peso"),
                        resultado.getString("raza"),
                        resultado.getBoolean("vacunas"),
                        resultado.getString("observaciones")
                ));
            }
            ejecutar.close();
            return listaMascotas;
        } catch (SQLException e) {
            System.out.println("ERROR SQL: "+e);
        }
        return null;
    }
    
    public ArrayList<MascotasModelo> listarMascotasPorDueno(String cedula) {
        ArrayList<MascotasModelo> listaMascotas = new ArrayList<>();
        
        try {
            String sentenciaSQL = "SELECT id, cliente_cedula, nombre, tipo_animal, fecha_nacimiento, peso, raza, vacunas, observaciones " +
                          "FROM mascotas WHERE cliente_cedula = " + cedula;
            
            ejecutar = (PreparedStatement) conectado.prepareCall(sentenciaSQL);
            resultado = ejecutar.executeQuery();
            
            while (resultado.next()) {
                listaMascotas.add(new MascotasModelo(
                        resultado.getInt("id"),
                        cc.buscarCliente(resultado.getString("cliente_cedula")),
                        resultado.getString("nombre"),
                        resultado.getString("tipo_animal"),
                        resultado.getDate("fecha_nacimiento"),
                        resultado.getDouble("peso"),
                        resultado.getString("raza"),
                        resultado.getBoolean("vacunas"),
                        resultado.getString("observaciones")
                ));
            }
            ejecutar.close();
            return listaMascotas;
        } catch (SQLException e) {
            System.out.println("ERROR SQL: "+e);
        }
        return null;
    }
    
    public void actualizarMascota(MascotasModelo mm){
        try {
            String sentenciaSQL = "UPDATE mascotas SET " +
                              "nombre = '" + mm.getNombre() + "', " +
                              "tipo_animal = '" + mm.getTipoAnimal() + "', " +
                              "fecha_nacimiento = '" + mm.getFechaNacimiento() + "', " +
                              "peso = " + mm.getPeso() + ", " +
                              "raza = '" + mm.getRaza() + "', " +
                              "vacunas = " + mm.isVacunas() + ", " +
                              "observaciones = '" + mm.getObservaciones() + "' " +
                              "WHERE id = " + mm.getId() + ";";
            
            ejecutar = conectado.prepareCall(sentenciaSQL);
            int res = ejecutar.executeUpdate();
            if(res > 0 ) {
                System.out.println("Se ha modificado la persona");
                ejecutar.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void eliminarMascotas(int id) {
        try {
            String sentenciaSQL = "DELETE FROM mascotas WHERE id = " + id + ";";
            ejecutar = conectado.prepareCall(sentenciaSQL);
            int res = ejecutar.executeUpdate();
            if(res > 0 ) {
                System.out.println("Se ha eliminado la persona");
                ejecutar.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

