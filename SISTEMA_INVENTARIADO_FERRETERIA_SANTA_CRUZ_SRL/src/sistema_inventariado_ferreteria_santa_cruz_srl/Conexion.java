package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/ferreteria_t1";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "root";

    public static Connection conectar() {

        try {

            Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexion exitosa a la base de datos.");
            return conexion;

        } catch (Exception e) {

            System.out.println("Error al conectar: " + e.getMessage());
            return null;

        }

    }

}