package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    Scanner sc = new Scanner(System.in);
    ProductoDAO dao = new ProductoDAO();
    RespaldoDAO respaldoDao = new RespaldoDAO(); // RF39
    ExportadorDAO exportadorDao = new ExportadorDAO(); // RF34
    ReporteDAO reporteDao = new ReporteDAO(); // Bloque 4 - RF25 a RF30

    public void mostrar() {

        int opcion;

        do {

            System.out.println("\n========= SISTEMA DE INVENTARIO Santa Cruz SRL =========");
            System.out.println("1. Registrar producto");
            System.out.println("2. Modificar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Buscar por codigo");
            System.out.println("5. Buscar por nombre");
            System.out.println("6. Mostrar todos");
            System.out.println("7. Consultar stock");
            System.out.println("8. Actualizar stock");
            // RF 32 - Mostrar menú principal (nuevas opciones del módulo)
            System.out.println("9. Generar respaldo de datos");
            System.out.println("10. Exportar datos a archivo");
            // Bloque 4 - Reportes y consultas
            System.out.println("11. Movimientos por producto (RF25)");
            System.out.println("12. Movimientos por categoria (RF26)");
            System.out.println("13. Inventario general (RF27)");
            System.out.println("14. Alertas activas (RF28)");
            System.out.println("15. Exportar reporte de inventario (RF29)");
            System.out.println("16. Resumen de inventario (RF30)");
            System.out.println("17. Salir");

            opcion = leerOpcion();

            // RF 33 - Validar opciones del menú
            if (!MenuValidator.validarOpcion(opcion, 1, 17)) {
                continue;
            }

            switch (opcion) {

                case 1:
                    registrarProducto();
                    break;

                case 2:
                    modificarProducto();
                    break;

                case 3:
                    eliminarProducto();
                    break;

                case 4:
                    buscarCodigo();
                    break;

                case 5:
                    buscarNombre();
                    break;

                case 6:
                    listarProductos();
                    break;

                case 7:
                    consultarStock();
                    break;

                case 8:
                    actualizarStock();
                    break;

                // RF 39 - Generar respaldo de datos
                case 9:
                    generarRespaldo();
                    break;

                // RF 34 - Guardar información en archivos
                case 10:
                    exportarDatos();
                    break;

                // RF25 - Movimientos por producto
                case 11:
                    movimientosPorProducto();
                    break;

                // RF26 - Movimientos por categoria
                case 12:
                    movimientosPorCategoria();
                    break;

                // RF27 - Inventario general
                case 13:
                    inventarioGeneral();
                    break;

                // RF28 - Alertas activas
                case 14:
                    alertasActivas();
                    break;

                // RF29 - Exportar reporte de inventario
                case 15:
                    exportarReporteInventario();
                    break;

                // RF30 - Resumen de inventario
                case 16:
                    resumenInventario();
                    break;

                // RF 40 - Salir del sistema
                case 17:
                    salirSistema();
                    break;
            }

        } while (opcion != 17);
    }

    private int leerOpcion() {

        while (true) {

            System.out.print("Opcion: ");

            try {
                String input = sc.nextLine().trim();

                int valor = Integer.parseInt(input);

                return valor;

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
            }
        }
    }

    // RF01
    private void registrarProducto() {

        Producto p = new Producto();

        System.out.print("Codigo: ");
        p.setCodigo(sc.nextLine());

        System.out.print("Nombre: ");
        p.setNombre(sc.nextLine());

        System.out.print("Descripcion: ");
        p.setDescripcion(sc.nextLine());

        System.out.print("Id Categoria: ");
        p.setIdCategoria(sc.nextInt());

        System.out.print("Precio: ");
        p.setPrecio(sc.nextDouble());

        sc.nextLine();

        System.out.print("Unidad de medida: ");
        p.setUnidadMedida(sc.nextLine());

        System.out.print("Stock actual: ");
        p.setStockActual(sc.nextInt());

        System.out.print("Stock minimo: ");
        p.setStockMinimo(sc.nextInt());

        sc.nextLine();

        p.setActivo(true);

        if (dao.registrar(p))
            System.out.println("Producto registrado.");
        else
            System.out.println("Error al registrar.");
    }

    // RF02
    private void modificarProducto() {

        Producto p = new Producto();

        System.out.print("ID del producto: ");
        p.setIdProducto(sc.nextInt());
        sc.nextLine();

        System.out.print("Codigo: ");
        p.setCodigo(sc.nextLine());

        System.out.print("Nombre: ");
        p.setNombre(sc.nextLine());

        System.out.print("Descripcion: ");
        p.setDescripcion(sc.nextLine());

        System.out.print("Id Categoria: ");
        p.setIdCategoria(sc.nextInt());

        System.out.print("Precio: ");
        p.setPrecio(sc.nextDouble());

        sc.nextLine();

        System.out.print("Unidad de medida: ");
        p.setUnidadMedida(sc.nextLine());

        System.out.print("Stock actual: ");
        p.setStockActual(sc.nextInt());

        System.out.print("Stock minimo: ");
        p.setStockMinimo(sc.nextInt());

        sc.nextLine();

        p.setActivo(true);

        if (dao.modificar(p))
            System.out.println("Producto modificado.");
        else
            System.out.println("No se pudo modificar.");
    }

    // RF03
    private void eliminarProducto() {

        System.out.print("ID del producto: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (dao.eliminar(id))
            System.out.println("Producto eliminado.");
        else
            System.out.println("No existe.");
    }

    // RF04
    private void buscarCodigo() {

        System.out.print("Codigo: ");
        String codigo = sc.nextLine();

        Producto p = dao.buscarPorCodigo(codigo);

        if (p != null) {
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Precio: " + p.getPrecio());
            System.out.println("Stock: " + p.getStockActual());
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    // RF05
    private void buscarNombre() {

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        List<Producto> lista = dao.buscarPorNombre(nombre);

        for (Producto p : lista) {
            System.out.println(p.getCodigo() + " - " + p.getNombre() + " - S/. " + p.getPrecio());
        }
    }

    // RF06
    private void listarProductos() {

        List<Producto> lista = dao.listarProductos();

        for (Producto p : lista) {

            System.out.println("--------------------------------");

            System.out.println("ID: " + p.getIdProducto());
            System.out.println("Código: " + p.getCodigo());
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Precio: " + p.getPrecio());
            System.out.println("Stock: " + p.getStockActual());
        }
    }

    // RF07
    private void consultarStock() {

        System.out.print("Codigo: ");
        String codigo = sc.nextLine();

        int stock = dao.consultarStock(codigo);

        System.out.println("Stock disponible: " + stock);
    }

    // RF08
    private void actualizarStock() {

        System.out.print("Codigo: ");
        String codigo = sc.nextLine();

        System.out.print("Nuevo stock: ");
        int stock = sc.nextInt();
        sc.nextLine();

        if (dao.actualizarStock(codigo, stock))
            System.out.println("Stock actualizado.");
        else
            System.out.println("Error al actualizar.");
    }

    // RF 39 - Generar respaldo de datos
    private void generarRespaldo() {

        System.out.print("¿Desea generar un respaldo de la información? (S/N): ");
        String respuesta = sc.nextLine();

        // RF 38 - Validar datos ingresados
        if (!Validador.validarConfirmacion(respuesta)) {
            MensajeUtil.mostrarError("Respuesta inválida. Ingrese S o N.");
            return;
        }

        if (respuesta.trim().equalsIgnoreCase("N")) {
            System.out.println("Respaldo cancelado.");
            return;
        }

        if (respaldoDao.generarRespaldo())
            // RF 36 - Confirmar operaciones
            MensajeUtil.confirmarOperacion("Respaldo generado correctamente.");
        else
            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("No se pudo generar el respaldo.");
    }

    // RF 34 - Guardar información en archivos
    private void exportarDatos() {

        String ruta = exportadorDao.exportarProductosCSV();

        if (ruta != null)
            // RF 36 - Confirmar operaciones
            MensajeUtil.confirmarOperacion("Datos exportados correctamente en: " + ruta);
        else
            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("No se pudo exportar los datos.");
    }

    // ===========================
    // Bloque 4 - Reportes y consultas
    // ===========================

    // RF25 - Mostrar movimientos por producto
    private void movimientosPorProducto() {

        System.out.print("Codigo del producto: ");
        String codigo = sc.nextLine();

        reporteDao.mostrarMovimientosPorProducto(codigo);
    }

    // RF26 - Mostrar movimientos por categoria
    private void movimientosPorCategoria() {

        System.out.print("Nombre de la categoria: ");
        String categoria = sc.nextLine();

        reporteDao.mostrarMovimientosPorCategoria(categoria);
    }

    // RF27 - Mostrar inventario general
    private void inventarioGeneral() {

        reporteDao.mostrarInventarioGeneral();
    }

    // RF28 - Mostrar alertas activas
    private void alertasActivas() {

        reporteDao.mostrarAlertasActivas();
    }

    // RF29 - Exportar reporte de inventario a archivo
    private void exportarReporteInventario() {

        String ruta = exportadorDao.exportarInventarioCSV();

        if (ruta != null)
            // RF 36 - Confirmar operaciones
            MensajeUtil.confirmarOperacion("Reporte de inventario exportado en: " + ruta);
        else
            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("No se pudo exportar el reporte de inventario.");
    }

    // RF30 - Mostrar resumen de inventario
    private void resumenInventario() {

        reporteDao.mostrarResumenInventario();
    }

    // RF 40 - Salir del sistema
    private void salirSistema() {

        System.out.println("\nCerrando el sistema de manera segura...");

        try {

            if (dao.cn != null && !dao.cn.isClosed()) {
                dao.cn.close();
            }

            if (respaldoDao.cn != null && !respaldoDao.cn.isClosed()) {
                respaldoDao.cn.close();
            }

        } catch (SQLException e) {

            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("Error al cerrar la conexión: " + e.getMessage());
        }

        sc.close();

        // RF 36 - Confirmar operaciones
        MensajeUtil.confirmarOperacion("Sesión finalizada. ¡Hasta pronto!");
    }
}
