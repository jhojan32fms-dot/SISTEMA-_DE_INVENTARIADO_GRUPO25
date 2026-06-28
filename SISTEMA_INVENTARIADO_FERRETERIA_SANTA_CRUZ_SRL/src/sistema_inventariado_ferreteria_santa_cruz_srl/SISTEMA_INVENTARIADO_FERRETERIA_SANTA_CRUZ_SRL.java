package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.util.Scanner;

public class SISTEMA_INVENTARIADO_FERRETERIA_SANTA_CRUZ_SRL {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Login login = new Login();

        String usuario;
        String contraseña;
        int opcion;

        System.out.println("===== FERRETERIA SANTA CRUZ S.R.L =====");

        System.out.print("Usuario: ");
        usuario = sc.nextLine();

        System.out.print("Contraseña: ");
        contraseña = sc.nextLine();

        if (login.validar(usuario, contraseña)) {

            System.out.println("\nBienvenido al sistema.");

            System.out.println("\n------ MENU ------");
            System.out.println("1. Registrar producto");
            System.out.println("2. Buscar producto por codigo");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Registrar producto...");
                    break;

                case 2:
                    System.out.println("Buscar producto...");
                    break;

                case 3:
                    System.out.println("Gracias por usar el sistema.");
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
            }

        } else {

            System.out.println("Usuario o contraseña incorrectos.");

        }

        sc.close();

    }

}