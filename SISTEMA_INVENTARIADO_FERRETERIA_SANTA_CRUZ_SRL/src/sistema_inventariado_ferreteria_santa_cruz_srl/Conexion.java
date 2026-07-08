package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/ferreteria_t1";
    private static final String USUARIO = "root";
<<<<<<< HEAD
    private static final String PASSWORD = "Royer.0407+";
=======
<<<<<<< HEAD
<<<<<<< HEAD
    private static final String PASSWORD = "12345678";
=======
    private static final String PASSWORD = "root";
>>>>>>> 26a38f1d5ffb15c88c7d96b9f03599646a057594
=======
    private static final String PASSWORD = "root";
>>>>>>> 9e57cb29090285f2b3b6593663c18e9b5bd054f9
>>>>>>> e146ef180febb0ce5600186dc61533149216f24c

    public static Connection conectar() {

        try {
            Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
<<<<<<< HEAD
<<<<<<< HEAD
            System.out.println("Conexion exitosa a la base de datos.");
=======
            //System.out.println("Conexion exitosa a la base de datos.");
>>>>>>> 26a38f1d5ffb15c88c7d96b9f03599646a057594
=======
            System.out.println("Conexion exitosa a la base de datos.");
>>>>>>> 9e57cb29090285f2b3b6593663c18e9b5bd054f9
            return conexion;

        } catch (Exception e) {

            System.out.println("Error al conectar: " + e.getMessage());
            return null;

        }

    }

}