package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.util.Scanner;

public class Menu {

    public void mostrar() {

        Scanner sc = new Scanner(System.in);

        int opcion;

        do {

            System.out.println("\n------ MENU ------");
            System.out.println("1. Registrar producto");
            System.out.println("2. Buscar producto");
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

        } while (opcion != 3);

    }

}