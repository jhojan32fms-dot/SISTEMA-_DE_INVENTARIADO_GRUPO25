package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SistemaDAO {

    // RF 35 - Cargar información almacenada al iniciar
    public void cargarDatosAlIniciar() {

        Connection cn = Conexion.conectar();

        String sql = "SELECT "
                + "(SELECT COUNT(*) FROM productos WHERE activo = 1) AS total_productos, "
                + "(SELECT COUNT(*) FROM categorias) AS total_categorias, "
                + "(SELECT COUNT(*) FROM movimientos) AS total_movimientos";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                /*System.out.println("\n===== Datos cargados desde la base de datos =====");
                System.out.println("Productos registrados   : " + rs.getInt("total_productos"));
                System.out.println("Categorias registradas  : " + rs.getInt("total_categorias"));
                System.out.println("Movimientos registrados : " + rs.getInt("total_movimientos"));
                */
                // RF 36 - Confirmar operaciones
                MensajeUtil.confirmarOperacion("Información almacenada cargada correctamente.");
            }

        } catch (SQLException e) {

            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("No se pudo cargar la información almacenada: " + e.getMessage());
        }
    }

}
