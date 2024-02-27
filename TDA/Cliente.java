package TPO.TDA;

public class Cliente {
    private Dni dni;
    private String apellidos;
    private String nombres;
    private String telefono;
    private String email;
    
    public Cliente(Dni dni, String apellidos, String nombres, String telefono, String email) {
        this.dni = dni;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.telefono = telefono;
        this.email = email;
    }

    public Dni getDni() {
        return dni;
    }

    public void setDni(Dni dni) {
        this.dni = dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente [DOC=" + dni + ", Apellidos=" + apellidos + ", Nombres=" + nombres + ", Telefono=" + telefono
                + ", Email=" + email + "]";
    }

}
