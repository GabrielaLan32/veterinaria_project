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
import modelo.HorariosModelo;

/**
 *
 * @author USER-LENOVO
 */
public class HorarioControlador {
    ClientesControlador cc = new ClientesControlador();
    ConexionBDD conectar = new ConexionBDD();
    Connection conectado = (Connection) conectar.conectar();
    PreparedStatement ejecutar;
    ResultSet resultado;
    
    public void insertarHorario(HorariosModelo hm) {
        try {
            String sentenciaSQL = "INSERT INTO horarios (hora, disponibilidad) VALUES ('" +
                hm.getHora() + "', " + 
                hm.isDisponibilidad() + ");";
            
            ejecutar = conectado.prepareCall(sentenciaSQL);
            int res = ejecutar.executeUpdate();
            if(res > 0 ) {
                System.out.println("Se ha añadido el horario");
                ejecutar.close();
            }
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
    
    public HorariosModelo buscarHorario(int id) {
        HorariosModelo hm = null;
        try {
            String sentenciaSQL = "SELECT id, hora, disponibilidad FROM horarios " + 
                    "WHERE id = " + id;
            
            ejecutar = (PreparedStatement) conectado.prepareCall(sentenciaSQL);
            resultado = ejecutar.executeQuery();
            
            while (resultado.next()) {
                hm = new HorariosModelo(
                        resultado.getInt("id"),
                        resultado.getString("hora"), 
                        resultado.getBoolean("disponibilidad"));
                System.out.println();
            }
            ejecutar.close();
            return hm;
        } catch (SQLException e) {
            System.out.println("ERROR SQL: "+e);
        }
        return null;
    }
    
    public ArrayList<HorariosModelo> listarHorario() {
        ArrayList<HorariosModelo> listaHorario = new ArrayList<>();
        
        try {
            String sentenciaSQL = "SELECT id, hora, disponibilidad FROM horarios;";
            
            ejecutar = (PreparedStatement) conectado.prepareCall(sentenciaSQL);
            resultado = ejecutar.executeQuery();
            
            while (resultado.next()) {
                listaHorario.add(new HorariosModelo(
                        resultado.getInt("id"), 
                        resultado.getString("hora"), 
                        resultado.getBoolean("disponibilidad")));
            }
            ejecutar.close();
            return listaHorario;
        } catch (SQLException e) {
            System.out.println("ERROR SQL: "+e);
        }
        return null;
    }
    
    public void actualizarHorario(HorariosModelo hm) {
        try {
            String sentenciaSQL = "UPDATE horarios SET " +
                              "hora = '" + hm.getHora() + "', " +
                              "disponibilidad = " + hm.isDisponibilidad() + " " +
                              "WHERE id = " + hm.getId() + ";";
            
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
    
    public void eliminarHorario(int id) {
        try {
            String sentenciaSQL = "DELETE FROM horarios WHERE id = " + id + ";";
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
