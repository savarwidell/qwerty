import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static ArrayList<ClienteFrecuente> clientesFrecuentes = new ArrayList<>();

    public static void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        Scanner scanner = new Scanner(System.in);
        Proveedor proveedor = new Proveedor("Cocacolaaa");
        ProveedorMayoritario proveedorMayoritario = new ProveedorMayoritario("Mayorista SA");
        ProveedorMinoritario proveedorMinoritario = new ProveedorMinoritario("Minorista Express");
        boolean programaActivo = true;

        while (programaActivo) {
            limpiarConsola();
            System.out.println(": - Menú de Inventario - :");
            System.out.println("1. Registrar producto");
            System.out.println("2. Mostrar productos");
            System.out.println("3. Solicitud de cliente");
            System.out.println("4. Mostrar clientes frecuentes");
            System.out.println("5. Mostrar lista de precios de proveedores");
            System.out.println("6. Salir");
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
                    realizarSolicitudCliente(inventario, scanner);
                    break;
                case 4:
                    mostrarClientesFrecuentes(scanner);
                    break;
                case 5:
                    mostrarListaPreciosProveedores(scanner, proveedorMayoritario, proveedorMinoritario);
                    break;
                case 6:
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

    public static void registrarProducto(Inventario inventario, Scanner scanner, Proveedor proveedor) {
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

            Producto nuevoProducto = new Producto(nombre, precio, cantidad);

            if (cantidad < 5) {
                proveedor.informeStack(nuevoProducto);
            }

            inventario.agregarProducto(nuevoProducto);

            System.out.println();
            System.out.println("Se registró el producto: " + nuevoProducto.obtenerNombre());
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

    public static void realizarSolicitudCliente(Inventario inventario, Scanner scanner) {
        limpiarConsola();
        System.out.println(": - Solicitud del Cliente - :");
        System.out.println();
        
        scanner.nextLine();
        System.out.print("Ingrese el nombre del cliente: ");
        String nombreCliente = scanner.nextLine();
        
        System.out.print("¿Es cliente frecuente? (si/no): ");
        String esFrecuente = scanner.nextLine();
        
        Cliente cliente;
        if (esFrecuente.equalsIgnoreCase("si")) {
            ClienteFrecuente clienteFrecuente = buscarClienteFrecuente(nombreCliente);
            if (clienteFrecuente == null) {
                clienteFrecuente = new ClienteFrecuente(nombreCliente);
                clientesFrecuentes.add(clienteFrecuente);
            }
            cliente = clienteFrecuente;
        } else {
            cliente = new Cliente(nombreCliente);
        }
        
        cliente.solicitarMercancia(inventario, scanner);
        
        System.out.println();
        System.out.println("Presione ENTER para continuar...");
        scanner.nextLine();
        scanner.nextLine();
    }

    public static ClienteFrecuente buscarClienteFrecuente(String nombre) {
        for (ClienteFrecuente cliente : clientesFrecuentes) {
            if (cliente.obtenerNombre().equalsIgnoreCase(nombre)) {
                return cliente;
            }
        }
        return null;
    }

    public static void mostrarClientesFrecuentes(Scanner scanner) {
        limpiarConsola();
        System.out.println(": - CLIENTES FRECUENTES - :");
        System.out.println();
        
        if (clientesFrecuentes.isEmpty()) {
            System.out.println("No hay clientes frecuentes registrados.");
        } else {
            System.out.println("Total: " + clientesFrecuentes.size() + " clientes");
            System.out.println("----------------------------------------");
            for (ClienteFrecuente cliente : clientesFrecuentes) {
                cliente.mostrarHistorialCompras();
                System.out.println();
            }
        }
        
        System.out.println();
        System.out.println("Presione ENTER para continuar...");
        scanner.nextLine();
        scanner.nextLine();
    }

    public static void mostrarListaPreciosProveedores(Scanner scanner, ProveedorMayoritario mayoritario, ProveedorMinoritario minoritario) {
        limpiarConsola();
        System.out.println(": - LISTA DE PRECIOS DE PROVEEDORES - :");
        System.out.println();
        
        mayoritario.darListaPrecios();
        System.out.println();
        minoritario.darListaPrecios();
        
        System.out.println();
        System.out.println("Presione ENTER para continuar...");
        scanner.nextLine();
        scanner.nextLine();
    }
}