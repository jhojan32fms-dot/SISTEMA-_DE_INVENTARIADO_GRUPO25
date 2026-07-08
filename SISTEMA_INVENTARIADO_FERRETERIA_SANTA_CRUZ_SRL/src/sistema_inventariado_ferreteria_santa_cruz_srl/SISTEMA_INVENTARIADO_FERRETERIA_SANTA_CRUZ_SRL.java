package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.util.Scanner;

public class SISTEMA_INVENTARIADO_FERRETERIA_SANTA_CRUZ_SRL {

    public static void main(String[] args) {

        if (Conexion.conectar() == null) {
            System.out.println("No se pudo establecer la conexion.");
            return;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 9e57cb29090285f2b3b6593663c18e9b5bd054f9
        }

        Scanner sc = new Scanner(System.in);
        Login login = new Login();

        Usuario usuarioLogeado = null;

        System.out.println("===== FERRETERIA SANTA CRUZ S.R.L =====");

        while (usuarioLogeado == null) {

            System.out.print("Usuario: ");
            String username = sc.nextLine();

            System.out.print("Contraseña: ");
            String password = sc.nextLine();

            usuarioLogeado = login.iniciarSesion(username, password);

            if (usuarioLogeado == null) {
                System.out.println("\nUsuario y/o contraseña incorrectos.\n");
            }
        }

<<<<<<< HEAD
=======
        }

        Scanner sc = new Scanner(System.in);
        Login login = new Login();

        Usuario usuarioLogeado = null;

        System.out.println("===== FERRETERIA SANTA CRUZ =====");

        while (usuarioLogeado == null) {

            System.out.print("Usuario: ");
            String username = sc.nextLine();

            System.out.print("Contraseña: ");
            String password = sc.nextLine();

            usuarioLogeado = login.iniciarSesion(username, password);

            if (usuarioLogeado == null) {
                System.out.println("\nUsuario y/o contraseña incorrectos.\n");
            }
        }

>>>>>>> 26a38f1d5ffb15c88c7d96b9f03599646a057594
=======
>>>>>>> 9e57cb29090285f2b3b6593663c18e9b5bd054f9
        System.out.println("\nBienvenido al sistema " + usuarioLogeado.getNombre());

        // RF 35 - Cargar información almacenada al iniciar
        SistemaDAO sistemaDAO = new SistemaDAO();
        sistemaDAO.cargarDatosAlIniciar();

        Menu menu = new Menu();
        menu.mostrar();

        sc.close();
    }
}