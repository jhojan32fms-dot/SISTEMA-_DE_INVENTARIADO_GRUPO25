package sistema_inventariado_ferreteria_santa_cruz_srl;

public class Login {

    // Usuario de prueba
    Usuario usuario1 = new Usuario("admin", "1234");

    public boolean validar(String usuario, String contraseña) {

        if (usuario.equals(usuario1.getUsuario())
                && contraseña.equals(usuario1.getContraseña())) {

            return true;
        }

        return false;
    }

}