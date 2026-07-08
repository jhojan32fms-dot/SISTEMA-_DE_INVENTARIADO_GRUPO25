package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReporteDAO {

    Connection cn = Conexion.conectar();

    // ===========================
    // RF25 - Mostrar movimientos por producto
    // ===========================
    public void mostrarMovimientosPorProducto(String codigo) {

        String sql = "SELECT * FROM v_movimientos_recientes WHERE codigo = ? ORDER BY fecha DESC";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();
            boolean hayDatos = false;

            while (rs.next()) {

                hayDatos = true;
                imprimirMovimiento(rs);
            }

            if (!hayDatos) {
                System.out.println("No se encontraron movimientos para el producto: " + codigo);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    // ===========================
    // RF26 - Mostrar movimientos por categoria
    // ===========================
    public void mostrarMovimientosPorCategoria(String categoria) {

        String sql = "SELECT * FROM v_movimientos_recientes WHERE categoria = ? ORDER BY fecha DESC";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, categoria);

            ResultSet rs = ps.executeQuery();
            boolean hayDatos = false;

            while (rs.next()) {

                hayDatos = true;
                imprimirMovimiento(rs);
            }

            if (!hayDatos) {
                System.out.println("No se encontraron movimientos para la categoria: " + categoria);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    private void imprimirMovimiento(ResultSet rs) throws SQLException {

        System.out.println("--------------------------------");
        System.out.println("Fecha: " + rs.getTimestamp("fecha"));
        System.out.println("Producto: " + rs.getString("producto") + " (" + rs.getString("codigo") + ")");
        System.out.println("Categoria: " + rs.getString("categoria"));
        System.out.println("Tipo: " + rs.getString("tipo"));
        System.out.println("Cantidad: " + rs.getInt("cantidad"));
        System.out.println("Motivo: " + rs.getString("motivo"));
        System.out.println("Stock antes: " + rs.getInt("stock_antes"));
        System.out.println("Stock despues: " + rs.getInt("stock_despues"));
        System.out.println("Registrado por: " + rs.getString("usuario"));
    }

    // ===========================
    // RF27 - Mostrar inventario general
    // ===========================
    public void mostrarInventarioGeneral() {

        String sql = "SELECT * FROM v_inventario_general";

        try {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            boolean hayDatos = false;

            System.out.println("================ INVENTARIO GENERAL ================");

            while (rs.next()) {

                hayDatos = true;

                System.out.println("--------------------------------");
                System.out.println("Codigo: " + rs.getString("codigo"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Categoria: " + rs.getString("categoria"));
                System.out.println("Unidad: " + rs.getString("unidad_medida"));
                System.out.println("Precio: " + rs.getDouble("precio"));
                System.out.println("Stock actual: " + rs.getInt("stock_actual"));
                System.out.println("Stock minimo: " + rs.getInt("stock_minimo"));
                System.out.println("Valor total: " + rs.getDouble("valor_total"));
            }

            if (!hayDatos) {
                System.out.println("No hay productos registrados en el inventario.");
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    // ===========================
    // RF28 - Mostrar alertas activas
    // ===========================
    public void mostrarAlertasActivas() {

        String sql = "SELECT * FROM v_stock_critico";

        try {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            boolean hayAlertas = false;

            System.out.println("================ ALERTAS ACTIVAS ================");

            while (rs.next()) {

                hayAlertas = true;

                System.out.println("--------------------------------");
                System.out.println("Codigo: " + rs.getString("codigo"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Categoria: " + rs.getString("categoria"));
                System.out.println("Stock actual: " + rs.getInt("stock_actual"));
                System.out.println("Stock minimo: " + rs.getInt("stock_minimo"));
                System.out.println("Estado: " + rs.getString("estado"));
            }

            if (!hayAlertas) {
                System.out.println("No hay alertas activas. El inventario esta dentro de los niveles normales.");
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    // ===========================
    // RF30 - Mostrar resumen de inventario
    // ===========================
    public void mostrarResumenInventario() {

        String sql = "SELECT "
                + "COUNT(*) AS total_productos, "
                + "SUM(precio * stock_actual) AS valor_total_inventario, "
                + "SUM(CASE WHEN stock_actual = 0 THEN 1 ELSE 0 END) AS productos_agotados, "
                + "SUM(CASE WHEN stock_actual <= stock_minimo AND stock_actual > 0 THEN 1 ELSE 0 END) AS productos_criticos "
                + "FROM productos WHERE activo = 1";

        try {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {

                System.out.println("================ RESUMEN DE INVENTARIO ================");
                System.out.println("Total de productos activos : " + rs.getInt("total_productos"));
                System.out.println("Valor total del inventario : S/ " + rs.getDouble("valor_total_inventario"));
                System.out.println("Productos agotados         : " + rs.getInt("productos_agotados"));
                System.out.println("Productos en estado critico: " + rs.getInt("productos_criticos"));
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

}
