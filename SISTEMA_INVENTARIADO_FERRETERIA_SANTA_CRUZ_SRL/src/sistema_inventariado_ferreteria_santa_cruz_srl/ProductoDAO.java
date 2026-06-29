package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    Connection cn = Conexion.conectar();

    // ===========================
    // RF01 - Registrar producto
    // ===========================
    public boolean registrar(Producto p) {

        String sql = "INSERT INTO productos(codigo,nombre,descripcion,id_categoria,precio,unidad_medida,stock_actual,stock_minimo,activo) VALUES(?,?,?,?,?,?,?,?,?)";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getIdCategoria());
            ps.setDouble(5, p.getPrecio());
            ps.setString(6, p.getUnidadMedida());
            ps.setInt(7, p.getStockActual());
            ps.setInt(8, p.getStockMinimo());
            ps.setBoolean(9, p.isActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return false;
    }

    // ===========================
    // RF02 - Modificar producto
    // ===========================
    public boolean modificar(Producto p) {

        String sql = "UPDATE productos SET codigo=?,nombre=?,descripcion=?,id_categoria=?,precio=?,unidad_medida=?,stock_actual=?,stock_minimo=?,activo=? WHERE id_producto=?";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getIdCategoria());
            ps.setDouble(5, p.getPrecio());
            ps.setString(6, p.getUnidadMedida());
            ps.setInt(7, p.getStockActual());
            ps.setInt(8, p.getStockMinimo());
            ps.setBoolean(9, p.isActivo());
            ps.setInt(10, p.getIdProducto());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return false;
    }

    // ===========================
    // RF03 - Eliminar producto
    // ===========================
    public boolean eliminar(int idProducto) {

        String sql = "DELETE FROM productos WHERE id_producto=?";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idProducto);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return false;
    }

    // ===========================
    // RF04 - Buscar por código
    // ===========================
    public Producto buscarPorCodigo(String codigo) {

        String sql = "SELECT * FROM productos WHERE codigo=?";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Producto p = new Producto();

                p.setIdProducto(rs.getInt("id_producto"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setUnidadMedida(rs.getString("unidad_medida"));
                p.setStockActual(rs.getInt("stock_actual"));
                p.setStockMinimo(rs.getInt("stock_minimo"));
                p.setActivo(rs.getBoolean("activo"));

                return p;
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return null;
    }

    // ===========================
    // RF05 - Buscar por nombre
    // ===========================
    public List<Producto> buscarPorNombre(String nombre) {

        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM productos WHERE nombre LIKE ?";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Producto p = new Producto();

                p.setIdProducto(rs.getInt("id_producto"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setUnidadMedida(rs.getString("unidad_medida"));
                p.setStockActual(rs.getInt("stock_actual"));
                p.setStockMinimo(rs.getInt("stock_minimo"));
                p.setActivo(rs.getBoolean("activo"));

                lista.add(p);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return lista;
    }

    // ===========================
    // RF06 - Mostrar todos
    // ===========================
    public List<Producto> listarProductos() {

        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM productos";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Producto p = new Producto();

                p.setIdProducto(rs.getInt("id_producto"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setUnidadMedida(rs.getString("unidad_medida"));
                p.setStockActual(rs.getInt("stock_actual"));
                p.setStockMinimo(rs.getInt("stock_minimo"));
                p.setActivo(rs.getBoolean("activo"));

                lista.add(p);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return lista;
    }

    // ===========================
    // RF07 - Consultar stock
    // ===========================
    public int consultarStock(String codigo) {

        String sql = "SELECT stock_actual FROM productos WHERE codigo=?";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt("stock_actual");
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return -1;
    }

    // ===========================
    // RF08 - Actualizar stock
    // ===========================
    public boolean actualizarStock(String codigo, int nuevoStock) {

        String sql = "UPDATE productos SET stock_actual=? WHERE codigo=?";

        try {

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, nuevoStock);
            ps.setString(2, codigo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return false;
    }

}