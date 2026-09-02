import java.util.Scanner;

/*
INTEGRANTES:
    Daniela Nicole Juarez Resendiz 
    Valentin Angeles Juarez
    Alejandro Barrera Lopez 
*/

public class Main {

    public static void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {

        Inventario inventario = new Inventario();
        Scanner scanner = new Scanner(System.in);

        Proveedor proveedor = new Proveedor("Cocacolaaa");

        boolean programaActivo = true;

        while (programaActivo) {

            limpiarConsola();

            System.out.println(": - Menú de Inventario - :");
            System.out.println("1. Registrar producto");
            System.out.println("2. Mostrar productos");
            System.out.println("3. Solicitud de cliente");
            System.out.println("4. Salir");
            System.out.println();

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1:

                    registrarProducto(inventario, scanner, proveedor);

                    break;

                case 2:

                    limpiarConsola();

                    System.out.println(": - Lista de Productos - :");
                    System.out.println();

                    inventario.mostrarProductos();

                    System.out.println();
                    System.out.println(": - - - - - - - - - - - - - - :");

                    System.out.println("\nPresione ENTER para continuar...");
                    scanner.nextLine();
                    scanner.nextLine();

                    break;

                case 3:

                    limpiarConsola();

                    System.out.println(": - Solicitud del Cliente - :");
                    System.out.println();

                    scanner.nextLine();

                    System.out.print("Ingrese el nombre del cliente: ");
                    String nombreCliente = scanner.nextLine();

                    Cliente cliente = new Cliente(nombreCliente);

                    cliente.solicitarMercancia(inventario, scanner);

                    System.out.println();
                    System.out.println("Presione ENTER para continuar...");
                    scanner.nextLine();

                    break;

                case 4:

                    programaActivo = false;

                    System.out.println();
                    System.out.println("Programa finalizado.");

                    break;

                default:

                    System.out.println();
                    System.out.println("Opción no válida.");

                    System.out.println("Presione ENTER para continuar...");
                    scanner.nextLine();
                    scanner.nextLine();

                    break;
            }
        }

        scanner.close();
    }


    // MÉTODO PARA REGISTRAR PRODUCTOS
    public static void registrarProducto(
            Inventario inventario,
            Scanner scanner,
            Proveedor proveedor) {

        boolean registrando = true;

        scanner.nextLine();

        while (registrando) {

            limpiarConsola();

            System.out.println(": - Registro de Productos - :");
            System.out.println();

            System.out.println("Ingrese los datos del producto:");
            System.out.println();

            System.out.print("Ingrese el nombre del producto: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese el precio del producto: ");
            double precio = scanner.nextDouble();

            System.out.print("Ingrese la cantidad del producto: ");
            int cantidad = scanner.nextInt();

            Producto nuevoProducto =
                    new Producto(nombre, precio, cantidad);

            // Verificar si hay pocas existencias
            if (cantidad < 5) {

                proveedor.informeStack(nuevoProducto);
            }

            // Agregar producto al inventario
            inventario.agregarProducto(nuevoProducto);

            System.out.println();
            System.out.println(
                    "Se registró el producto: "
                    + nuevoProducto.obtenerNombre());

            System.out.println();
            System.out.println(": - - - - - - - - - - - - - - :");

            scanner.nextLine();

            System.out.print("¿Desea continuar? si/no: ");
            String continuar = scanner.nextLine();

            if (!continuar.equalsIgnoreCase("si")) {

                registrando = false;
            }
        }
    }
}