package modelo;

import java.util.Date;

public class Solicitud {
    private int id;
    private int idEmpleado;
    private Date fechaSalida;
    private Date fechaRegreso;
    private String destino;
    private String motivoViaje;
    private String motivoRespuesta;
    private int pasajeros;
    private String estado;
    private Date fechaEstado;
    
    public Solicitud() {}

    public Solicitud(int id, int idEmpleado, Date fechaSalida, Date fechaRegreso,
                     String destino, String motivoViaje, String motivoRespuesta,
                     int pasajeros, String estado) {

        this.id = id;
        this.idEmpleado = idEmpleado;
        this.fechaSalida = fechaSalida;
        this.fechaRegreso = fechaRegreso;
        this.destino = destino;
        this.motivoViaje = motivoViaje;
        this.motivoRespuesta = motivoRespuesta;
        this.pasajeros = pasajeros;
        this.estado = estado;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaRegreso() {
        return fechaRegreso;
    }

    public void setFechaRegreso(Date fechaRegreso) {
        this.fechaRegreso = fechaRegreso;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getMotivoViaje() {
        return motivoViaje;
    }

    public void setMotivoViaje(String motivoViaje) {
        this.motivoViaje = motivoViaje;
    }

    public String getMotivoRespuesta() {
        return motivoRespuesta;
    }

    public void setMotivoRespuesta(String motivoRespuesta) {
        this.motivoRespuesta = motivoRespuesta;
    }

    public int getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(int pasajeros) {
        this.pasajeros = pasajeros;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
         
    public Date getFechaEstado(){ 
        return fechaEstado;
    }
    public void setFechaEstado(Date fechaEstado) { 
        this.fechaEstado = fechaEstado; 
    }
}
