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
    MovimientoDAO movimientoDao = new MovimientoDAO(); // RF11-RF16
    InventarioDAO inventarioDao = new InventarioDAO(); // Bloque 3 - RF17 a RF24
    Usuario usuarioActual;

    public Menu(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

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
            // RF09-RF16 - Gestión de precios y movimientos
            System.out.println("9. Registrar precio");
            System.out.println("10. Modificar precio");
            System.out.println("11. Registrar entrada de stock");
            System.out.println("12. Registrar salida de stock");
            System.out.println("13. Consultar historial de movimientos");
            // RF 32 - Mostrar menú principal (nuevas opciones del módulo)
            System.out.println("14. Generar respaldo de datos");
            System.out.println("15. Exportar datos a archivo");
            // Bloque 4 - Reportes y consultas
            System.out.println("16. Movimientos por producto (RF25)");
            System.out.println("17. Movimientos por categoria (RF26)");
            System.out.println("18. Inventario general (RF27)");
            System.out.println("19. Alertas activas (RF28)");
            System.out.println("20. Exportar reporte de inventario (RF29)");
            System.out.println("21. Resumen de inventario (RF30)");
            // Bloque 3 - Alertas y consultas de movimiento
            System.out.println("22. Buscar movimientos por fecha (RF17)");
            System.out.println("23. Mostrar movimientos recientes (RF18)");
            System.out.println("24. Configurar stock minimo de alerta (RF19)");
            System.out.println("25. Consultar alertas de bajo stock (RF20/RF21)");
            System.out.println("26. Listar productos agotados (RF22)");
            System.out.println("27. Mostrar entradas de almacen (RF23)");
            System.out.println("28. Mostrar salidas de almacen (RF24)");
            System.out.println("29. Salir");

            opcion = leerOpcion();

            // RF 33 - Validar opciones del menú
            if (!MenuValidator.validarOpcion(opcion, 1, 29)) {
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

                // RF09 - Registrar precio
                case 9:
                    registrarPrecioProducto();
                    break;

                // RF10 - Modificar precio
                case 10:
                    modificarPrecioProducto();
                    break;

                // RF11 - Registrar entrada de stock
                case 11:
                    registrarEntradaStock();
                    break;

                // RF12 - Registrar salida de stock
                case 12:
                    registrarSalidaStock();
                    break;

                // RF16 - Consultar historial de movimientos
                case 13:
                    consultarHistorialMovimientos();
                    break;

                // RF 39 - Generar respaldo de datos
                case 14:
                    generarRespaldo();
                    break;

                // RF 34 - Guardar información en archivos
                case 15:
                    exportarDatos();
                    break;

                // RF25 - Movimientos por producto
                case 16:
                    movimientosPorProducto();
                    break;

                // RF26 - Movimientos por categoria
                case 17:
                    movimientosPorCategoria();
                    break;

                // RF27 - Inventario general
                case 18:
                    inventarioGeneral();
                    break;

                // RF28 - Alertas activas
                case 19:
                    alertasActivas();
                    break;

                // RF29 - Exportar reporte de inventario
                case 20:
                    exportarReporteInventario();
                    break;

                // RF30 - Resumen de inventario
                case 21:
                    resumenInventario();
                    break;

                // RF17 - Buscar movimientos por fecha
                case 22:
                    buscarMovimientosPorFecha();
                    break;

                // RF18 - Mostrar movimientos recientes
                case 23:
                    mostrarMovimientosRecientes();
                    break;

                // RF19 - Configurar stock minimo de alerta
                case 24:
                    configurarStockMinimo();
                    break;

                // RF20/RF21 - Consultar alertas de bajo stock
                case 25:
                    alertasBajoStock();
                    break;

                // RF22 - Listar productos agotados
                case 26:
                    productosAgotados();
                    break;

                // RF23 - Mostrar entradas de almacen
                case 27:
                    entradasAlmacen();
                    break;

                // RF24 - Mostrar salidas de almacen
                case 28:
                    salidasAlmacen();
                    break;

                // RF 40 - Salir del sistema
                case 29:
                    salirSistema();
                    break;
            }

        } while (opcion != 29);
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

    // RF09 - Registrar precio
    private void registrarPrecioProducto() {

        System.out.print("Codigo: ");
        String codigo = sc.nextLine();

        System.out.print("Precio a registrar: ");
        double precio = sc.nextDouble();
        sc.nextLine();

        // RF 38 - Validar datos ingresados
        if (!Validador.validarNumeroPositivo(precio)) {
            MensajeUtil.mostrarError("El precio debe ser mayor a 0.");
            return;
        }

        if (dao.registrarPrecio(codigo, precio))
            // RF 36 - Confirmar operaciones
            MensajeUtil.confirmarOperacion("Precio registrado correctamente.");
        else
            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("No se pudo registrar el precio (verifique el código o si ya tiene un precio asignado).");
    }

    // RF10 - Modificar precio
    private void modificarPrecioProducto() {

        System.out.print("Codigo: ");
        String codigo = sc.nextLine();

        System.out.print("Nuevo precio: ");
        double precio = sc.nextDouble();
        sc.nextLine();

        // RF 38 - Validar datos ingresados
        if (!Validador.validarNumeroPositivo(precio)) {
            MensajeUtil.mostrarError("El precio debe ser mayor a 0.");
            return;
        }

        if (dao.modificarPrecio(codigo, precio))
            // RF 36 - Confirmar operaciones
            MensajeUtil.confirmarOperacion("Precio modificado correctamente.");
        else
            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("No se pudo modificar el precio (verifique el código o si aún no tiene un precio registrado).");
    }

    // RF11 - Registrar entrada de stock
    private void registrarEntradaStock() {

        System.out.print("Codigo: ");
        String codigo = sc.nextLine();

        System.out.print("Cantidad a ingresar: ");
        int cantidad = sc.nextInt();
        sc.nextLine();

        // RF14 - Registrar motivo del movimiento
        System.out.print("Motivo del ingreso: ");
        String motivo = sc.nextLine();

        // RF 38 - Validar datos ingresados
        if (!Validador.validarTexto(motivo)) {
            MensajeUtil.mostrarError("Debe ingresar un motivo válido.");
            return;
        }

        // RF15 - Registrar fecha del movimiento (se registra automáticamente)
        if (movimientoDao.registrarEntrada(codigo, cantidad, motivo, usuarioActual.getIdUsuario()))
            // RF 36 - Confirmar operaciones
            MensajeUtil.confirmarOperacion("Entrada de stock registrada correctamente.");
        else
            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("No se pudo registrar la entrada de stock.");
    }

    // RF12 - Registrar salida de stock
    private void registrarSalidaStock() {

        System.out.print("Codigo: ");
        String codigo = sc.nextLine();

        System.out.print("Cantidad a retirar: ");
        int cantidad = sc.nextInt();
        sc.nextLine();

        // RF13 - Validar stock disponible
        if (!movimientoDao.validarStockDisponible(codigo, cantidad)) {
            MensajeUtil.mostrarError("Stock insuficiente para realizar la salida.");
            return;
        }

        // RF14 - Registrar motivo del movimiento
        System.out.print("Motivo de la salida: ");
        String motivo = sc.nextLine();

        // RF 38 - Validar datos ingresados
        if (!Validador.validarTexto(motivo)) {
            MensajeUtil.mostrarError("Debe ingresar un motivo válido.");
            return;
        }

        // RF15 - Registrar fecha del movimiento (se registra automáticamente)
        if (movimientoDao.registrarSalida(codigo, cantidad, motivo, usuarioActual.getIdUsuario()))
            // RF 36 - Confirmar operaciones
            MensajeUtil.confirmarOperacion("Salida de stock registrada correctamente.");
        else
            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("No se pudo registrar la salida de stock.");
    }

    // RF16 - Consultar historial de movimientos
    private void consultarHistorialMovimientos() {

        System.out.print("Codigo: ");
        String codigo = sc.nextLine();

        List<Movimiento> lista = movimientoDao.consultarHistorial(codigo);

        if (lista.isEmpty()) {
            System.out.println("No existen movimientos registrados para este producto.");
            return;
        }

        for (Movimiento m : lista) {

            System.out.println("--------------------------------");
            System.out.println("Tipo: " + m.getTipo());
            System.out.println("Cantidad: " + m.getCantidad());
            System.out.println("Motivo: " + m.getMotivo());
            System.out.println("Fecha: " + m.getFecha());
            System.out.println("Stock antes: " + m.getStockAntes());
            System.out.println("Stock despues: " + m.getStockDespues());
        }
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

    // ===========================
    // Bloque 3 - Alertas y consultas de movimiento
    // ===========================

    // RF17 - Buscar movimientos por fecha
    private void buscarMovimientosPorFecha() {

        System.out.print("Ingrese fecha de busqueda (AAAA-MM-DD): ");
        String fecha = sc.nextLine();

        List<Movimiento> lista = inventarioDao.buscarMovimientosPorFecha(fecha);
        imprimirMovimientos(lista);
    }

    // RF18 - Mostrar movimientos recientes
    private void mostrarMovimientosRecientes() {

        List<Movimiento> lista = inventarioDao.mostrarMovimientosRecientes();
        imprimirMovimientos(lista);
    }

    // RF19 - Configurar stock minimo de alerta para producto
    private void configurarStockMinimo() {

        System.out.print("Codigo del producto: ");
        String codigo = sc.nextLine();

        System.out.print("Stock minimo de seguridad: ");
        int cantMin = sc.nextInt();
        sc.nextLine();

        if (inventarioDao.registrarStockMinimo(codigo, cantMin))
            // RF 36 - Confirmar operaciones
            MensajeUtil.confirmarOperacion("Stock minimo actualizado correctamente.");
        else
            // RF 37 - Mostrar mensajes de error
            MensajeUtil.mostrarError("No se pudo actualizar el stock minimo (verifique el codigo).");
    }

    // RF20/RF21 - Consultar alertas de bajo stock / stock critico
    private void alertasBajoStock() {

        inventarioDao.mostrarProductosBajoStock();
    }

    // RF22 - Listar productos agotados
    private void productosAgotados() {

        inventarioDao.mostrarProductosAgotados();
    }

    // RF23 - Mostrar todas las entradas de almacen
    private void entradasAlmacen() {

        List<Movimiento> lista = inventarioDao.mostrarMovimientosPorTipo("ENTRADA");
        imprimirMovimientos(lista);
    }

    // RF24 - Mostrar todas las salidas de almacen
    private void salidasAlmacen() {

        List<Movimiento> lista = inventarioDao.mostrarMovimientosPorTipo("SALIDA");
        imprimirMovimientos(lista);
    }

    // Metodo auxiliar para imprimir listas de movimientos sin duplicar codigo (Bloque 3)
    private void imprimirMovimientos(List<Movimiento> lista) {

        if (lista.isEmpty()) {
            System.out.println("No hay registros de movimientos en esta consulta.");
            return;
        }

        for (Movimiento m : lista) {
            System.out.println("ID Movimiento: " + m.getIdMovimiento()
                    + " | Articulo: " + m.getCodigoProducto()
                    + " | Accion: " + m.getTipoMovimiento()
                    + " | Cantidad: " + m.getCantidad()
                    + " | Fecha: " + m.getFecha());
        }
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

            if (movimientoDao.cn != null && !movimientoDao.cn.isClosed()) {
                movimientoDao.cn.close();
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