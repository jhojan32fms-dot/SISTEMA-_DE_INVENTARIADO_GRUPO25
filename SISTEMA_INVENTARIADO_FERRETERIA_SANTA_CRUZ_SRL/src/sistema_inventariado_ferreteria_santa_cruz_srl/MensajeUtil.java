package sistema_inventariado_ferreteria_santa_cruz_srl;

public class MensajeUtil {

    private MensajeUtil() {
    }

    // RF 36 - Confirmar operaciones
    public static void confirmarOperacion(String mensaje) {
        System.out.println("\n[OK] " + mensaje);
    }

    // RF 37 - Mostrar mensajes de error
    public static void mostrarError(String mensaje) {
        System.out.println("\n[ERROR] " + mensaje);
    }

}
