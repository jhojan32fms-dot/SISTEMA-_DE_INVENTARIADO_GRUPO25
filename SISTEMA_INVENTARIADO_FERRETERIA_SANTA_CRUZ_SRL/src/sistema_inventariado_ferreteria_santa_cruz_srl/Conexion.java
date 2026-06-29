package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/ferreteria_t1";
    private static final String USUARIO = "root";
<<<<<<< HEAD
    private static final String PASSWORD = "12345678";
=======
    private static final String PASSWORD = "root";
>>>>>>> 26a38f1d5ffb15c88c7d96b9f03599646a057594

    public static Connection conectar() {

        try {
            Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
<<<<<<< HEAD
            System.out.println("Conexion exitosa a la base de datos.");
=======
            //System.out.println("Conexion exitosa a la base de datos.");
>>>>>>> 26a38f1d5ffb15c88c7d96b9f03599646a057594
            return conexion;

        } catch (Exception e) {

            System.out.println("Error al conectar: " + e.getMessage());
            return null;

        }

    }

}