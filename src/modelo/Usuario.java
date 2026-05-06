package modelo;

public class Usuario {

    private int idUsuario;
    private String username;
    private String rol;
    private boolean estado;
    private boolean debeCambiarPassword;
   
    private String nombreCompleto;
    private int idEmpleado;

    public Usuario() {}
    
    public Usuario(int idUsuario, String username, String rol, boolean estado, 
            boolean debeCambiarPassword, String nombreCompleto, int idEmpleado) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.rol = rol;
        this.estado = estado;
        this.debeCambiarPassword = debeCambiarPassword;
        this.nombreCompleto = nombreCompleto;
        this.idEmpleado = idEmpleado;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isDebeCambiarPassword() {
        return debeCambiarPassword;
    }

    public void setDebeCambiarPassword(boolean debeCambiarPassword) {
        this.debeCambiarPassword = debeCambiarPassword;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

   
}