import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Inventario tienda = new Inventario();

        Proveedor proveedor = new Proveedor("CocaCola");

        int opcion;

        do {

            System.out.println("\n====== TIENDA ======");
            System.out.println("1. Registrar producto");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Reporte del proveedor");
            System.out.println("4. Cliente comprar");
            System.out.println("5. Salir");
            System.out.print("Opcion: ");

            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:

                    scanner.nextLine();

                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Precio: ");
                    double precio = scanner.nextDouble();

                    System.out.print("Cantidad: ");
                    int cantidad = scanner.nextInt();

                    scanner.nextLine();

                    System.out.print("Descripcion: ");
                    String descripcion = scanner.nextLine();

                    System.out.print("Pais de origen: ");
                    String pais = scanner.nextLine();

                    System.out.print("¿Es organico? (si/no): ");
                    String resp = scanner.nextLine();

                    boolean organico = resp.equalsIgnoreCase("si");

                    ProductoEspecializado especial =
                            new ProductoEspecializado(descripcion, pais, organico);

                    Producto producto =
                            new Producto(nombre, precio, cantidad, especial);

                    tienda.agregarProducto(producto);

                    if (cantidad < 5) {
                        proveedor.informeStock(producto);
                    }

                    System.out.println("Producto registrado.");

                    break;

                case 2:

                    tienda.mostrarProductos();

                    break;

                case 3:

                    proveedor.revisarInventario(tienda);

                    break;

                case 4:

                    scanner.nextLine();

                    System.out.print("Nombre del cliente: ");
                    String clienteNombre = scanner.nextLine();

                    Cliente cliente = new Cliente(clienteNombre);

                    cliente.comprar(tienda, scanner);

                    break;

                case 5:

                    System.out.println("Gracias por usar la tienda.");

                    break;

                default:

                    System.out.println("Opcion incorrecta.");

            }

        } while (opcion != 5);

        scanner.close();
    }
}