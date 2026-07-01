package sistema_inventariado_ferreteria_santa_cruz_srl;

import java.util.List;
import java.util.Scanner;

public class Menu {

    Scanner sc = new Scanner(System.in);
    ProductoDAO dao = new ProductoDAO();

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
            System.out.println("9. Salir");

            opcion = leerOpcion();

            // RF33 - Validar opciones del menú
            if (!MenuValidator.validarOpcion(opcion, 1, 9)) {
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

                case 9:
                    System.out.println("Fin del programa.");
                    break;
            }

        } while (opcion != 9);
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
}