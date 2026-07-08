package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.sql.Timestamp;
import java.util.Date;

public class Movimiento {

    private int idMovimiento;
    private int idProducto;
    private String codigoProducto;
    private String tipo;
    private String tipoMovimiento;
    private int cantidad;
    private String motivo;
    private Timestamp fecha;
    private int idUsuario;
    private int stockAntes;
    private int stockDespues;

    public Movimiento() {
    }

    public Movimiento(int idMovimiento, String codigoProducto, String tipoMovimiento, int cantidad, Date fecha) {
        this.idMovimiento = idMovimiento;
        this.codigoProducto = codigoProducto;
        this.tipoMovimiento = tipoMovimiento;
        this.tipo = tipoMovimiento;
        this.cantidad = cantidad;

        if (fecha != null) {
            this.fecha = new Timestamp(fecha.getTime());
        }
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        this.tipoMovimiento = tipo;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
        this.tipo = tipoMovimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public void setFecha(Date fecha) {
        if (fecha != null) {
            this.fecha = new Timestamp(fecha.getTime());
        }
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getStockAntes() {
        return stockAntes;
    }

    public void setStockAntes(int stockAntes) {
        this.stockAntes = stockAntes;
    }

    public int getStockDespues() {
        return stockDespues;
    }

    public void setStockDespues(int stockDespues) {
        this.stockDespues = stockDespues;
    }

}