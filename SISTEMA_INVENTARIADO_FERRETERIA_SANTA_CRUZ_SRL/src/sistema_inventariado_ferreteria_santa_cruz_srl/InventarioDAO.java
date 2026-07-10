package sistema_inventariado_ferreteria_santa_cruz_srl;
// Importamos la conexión y el modelo con el nuevo nombre de paquete 
import sistema_inventariado_ferreteria_santa_cruz_srl.Conexion;
import sistema_inventariado_ferreteria_santa_cruz_srl.Movimiento;
import java.sql.*;
import java.util.ArrayList;
/**
  * @author HPTEC
 */
public class InventarioDAO {

    // RF19: Registrar o actualizar el stock mínimo de un producto ferretero
    public boolean registrarStockMinimo(String codigo, int stockMinimo) {
        String sql = "UPDATE productos SET stock_minimo = ? WHERE codigo = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, stockMinimo);
            ps.setString(2, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en RF19: " + e.getMessage());
            return false;
        }
    }

    // RF17: Buscar movimientos filtrados por una fecha específica 
    // (corregido: la tabla movimientos no tiene codigo_producto/tipo_movimiento,
    // se hace JOIN con productos usando id_producto y se compara solo la parte de fecha del timestamp)
    public ArrayList<Movimiento> buscarMovimientosPorFecha(String fechaStr) {
        ArrayList<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT m.id_movimiento, p.codigo AS codigo_producto, m.tipo AS tipo_movimiento, "
                + "m.cantidad, m.fecha "
                + "FROM movimientos m JOIN productos p ON m.id_producto = p.id_producto "
                + "WHERE DATE(m.fecha) = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fechaStr));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Movimiento(
                    rs.getInt("id_movimiento"), 
                    rs.getString("codigo_producto"),
                    rs.getString("tipo_movimiento"), 
                    rs.getInt("cantidad"), 
                    rs.getTimestamp("fecha")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error en RF17: " + e.getMessage());
        }
        return lista;
    }
// RMostrar los últimos movimientos registrados 
    // (corregido: JOIN con productos para obtener el codigo, ya que movimientos guarda id_producto)
    public ArrayList<Movimiento> mostrarMovimientosRecientes() {
        ArrayList<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT m.id_movimiento, p.codigo AS codigo_producto, m.tipo AS tipo_movimiento, "
                + "m.cantidad, m.fecha "
                + "FROM movimientos m JOIN productos p ON m.id_producto = p.id_producto "
                + "ORDER BY m.id_movimiento DESC LIMIT 10";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Movimiento(
                    rs.getInt("id_movimiento"), 
                    rs.getString("codigo_producto"),
                    rs.getString("tipo_movimiento"), 
                    rs.getInt("cantidad"), 
                    rs.getTimestamp("fecha")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error en RF18: " + e.getMessage());
        }
        return lista;
    }
// Buscar y mostrar los productos cuyo stock actual sea menor o igual al stock mínimo 
    // (corregido: la columna real es stock_actual, no stock)
    public void mostrarProductosBajoStock() {
        String sql = "SELECT codigo, descripcion, stock_actual, stock_minimo FROM productos WHERE stock_actual <= stock_minimo AND stock_actual > 0";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n⚠️ --- ALERTA: PRODUCTOS EN STOCK CRÍTICO (RF20 / RF21) ---");
            while (rs.next()) {
                System.out.println(" Cód: " + rs.getString("codigo") + " | " + rs.getString("descripcion") 
                        + " | Stock Actual: " + rs.getInt("stock_actual") + " (Mínimo: " + rs.getInt("stock_minimo") + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error en RF21: " + e.getMessage());
        }
    }
// RF22: Mostrar de forma exclusiva los productos agotados (Stock exactamente igual a 0)
    // (corregido: la columna real es stock_actual, no stock)
    public void mostrarProductosAgotados() {
        String sql = "SELECT codigo, descripcion FROM productos WHERE stock_actual = 0";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n🚫 --- REPORTE: PRODUCTOS TOTALMENTE AGOTADOS (RF22) ---");
            while (rs.next()) {
                System.out.println(" COMPLETAMENTE VACÍO -> Cód: " + rs.getString("codigo") + " | " + rs.getString("descripcion"));
            }
        } catch (SQLException e) {
            System.out.println("Error en RF22: " + e.getMessage());
        }
    }
    // RF23 y RF24: Mostrar movimientos filtrados por tipo ("ENTRADA" o "SALIDA")
    // (corregido: JOIN con productos y comparación insensible a mayúsculas, ya que
    // MovimientoDAO guarda el tipo en minúsculas: "entrada" / "salida")
    public ArrayList<Movimiento> mostrarMovimientosPorTipo(String tipo) {
        ArrayList<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT m.id_movimiento, p.codigo AS codigo_producto, m.tipo AS tipo_movimiento, "
                + "m.cantidad, m.fecha "
                + "FROM movimientos m JOIN productos p ON m.id_producto = p.id_producto "
                + "WHERE UPPER(m.tipo) = UPPER(?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Movimiento(
                    rs.getInt("id_movimiento"), 
                    rs.getString("codigo_producto"),
                    rs.getString("tipo_movimiento"), 
                    rs.getInt("cantidad"), 
                    rs.getTimestamp("fecha")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar movimientos por tipo: " + e.getMessage());
        }
        return lista;
    }
}