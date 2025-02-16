/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USER-LENOVO
 */
public class ConexionBDD {
    java.sql.Connection conexion;
    String urlDatabase = "jdbc:mysql://localhost:3306/veterinaria_project";
     public java.sql.Connection conectar(){
        //LANZAR CÓDIGO DE PRUEBA 
        try {
            //Manera de Conexión a la Base de Datos
            Class.forName("com.mysql.jdbc.Driver");
            //Parámetros de conexión url/usuario/clave en mysql
            conexion=DriverManager.getConnection(urlDatabase,"root","gaby321");
            System.out.println("CONECTADO"); 
        } catch (ClassNotFoundException | SQLException e)//CAPTURAR ERRORES 
        {
             System.out.println("ERROR DE CONEXION A LA BASE DE DATOS");
             System.out.println(e);
        }
        return conexion;
    }
}
