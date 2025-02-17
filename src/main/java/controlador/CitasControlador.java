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
import modelo.CitasModelo;

/**
 *
 * @author USER-LENOVO
 */
public class CitasControlador {
    MascotaControlador mc = new MascotaControlador();
    HorarioControlador hc = new HorarioControlador();
    ConexionBDD conectar = new ConexionBDD();
    Connection conectado = (Connection) conectar.conectar();
    PreparedStatement ejecutar;
    ResultSet resultado;
    
    public void insertarCitas(CitasModelo cm) {
        String sentenciaSQL = "CALL Sp_crear_citas(@p_citas_id, " + cm.getMascota().getId() + ", " + cm.getHorario().getId()+ ", '" + cm.getMotivoConsulta() + "');";
        
        try {
            ejecutar = conectado.prepareCall(sentenciaSQL);
            int res = ejecutar.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null,"Cita Creada con éxito");//llamar ventanas emergentes--sacar mensaje de diálogo
                System.out.println("Se ha añadido la cita");
                ejecutar.close();
            }
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
    
    public CitasModelo buscarCita(int id){
        CitasModelo cm = null;
        
        String sentenciaSQL = "CALL Sp_obtener_citas(" + id + ", @p_mascota_id, @p_horario_id, @p_motivo_consulta);";
        try {
            ejecutar = (PreparedStatement) conectado.prepareCall(sentenciaSQL);
            resultado = ejecutar.executeQuery();
            
            while (resultado.next()) {
                cm = new CitasModelo(
                        resultado.getInt(1),
                        mc.buscarMascota(resultado.getInt(2)),
                        hc.buscarHorario(resultado.getInt(3)), 
                        resultado.getString(4));
                System.out.println();
            }
            ejecutar.close();
            return cm;
        } catch (SQLException e) {
            System.out.println("ERROR SQL"+e);
        }
        return null;
    }
    
    public ArrayList<CitasModelo> listarCitas() {
        ArrayList<CitasModelo> listaCitas = new ArrayList<>();
        
        try {
            String sentenciaSQL = "SELECT id, mascota_id, horario_id, motivo_consulta FROM citas;";
            
            ejecutar = (PreparedStatement) conectado.prepareCall(sentenciaSQL);
            resultado = ejecutar.executeQuery();
            
            while (resultado.next()) {
                listaCitas.add(new CitasModelo(
                        resultado.getInt("id"), 
                        mc.buscarMascota(resultado.getInt("mascota_id")), 
                        hc.buscarHorario(resultado.getInt("horario_id")), 
                        resultado.getString("motivo_consulta")));
            }
            ejecutar.close();
            return listaCitas;
        } catch (SQLException e) {
            System.out.println("ERROR SQL: "+e);
        }
        return null;
    }
    
    public void actualizarCita(CitasModelo cm) {
        try {
            String sentenciaSQL = "CALL Sp_modificar_citas(" + cm.getId() + ", @p_mascota_id, " + 
                              cm.getHorario().getId() + ", '" + 
                              cm.getMotivoConsulta() + "');";
            
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
    
    public void eliminarCitas(int id) {
        try {
            String sentenciaSQL = "CALL Sp_eliminar_citas(" + id + ", @p_mascota_id, @p_horario_id);";
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
