package modelo;

import java.util.Date;

public class DevolucionVehiculo {

    private int idDevolucion;
    private int idAsignacion;
    private double kilometrajeSalida;
    private double kilometrajeRegreso;
    private String observaciones;
    private Date fechaDevolucion;

    public DevolucionVehiculo() {
    }
    
    public DevolucionVehiculo(int idDevolucion, int idAsignacion, double kilometrajeSalida, double kilometrajeRegreso, String observaciones, Date fechaDevolucion) {
        this.idDevolucion = idDevolucion;
        this.idAsignacion = idAsignacion;
        this.kilometrajeSalida = kilometrajeSalida;
        this.kilometrajeRegreso = kilometrajeRegreso;
        this.observaciones = observaciones;
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(int idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public double getKilometrajeSalida() {
        return kilometrajeSalida;
    }

    public void setKilometrajeSalida(double kilometrajeSalida) {
        this.kilometrajeSalida = kilometrajeSalida;
    }

    public double getKilometrajeRegreso() {
        return kilometrajeRegreso;
    }

    public void setKilometrajeRegreso(double kilometrajeRegreso) {
        this.kilometrajeRegreso = kilometrajeRegreso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}