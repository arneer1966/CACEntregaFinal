package modelo;

public class Usuario {

    //private con los metodos del padre sino con protected
    private long documento;
    private String apellido;
    private String nombres;
    private String direccion;
    private String email;
    private String password;
    private int estado;
    private String tipo;

    public Usuario() {
    }

    public Usuario(long documento, String apellido, String nombres, String direccion, String email, String password, int estado, String tipo) {
        this.documento = documento;
        this.apellido = apellido;
        this.nombres = nombres;
        this.direccion = direccion;
        this.email = email;
        this.password = password;
        this.estado = estado;
        this.tipo = tipo;
    }

    public String getApellido() {
        return apellido;
    }

    public long getDocumento() {
        return documento;
    }

    public String getNombres() {
        return nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getEstado() {
        return estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDocumento(long documento) {
        this.documento = documento;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void mostrarUsuario() {
        System.out.println("Documento:" + documento);
        System.out.println("Apellido: " + apellido);
        System.out.println("Nombres:  " + nombres);
        System.out.println("Direccion:" + direccion);
        System.out.println("Email:    " + email);
        System.out.println("Password: " + password);
        System.out.println("Estado:   " + estado);
        System.out.println("Tipo:     " + tipo);
    }

    @Override
    public String toString() {
        return "Documento:" + documento + ",Apellido: " + apellido + ",Nombres:  " + nombres + ",Direccion:" + direccion + ",Email: " + email + ",Password: " + password + ",Estado: " + estado + ",Tipo: " + tipo;
    }

    public void mostrarDetalles() {
        System.out.println(toString());
    }

}
