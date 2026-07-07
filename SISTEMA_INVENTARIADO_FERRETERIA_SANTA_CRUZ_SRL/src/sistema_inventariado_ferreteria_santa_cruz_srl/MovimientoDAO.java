package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO {

    Connection cn = Conexion.conectar();
    ProductoDAO productoDAO = new ProductoDAO();

    // ===========================
    // RF13 - Validar stock disponible
    // ===========================
    public boolean validarStockDisponible(String codigo, int cantidadSolicitada) {

        int stockActual = productoDAO.consultarStock(codigo);

        return stockActual >= cantidadSolicitada;
    }

    // ===========================
    // RF11 - Registrar entrada de stock
    // RF14 - Registrar motivo del movimiento
    // RF15 - Registrar fecha del movimiento
    // ===========================
    public boolean registrarEntrada(String codigo, int cantidad, String motivo, int idUsuario) {

        Producto p = productoDAO.buscarPorCodigo(codigo);

        if (p == null) {
            return false;
        }

        int stockAntes = p.getStockActual();
        int stockDespues = stockAntes + cantidad;

        if (!productoDAO.actualizarStock(codigo, stockDespues)) {
            return false;
        }

        String sql = "INSERT INTO movimientos(id_producto,tipo,cantidad,motivo,fecha,id_usuario,stock_antes,stock_despues) "
                + "VALUES(?,?,?,?,NOW(),?,?,?)";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, p.getIdProducto());
            ps.setString(2, "entrada");
            ps.setInt(3, cantidad);
            ps.setString(4, motivo);
            ps.setInt(5, idUsuario);
            ps.setInt(6, stockAntes);
            ps.setInt(7, stockDespues);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return false;
    }

    // ===========================
    // RF12 - Registrar salida de stock
    // RF13 - Validar stock disponible
    // RF14 - Registrar motivo del movimiento
    // RF15 - Registrar fecha del movimiento
    // ===========================
    public boolean registrarSalida(String codigo, int cantidad, String motivo, int idUsuario) {

        if (!validarStockDisponible(codigo, cantidad)) {
            return false;
        }

        Producto p = productoDAO.buscarPorCodigo(codigo);

        if (p == null) {
            return false;
        }

        int stockAntes = p.getStockActual();
        int stockDespues = stockAntes - cantidad;

        if (!productoDAO.actualizarStock(codigo, stockDespues)) {
            return false;
        }

        String sql = "INSERT INTO movimientos(id_producto,tipo,cantidad,motivo,fecha,id_usuario,stock_antes,stock_despues) "
                + "VALUES(?,?,?,?,NOW(),?,?,?)";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, p.getIdProducto());
            ps.setString(2, "salida");
            ps.setInt(3, cantidad);
            ps.setString(4, motivo);
            ps.setInt(5, idUsuario);
            ps.setInt(6, stockAntes);
            ps.setInt(7, stockDespues);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return false;
    }

    // ===========================
    // RF16 - Consultar historial de movimientos
    // ===========================
    public List<Movimiento> consultarHistorial(String codigo) {

        List<Movimiento> lista = new ArrayList<>();

        Producto p = productoDAO.buscarPorCodigo(codigo);

        if (p == null) {
            return lista;
        }

        String sql = "SELECT * FROM movimientos WHERE id_producto=? ORDER BY fecha DESC";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, p.getIdProducto());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Movimiento m = new Movimiento();

                m.setIdMovimiento(rs.getInt("id_movimiento"));
                m.setIdProducto(rs.getInt("id_producto"));
                m.setTipo(rs.getString("tipo"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setMotivo(rs.getString("motivo"));
                m.setFecha(rs.getTimestamp("fecha"));
                m.setIdUsuario(rs.getInt("id_usuario"));
                m.setStockAntes(rs.getInt("stock_antes"));
                m.setStockDespues(rs.getInt("stock_despues"));

                lista.add(m);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return lista;
    }

}
