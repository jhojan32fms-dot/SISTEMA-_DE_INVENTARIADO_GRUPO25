package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RespaldoDAO {

    Connection cn = Conexion.conectar();

    // RF 39 - Generar respaldo de datos
    public boolean generarRespaldo() {

        try {

            Statement st = cn.createStatement();

            // Respaldo de productos
            st.executeUpdate("DROP TABLE IF EXISTS productos_respaldo");
            st.executeUpdate("CREATE TABLE productos_respaldo AS "
                    + "SELECT p.*, NOW() AS fecha_respaldo FROM productos p");

            // Respaldo de movimientos
            st.executeUpdate("DROP TABLE IF EXISTS movimientos_respaldo");
            st.executeUpdate("CREATE TABLE movimientos_respaldo AS "
                    + "SELECT m.*, NOW() AS fecha_respaldo FROM movimientos m");

            return true;

        } catch (SQLException e) {

            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("No se pudo generar el respaldo: " + e.getMessage());
        }

        return false;
    }

}
