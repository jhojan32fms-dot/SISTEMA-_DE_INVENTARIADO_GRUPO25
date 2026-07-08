package reportes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * RF31. Iniciar sistema (login)
 * RF32. Mostrar menu principal
 *
 * Clase de ejemplo que integra el Bloque 4 completo (RF25 - RF32).
 * Puedes copiar el metodo main() e integrarlo al menu principal
 * de tu proyecto en equipo, o ejecutar esta clase directamente
 * para probar los reportes.
 */
public class Main {

    static Scanner sc = new Scanner(System.in);
    static ReporteDAO dao = new ReporteDAO();

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE INVENTARIO - FERRETERIA SANTA CRUZ S.R.L. ===");

        // RF31. Iniciar sistema
        if (iniciarSistema()) {
            // RF32. Mostrar menu principal
            menuPrincipal();
        } else {
            System.out.println("Usuario o contraseña incorrectos. Sistema finalizado.");
        }
    }

    // ============================================================
    // RF31. Iniciar sistema (login contra tabla usuarios)
    // ============================================================
    public static boolean iniciarSistema() {
        System.out.print("Usuario: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        String sql = "SELECT id_usuario, nombre FROM usuarios "
                + "WHERE username = ? AND password = ? AND activo = 1";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Bienvenido, " + rs.getString("nombre") + "!");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return false;
    }

    // ============================================================
    // RF32. Mostrar menu principal
    // ============================================================
    public static void menuPrincipal() {
        int opcion;
        do {
            System.out.println("\n=========== MENU PRINCIPAL - REPORTES ===========");
            System.out.println("1. Movimientos por producto (RF25)");
            System.out.println("2. Movimientos por categoria (RF26)");
            System.out.println("3. Inventario general (RF27)");
            System.out.println("4. Alertas activas (RF28)");
            System.out.println("5. Resumen de inventario (RF30)");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");

            opcion = leerEntero();

            try {
                switch (opcion) {
                    case 1 -> opcionMovimientosPorProducto();
                    case 2 -> opcionMovimientosPorCategoria();
                    case 3 -> opcionInventarioGeneral();
                    case 4 -> opcionAlertasActivas();
                    case 5 -> opcionResumenInventario();
                    case 0 -> System.out.println("Cerrando sistema...");
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (Exception e) {
                System.out.println("Ocurrio un error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static int leerEntero() {
        while (!sc.hasNextInt()) {
            System.out.print("Ingresa un numero valido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    // ---------- RF25 ----------
    private static void opcionMovimientosPorProducto() throws SQLException {
        System.out.print("Codigo del producto: ");
        String codigo = sc.nextLine();
        List<String[]> datos = dao.movimientosPorProducto(codigo);
        mostrarTabla(dao.encabezadosMovimientos(), datos);
        preguntarExportar(datos, dao.encabezadosMovimientos());
    }

    // ---------- RF26 ----------
    private static void opcionMovimientosPorCategoria() throws SQLException {
        System.out.print("Nombre de la categoria: ");
        String categoria = sc.nextLine();
        List<String[]> datos = dao.movimientosPorCategoria(categoria);
        mostrarTabla(dao.encabezadosMovimientos(), datos);
        preguntarExportar(datos, dao.encabezadosMovimientos());
    }

    // ---------- RF27 ----------
    private static void opcionInventarioGeneral() throws SQLException {
        List<String[]> datos = dao.inventarioGeneral();
        mostrarTabla(dao.encabezadosInventario(), datos);
        preguntarExportar(datos, dao.encabezadosInventario());
    }

    // ---------- RF28 ----------
    private static void opcionAlertasActivas() throws SQLException {
        List<String[]> datos = dao.alertasActivas();
        if (datos.isEmpty()) {
            System.out.println("No hay alertas activas. Todo el stock esta OK.");
        } else {
            mostrarTabla(dao.encabezadosAlertas(), datos);
        }
        preguntarExportar(datos, dao.encabezadosAlertas());
    }

    // ---------- RF30 ----------
    private static void opcionResumenInventario() throws SQLException {
        Map<String, String> resumen = dao.resumenInventario();
        System.out.println("\n--- RESUMEN DE INVENTARIO ---");
        for (Map.Entry<String, String> entry : resumen.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // ---------- RF29 (usado desde cualquier reporte de tabla) ----------
    private static void preguntarExportar(List<String[]> datos, String[] encabezados) {
        if (datos == null || datos.isEmpty()) {
            return;
        }
        System.out.print("¿Deseas exportar este reporte a un archivo? (s/n): ");
        String resp = sc.nextLine();
        if (resp.equalsIgnoreCase("s")) {
            System.out.print("Nombre del archivo (ej: reporte.txt): ");
            String nombreArchivo = sc.nextLine();
            try {
                dao.exportarReporte(datos, encabezados, nombreArchivo);
                System.out.println("Reporte exportado correctamente a: " + nombreArchivo);
            } catch (Exception e) {
                System.out.println("Error al exportar: " + e.getMessage());
            }
        }
    }

    private static void mostrarTabla(String[] encabezados, List<String[]> datos) {
        System.out.println(String.join(" | ", encabezados));
        System.out.println("-".repeat(90));
        if (datos.isEmpty()) {
            System.out.println("(Sin resultados)");
        }
        for (String[] fila : datos) {
            System.out.println(String.join(" | ", fila));
        }
    }
}
