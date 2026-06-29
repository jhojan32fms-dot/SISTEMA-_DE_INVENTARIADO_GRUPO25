package sistema_inventariado_ferreteria_santa_cruz_srl;

public class Usuario {

    private String nombre;
    private String usuario;
    private String contraseña;

    public Usuario(String nombre, String usuario, String contraseña) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

}