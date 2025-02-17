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
import javax.swing.JOptionPane;
import modelo.AdministradoresModelo;

/**
 *
 * @author USER-LENOVO
 */
public class AdministradoresControlador {
    ClientesControlador cc = new ClientesControlador();
    ConexionBDD conectar = new ConexionBDD();
    Connection conectado = (Connection) conectar.conectar();
    PreparedStatement ejecutar;
    ResultSet resultado;
    
    public void insertarAdministrador(AdministradoresModelo am){
        try {
            String sentenciaSQL = "INSERT INTO administradores (usuario, contrasena) VALUES ('" +
                am.getUsuario() + "', '" +
                am.getContrasena() + "');";
            
            ejecutar = conectado.prepareCall(sentenciaSQL);
            int res = ejecutar.executeUpdate();
            if(res > 0 ) {
                System.out.println("Se ha añadido el administrador");
                ejecutar.close();
            }
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
    
    public AdministradoresModelo buscarAdministrador(int id) {
        AdministradoresModelo am = new AdministradoresModelo(0, "", "");
        
        try {
            String sentenciaSQL = "SELECT id, usuario, contrasena FROM administradores WHERE id = " + id + ";";
            
            ejecutar = (PreparedStatement) conectado.prepareCall(sentenciaSQL);
            resultado = ejecutar.executeQuery();
            
            while (resultado.next()) {
                am = new AdministradoresModelo(id, sentenciaSQL, sentenciaSQL);
                System.out.println();
            }
            ejecutar.close();
            return am;
        } catch (SQLException e) {
            System.out.println("ERROR SQL: "+e);
        }
        return null;
    }
    
    public ArrayList<AdministradoresModelo> listarAdministradores() {
        ArrayList<AdministradoresModelo> listaAdministradores = new ArrayList<>();
        
        try {
            String sentenciaSQL = "SELECT id, usuario, contrasena FROM administradores;";
            
            ejecutar = (PreparedStatement) conectado.prepareCall(sentenciaSQL);
            resultado = ejecutar.executeQuery();
            
            while (resultado.next()) {
                listaAdministradores.add(new AdministradoresModelo(
                        resultado.getInt("id"),
                        resultado.getString("usuario"), 
                        resultado.getString("contrasena")));
            }
            ejecutar.close();
            return listaAdministradores;
        } catch (SQLException e) {
            System.out.println("ERROR SQL: "+e);
        }
        return null;
    }
    
    public void actualizarAdministradores(AdministradoresModelo am) {
        try {
            String sentenciaSQL = "UPDATE administradores SET " +
                              "usuario = '" + am.getUsuario()+ "', " +
                              "contrasena = '" + am.getContrasena() + "' " +
                              "WHERE id = " + am.getId() + ";";
            
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
    
    public void eliminarAdministrador(int id ) {
        try {
            String sentenciaSQL = "DELETE FROM administradores WHERE id = " + id + ";";
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
    
    public boolean iniciarSesion(String usuario, String contrasena) {
        boolean session = false;
        try {
            String sentenciaSQL = "SELECT contrasena FROM administradores WHERE usuario = '" + usuario + "';";
            
            ejecutar = (PreparedStatement) conectado.prepareCall(sentenciaSQL);
            resultado = ejecutar.executeQuery();
            
            if (resultado.next()) {
                String contrasenaAlmacenada = resultado.getString("contrasena");

                if (contrasenaAlmacenada.equals(contrasena)) {
                    session = true;
                } else {
                    System.out.println("Error: Contraseña incorrecta.");
                    JOptionPane.showMessageDialog(null, "Error: Contraseña incorrecta.");
                }
            } else {
                System.out.println("Error: Usuario no encontrado.");
                JOptionPane.showMessageDialog(null, "Error: Usuario no encontrado.");
            }
            
            ejecutar.close();
        } catch (SQLException e) {
            System.out.println("ERROR SQL: " + e);
        }
        return session;
    }
}
