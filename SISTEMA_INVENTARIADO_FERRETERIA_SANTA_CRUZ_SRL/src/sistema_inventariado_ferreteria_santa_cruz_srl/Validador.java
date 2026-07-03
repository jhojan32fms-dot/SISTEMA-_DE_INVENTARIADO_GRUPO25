package sistema_inventariado_ferreteria_santa_cruz_srl;

public class Validador {

    private Validador() {
    }

    // RF 38 - Validar datos ingresados
    public static boolean validarTexto(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    // RF 38 - Validar datos ingresados
    public static boolean validarNumeroPositivo(double numero) {
        return numero > 0;
    }

    // RF 38 - Validar datos ingresados
    public static boolean validarConfirmacion(String respuesta) {

        if (respuesta == null) {
            return false;
        }

        String r = respuesta.trim().toUpperCase();

        return r.equals("S") || r.equals("N");
    }

}
