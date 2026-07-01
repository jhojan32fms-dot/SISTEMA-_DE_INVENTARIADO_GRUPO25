package sistema_inventariado_ferreteria_santa_cruz_srl;

public class MenuValidator {

    private MenuValidator() {
    }

    public static boolean validarOpcion(int opcion, int minimo, int maximo) {

        if (opcion < minimo || opcion > maximo) {
            System.out.println("Opción inválida. Debe seleccionar una opción entre "
                    + minimo + " y " + maximo + ".");
            return false;
        }

        return true;
    }

}