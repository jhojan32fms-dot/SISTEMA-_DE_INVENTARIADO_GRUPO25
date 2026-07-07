
package sistema_inventariado_ferreteria_santa_cruz_srl;
import java.sql.Date;
/**
 * @author JUANPC
 */
public class Movimiento {
    // Atributos privados (Encapsulamiento para proteger los datos)
    private int idMovimiento;
    private String codigoProducto;
    private String tipoMovimiento; // Guardará "ENTRADA" o "SALIDA"
    private int cantidad;
    private Date fecha;

    // Constructor vacío (Obligatorio en buenas prácticas)
    public Movimiento() {}

    // Constructor con parámetros (Sirve para llenar el objeto rápidamente)
    public Movimiento(int idMovimiento, String codigoProducto, String tipoMovimiento, int cantidad, Date fecha) {
        this.idMovimiento = idMovimiento;
        this.codigoProducto = codigoProducto;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

// Métodos Getters y Setters (Para leer y escribir de forma segura en los atributos)
    public int getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(int idMovimiento) { this.idMovimiento = idMovimiento; }

    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }

    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}