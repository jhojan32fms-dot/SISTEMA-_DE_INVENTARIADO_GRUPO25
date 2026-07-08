package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {

    public Usuario iniciarSesion(String usuario, String contraseña) {

        String sql = "SELECT id_usuario, nombre, username, password FROM usuarios WHERE username = ? AND password = ? AND activo = 1";

        try {

            Connection conexion = Conexion.conectar();

            PreparedStatement ps = conexion.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, contraseña);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }

        } catch (Exception e) {
            System.out.println("Error al iniciar sesion: " + e.getMessage());
        }

        return null;
    }
}