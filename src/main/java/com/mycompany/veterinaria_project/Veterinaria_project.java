/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.veterinaria_project;

import controlador.ConexionBDD;

/**
 *
 * @author USER-LENOVO
 */
public class Veterinaria_project {

    public static void main(String[] args) {
        ConexionBDD conexion = new ConexionBDD();
        conexion.conectar();
    }
}
