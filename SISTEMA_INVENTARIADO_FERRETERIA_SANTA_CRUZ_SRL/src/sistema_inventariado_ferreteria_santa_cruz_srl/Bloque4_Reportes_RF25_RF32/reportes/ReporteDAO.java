package reportes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Bloque 4 - Reportes y Consultas (RF25 - RF30)
 * Cada metodo devuelve los datos listos para mostrarse en consola
 * o para ser exportados a un archivo con exportarReporte().
 */
public class ReporteDAO {

    // ============================================================
    // RF25. Mostrar movimientos por producto
    // ============================================================
    public List<String[]> movimientosPorProducto(String codigo) throws SQLException {
        String sql = "SELECT fecha, codigo, producto, categoria, tipo, cantidad, motivo, "
                + "stock_antes, stock_despues, usuario "
                + "FROM v_movimientos_recientes WHERE codigo = ?";

        List<String[]> resultados = new ArrayList<>();
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultados.add(new String[]{
                        rs.getString("fecha"),
                        rs.getString("codigo"),
                        rs.getString("producto"),
                        rs.getString("categoria"),
                        rs.getString("tipo"),
                        rs.getString("cantidad"),
                        rs.getString("motivo"),
                        rs.getString("stock_antes"),
                        rs.getString("stock_despues"),
                        rs.getString("usuario")
                    });
                }
            }
        }
        return resultados;
    }

    public String[] encabezadosMovimientos() {
        return new String[]{"Fecha", "Codigo", "Producto", "Categoria", "Tipo",
            "Cantidad", "Motivo", "Stock Antes", "Stock Despues", "Usuario"};
    }

    // ============================================================
    // RF26. Mostrar movimientos por categoria
    // ============================================================
    public List<String[]> movimientosPorCategoria(String nombreCategoria) throws SQLException {
        String sql = "SELECT fecha, codigo, producto, categoria, tipo, cantidad, motivo, "
                + "stock_antes, stock_despues, usuario "
                + "FROM v_movimientos_recientes WHERE categoria = ?";

        List<String[]> resultados = new ArrayList<>();
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultados.add(new String[]{
                        rs.getString("fecha"),
                        rs.getString("codigo"),
                        rs.getString("producto"),
                        rs.getString("categoria"),
                        rs.getString("tipo"),
                        rs.getString("cantidad"),
                        rs.getString("motivo"),
                        rs.getString("stock_antes"),
                        rs.getString("stock_despues"),
                        rs.getString("usuario")
                    });
                }
            }
        }
        return resultados;
    }

    // ============================================================
    // RF27. Mostrar inventario general
    // ============================================================
    public List<String[]> inventarioGeneral() throws SQLException {
        String sql = "SELECT codigo, nombre, categoria, unidad_medida, precio, "
                + "stock_actual, stock_minimo, valor_total FROM v_inventario_general";

        List<String[]> resultados = new ArrayList<>();
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultados.add(new String[]{
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getString("unidad_medida"),
                    rs.getString("precio"),
                    rs.getString("stock_actual"),
                    rs.getString("stock_minimo"),
                    rs.getString("valor_total")
                });
            }
        }
        return resultados;
    }

    public String[] encabezadosInventario() {
        return new String[]{"Codigo", "Nombre", "Categoria", "Unidad", "Precio",
            "Stock Actual", "Stock Minimo", "Valor Total"};
    }

    // ============================================================
    // RF28. Mostrar alertas activas (stock critico / agotado)
    // ============================================================
    public List<String[]> alertasActivas() throws SQLException {
        String sql = "SELECT codigo, nombre, categoria, stock_actual, stock_minimo, estado "
                + "FROM v_stock_critico";

        List<String[]> resultados = new ArrayList<>();
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultados.add(new String[]{
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getString("stock_actual"),
                    rs.getString("stock_minimo"),
                    rs.getString("estado")
                });
            }
        }
        return resultados;
    }

    public String[] encabezadosAlertas() {
        return new String[]{"Codigo", "Nombre", "Categoria", "Stock Actual", "Stock Minimo", "Estado"};
    }

    // ============================================================
    // RF29. Exportar reporte a archivo de texto (.txt)
    // ============================================================
    public void exportarReporte(List<String[]> datos, String[] encabezados, String rutaArchivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write(String.join(" | ", encabezados));
            bw.newLine();
            bw.write("-".repeat(90));
            bw.newLine();
            for (String[] fila : datos) {
                bw.write(String.join(" | ", fila));
                bw.newLine();
            }
        }
    }

    // ============================================================
    // RF30. Mostrar resumen de inventario
    // ============================================================
    public Map<String, String> resumenInventario() throws SQLException {
        String sql = "SELECT "
                + "  COUNT(*) AS total_productos, "
                + "  SUM(precio * stock_actual) AS valor_total_inventario, "
                + "  SUM(CASE WHEN stock_actual = 0 THEN 1 ELSE 0 END) AS productos_agotados, "
                + "  SUM(CASE WHEN stock_actual <= stock_minimo AND stock_actual > 0 "
                + "           THEN 1 ELSE 0 END) AS productos_criticos "
                + "FROM productos WHERE activo = 1";

        Map<String, String> resumen = new LinkedHashMap<>();
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                resumen.put("Total de productos", rs.getString("total_productos"));
                resumen.put("Valor total del inventario", "S/ " + rs.getString("valor_total_inventario"));
                resumen.put("Productos agotados", rs.getString("productos_agotados"));
                resumen.put("Productos criticos", rs.getString("productos_criticos"));
            }
        }
        return resumen;
    }
}
