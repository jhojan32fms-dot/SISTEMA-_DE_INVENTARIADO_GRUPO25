
package sistema_inventariado_ferreteria_santa_cruz_srl;
// Importamos las clases necesarias desde sus nuevos paquetes correctos
import sistema_inventariado_ferreteria_santa_cruz_srl.InventarioDAO;
import sistema_inventariado_ferreteria_santa_cruz_srl.Movimiento;
import java.util.ArrayList;
import java.util.Scanner;
/**
  * @author JUANPC
 */
public class MenuInventario {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        InventarioDAO inventarioDAO = new InventarioDAO();
        int opcion = 0;

        do {
            System.out.println("\n==================================================================");
            System.out.println("  SISTEMA SANTA CRUZ SRL - INVENTARIO FERRETERO (BLOQUE 3) ");
            System.out.println("==================================================================");
            System.out.println("1. Buscar movimientos por fecha (RF17)");
            System.out.println("2. Mostrar movimientos recientes (RF18)");
            System.out.println("3. Configurar stock mínimo de alerta para producto (RF19)");
            System.out.println("4. Consultar alertas de bajo stock / stock crítico (RF20/RF21)");
            System.out.println("5. Listar productos agotados (RF22)");
            System.out.println("6. Mostrar todas las entradas de almacén (RF23)");
            System.out.println("7. Mostrar todas las salidas de almacén (RF24)");
            System.out.println("8. Salir del sistema / Volver");
            System.out.print("Por favor, digite una opción: ");
            
            try {
                opcion = teclado.nextInt();
                teclado.nextLine(); // Limpia el salto de línea residual

                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese fecha de búsqueda (AAAA-MM-DD): ");
                        String fecha = teclado.nextLine();
                        imprimirRegistros(inventarioDAO.buscarMovimientosPorFecha(fecha));
                        break;
                    case 2:
                        System.out.println("\n--- DESPLEGANDO ÚLTIMOS 10 MOVIMIENTOS ---");
                        imprimirRegistros(inventarioDAO.mostrarMovimientosRecientes());
                        break;
                    case 3:
                        System.out.print("Ingrese el código del producto ferretero: ");
                        String cod = teclado.nextLine();
                        System.out.print("Establezca el stock mínimo de seguridad: ");
                        int cantMin = teclado.nextInt();
                        if (inventarioDAO.registrarStockMinimo(cod, cantMin)) {
                            System.out.println("✔ ¡Operación Exitosa! El stock mínimo ha sido guardado.");
                        } else {
                            System.out.println(" Error: No se pudo actualizar. Revise que el código exista en MySQL.");
                        }
                        break;
                    case 4:
                        inventarioDAO.mostrarProductosBajoStock();
                        break;
                    case 5:
                        inventarioDAO.mostrarProductosAgotados();
                        break;
                    case 6:
                        System.out.println("\n--- HISTORIAL DE ENTRADAS REGISTRADAS ---");
                        imprimirRegistros(inventarioDAO.mostrarMovimientosPorTipo("ENTRADA"));
                        break;
                    case 7:
                        System.out.println("\n--- HISTORIAL DE SALIDAS REGISTRADAS ---");
                        imprimirRegistros(inventarioDAO.mostrarMovimientosPorTipo("SALIDA"));
                        break;
                    case 8:
                        System.out.println("Cerrando el módulo de inventariado de la Ferretería Santa Cruz SRL...");
                        break;
                    default:
                        System.out.println("Opción no válida. Ingrese un número del 1 al 8.");
                }
            } catch (Exception e) {
                System.out.println(" Error crítico: Debe ingresar únicamente números enteros en el menú.");
                teclado.nextLine(); // Limpia el buffer para evitar bucles infinitos por error de tipeo
            }
        } while (opcion != 8);
    }
    
      // Método para imprimir listas en consola sin duplicar código
    private static void imprimirRegistros(ArrayList<Movimiento> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay registros de movimientos en esta consulta.");
            return;
        }
        for (Movimiento m : lista) {
            System.out.println("ID Movimiento: " + m.getIdMovimiento() + 
                               " | Artículo: " + m.getCodigoProducto() +
                               " | Acción: " + m.getTipoMovimiento() + 
                               " | Cantidad: " + m.getCantidad() + 
                               " | Fecha: " + m.getFecha());
        }
    }
}

