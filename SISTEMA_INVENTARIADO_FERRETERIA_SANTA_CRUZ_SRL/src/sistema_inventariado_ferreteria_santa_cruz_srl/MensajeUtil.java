package sistema_inventariado_ferreteria_santa_cruz_srl;

public class MensajeUtil {

    private MensajeUtil() {
    }

    private static final String VERDE = "\u001B[32m";
    private static final String ROJO = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    // RF 36 - Confirmar operaciones
    public static void confirmarOperacion(String mensaje) {
        System.out.println("\n" + VERDE + "[OK] " + mensaje + RESET);
    }

    // RF 37 - Mostrar mensajes de error
    public static void mostrarError(String mensaje) {
        System.out.println("\n" + ROJO + "[ERROR] " + mensaje + RESET);
    }

}
