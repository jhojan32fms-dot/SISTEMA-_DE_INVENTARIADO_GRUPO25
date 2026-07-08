package reportes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de la conexion a la base de datos MySQL (ferreteria_t1).
 * Ajusta USUARIO y PASSWORD segun tu configuracion local de MySQL.
 */
public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/ferreteria_t1?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = ""; // <-- coloca aqui tu password de MySQL

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
