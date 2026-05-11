package modelo;

import java.util.Date;

public class Historial {

    private int id;
    private String empleado;
    private String conductor;
    private String vehiculo;
    private String placa;
    private String destino;
    private String estado;

    private double kmSalida;
    private double kmRegreso;

    private Date fechaSalida;
    private Date fechaRegreso;
    private Date fechaAsignacion;
    private Date fechaDevolucion;

    public Historial() {
    }
    
    public Historial(int id, String empleado, String conductor, String vehiculo, String placa, String destino, String estado, double kmSalida, double kmRegreso, Date fechaSalida, Date fechaRegreso, Date fechaAsignacion, Date fechaDevolucion) {
        this.id = id;
        this.empleado = empleado;
        this.conductor = conductor;
        this.vehiculo = vehiculo;
        this.placa = placa;
        this.destino = destino;
        this.estado = estado;
        this.kmSalida = kmSalida;
        this.kmRegreso = kmRegreso;
        this.fechaSalida = fechaSalida;
        this.fechaRegreso = fechaRegreso;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getKmSalida() {
        return kmSalida;
    }

    public void setKmSalida(double kmSalida) {
        this.kmSalida = kmSalida;
    }

    public double getKmRegreso() {
        return kmRegreso;
    }

    public void setKmRegreso(double kmRegreso) {
        this.kmRegreso = kmRegreso;
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

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}