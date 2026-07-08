package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExportadorDAO {

    ProductoDAO productoDAO = new ProductoDAO();

    // RF 34 - Guardar información en archivos
    public String exportarProductosCSV() {

        List<Producto> lista = productoDAO.listarProductos();

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String rutaCarpeta = "exportaciones";
        String rutaArchivo = rutaCarpeta + File.separator + "productos_" + timestamp + ".csv";

        File carpeta = new File(rutaCarpeta);

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        try (FileWriter fw = new FileWriter(rutaArchivo)) {

            fw.write("id_producto,codigo,nombre,descripcion,id_categoria,precio,unidad_medida,stock_actual,stock_minimo,activo\n");

            for (Producto p : lista) {

                fw.write(p.getIdProducto() + ","
                        + p.getCodigo() + ","
                        + p.getNombre() + ","
                        + p.getDescripcion() + ","
                        + p.getIdCategoria() + ","
                        + p.getPrecio() + ","
                        + p.getUnidadMedida() + ","
                        + p.getStockActual() + ","
                        + p.getStockMinimo() + ","
                        + p.isActivo() + "\n");
            }

            return rutaArchivo;

        } catch (IOException e) {

            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("No se pudo exportar los datos: " + e.getMessage());
        }

        return null;
    }

    // ===========================
    // RF29 - Exportar reporte de inventario a archivo
    // ===========================
    public String exportarInventarioCSV() {

        String sql = "SELECT * FROM v_inventario_general";

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String rutaCarpeta = "exportaciones";
        String rutaArchivo = rutaCarpeta + File.separator + "reporte_inventario_" + timestamp + ".csv";

        File carpeta = new File(rutaCarpeta);

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        try {

            Connection cn = Conexion.conectar();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            FileWriter fw = new FileWriter(rutaArchivo);

            fw.write("codigo,nombre,categoria,unidad_medida,precio,stock_actual,stock_minimo,valor_total\n");

            while (rs.next()) {

                fw.write(rs.getString("codigo") + ","
                        + rs.getString("nombre") + ","
                        + rs.getString("categoria") + ","
                        + rs.getString("unidad_medida") + ","
                        + rs.getDouble("precio") + ","
                        + rs.getInt("stock_actual") + ","
                        + rs.getInt("stock_minimo") + ","
                        + rs.getDouble("valor_total") + "\n");
            }

            fw.close();

            return rutaArchivo;

        } catch (SQLException | IOException e) {

            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("No se pudo exportar el reporte de inventario: " + e.getMessage());
        }

        return null;
    }

}
