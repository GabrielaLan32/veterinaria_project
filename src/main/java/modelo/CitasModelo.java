/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USER-LENOVO
 */
public class CitasModelo {
    private int id;
    private MascotasModelo mascota;
    private HorariosModelo horario;
    private String motivoConsulta;

    public CitasModelo(int id, MascotasModelo mascota, HorariosModelo horario, String motivoConsulta) {
        this.id = id;
        this.mascota = mascota;
        this.horario = horario;
        this.motivoConsulta = motivoConsulta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MascotasModelo getMascota() {
        return mascota;
    }

    public void setMascota(MascotasModelo mascota) {
        this.mascota = mascota;
    }

    public HorariosModelo getHorario() {
        return horario;
    }

    public void setHorario(HorariosModelo horario) {
        this.horario = horario;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }
}
