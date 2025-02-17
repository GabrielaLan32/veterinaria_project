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
import modelo.ClientesModelo;

/**
 *
 * @author USER-LENOVO
 */
public class ClientesControlador {
    
    ConexionBDD conectar = new ConexionBDD();
    Connection conectado = (Connection) conectar.conectar();
    PreparedStatement ejecutar;
    ResultSet resultado;
    
    public void insertarPersona(ClientesModelo cm) {
        try {
            String sentenciaSQL = "INSERT INTO personas (cedula, nombres, apellidos) VALUES ('" +
                cm.getCedula() + "', '" +
                cm.getNombres() + "', '" +
                cm.getApellidos() + "');";
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
    
    public void insertarCliente(ClientesModelo cm) {
        try {
            insertarPersona(cm);
            
            String sentenciaSQL = "INSERT INTO clientes (cedula, direccion, telefono, email) VALUES ('" +
                cm.getCedula() + "', '" +
                cm.getDireccion() + "', '" +
                cm.getTelefono() + "', '" +
                cm.getEmail() + "');";
            ejecutar = conectado.prepareCall(sentenciaSQL);
            int res = ejecutar.executeUpdate();
            if(res > 0 ) {
                JOptionPane.showMessageDialog(null,"Cliente Creado con éxito");
                ejecutar.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getSQLState());
        }
    }
    
    public ClientesModelo buscarCliente(String cedula) {
        ClientesModelo cm = new ClientesModelo("", "", "", "", "", "");
        try {
            String sentenciaSQL = "SELECT p.cedula, p.nombres, p.apellidos, c.direccion, c.telefono, c.email " +
                     "FROM personas p " +
                     "JOIN clientes c ON p.cedula = c.cedula " +
                     "WHERE p.cedula = " + cedula;
            ejecutar = (PreparedStatement) conectado.prepareCall(sentenciaSQL);
            resultado = ejecutar.executeQuery();
            
            while (resultado.next()) {
                cm = new ClientesModelo(
                        resultado.getString("cedula"),
                        resultado.getString("nombres"),
                        resultado.getString("apellidos"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono"),
                        resultado.getString("email")
                );
                System.out.println();
            }
            ejecutar.close();
            return cm;
        } catch (SQLException e) {
            System.out.println("ERROR SQL: "+e);
        }
        return null;
    }
    
    public ArrayList<ClientesModelo> listarClientes() {
        ArrayList<ClientesModelo> listaClientes = new ArrayList<>();
        
        try {
            String sentenciaSQL = "SELECT p.cedula, p.nombres, p.apellidos, c.direccion, c.telefono, c.email " +
                     "FROM personas p " +
                     "JOIN clientes c ON p.cedula = c.cedula ";
            
            ejecutar = (PreparedStatement) conectado.prepareCall(sentenciaSQL);
            resultado = ejecutar.executeQuery();
            
            while (resultado.next()) {
                listaClientes.add(
                        new ClientesModelo(
                        resultado.getString("cedula"),
                        resultado.getString("nombres"),
                        resultado.getString("apellidos"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono"),
                        resultado.getString("email"))
                );
            }
            ejecutar.close();
            return listaClientes;
        } catch (SQLException e) {
            System.out.println("ERROR SQL: "+e);
        }
        return null;
    }
    
    public void actualizarCliente(ClientesModelo cm){
        try {
            String sentenciaSQL = "UPDATE clientes SET " +
                          "direccion = '" + cm.getDireccion() + "', " +
                          "telefono = '" + cm.getTelefono() + "', " +
                          "email = '" + cm.getEmail() + "' " +
                          "WHERE cedula = '" + cm.getCedula() + "';";
            
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
    
    public void eliminarCliente(String cedula) {
        try {
            String sentenciaSQL = "DELETE FROM clientes WHERE cedula = '" + cedula + "';";
            
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
