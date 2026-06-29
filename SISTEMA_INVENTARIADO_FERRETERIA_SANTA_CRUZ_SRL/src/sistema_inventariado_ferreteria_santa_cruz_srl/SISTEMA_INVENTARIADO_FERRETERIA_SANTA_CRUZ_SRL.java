package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.util.Scanner;

public class SISTEMA_INVENTARIADO_FERRETERIA_SANTA_CRUZ_SRL {

    public static void main(String[] args) {

        if (Conexion.conectar() == null) {
            System.out.println("No se pudo establecer la conexion.");
            return;
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

        System.out.println("\nBienvenido al sistema " + usuarioLogeado.getNombre());

        Menu menu = new Menu();
        menu.mostrar();

        sc.close();
    }
}