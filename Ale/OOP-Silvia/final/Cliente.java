import java.util.Scanner;

public class Cliente {
    private String nombre;

    Cliente(String nombre) {
        this.nombre = nombre;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public void solicitarMercancia(Inventario inventario, Scanner scanner) {
        scanner.nextLine();

        System.out.println(": - Solicitud de Mercancía - :");
        System.out.print("Ingrese el nombre del producto que desea: ");
        String nombreProducto = scanner.nextLine();

        Producto producto = inventario.buscarProducto(nombreProducto);

        if (producto == null) {
            System.out.println("El producto no existe en la tienda.");
            return;
        }

        System.out.print("Ingrese la cantidad que desea: ");
        int cantidadSolicitada = scanner.nextInt();

        if (cantidadSolicitada <= 0) {
            System.out.println("La cantidad debe ser mayor a 0.");
            return;
        }

        if (cantidadSolicitada > producto.obtenerCantidad()) {
            System.out.println("No hay suficiente mercancía disponible.");
            System.out.println("Cantidad disponible: " + producto.obtenerCantidad());
            return;
        }

        producto.establecerCantidad(
            producto.obtenerCantidad() - cantidadSolicitada
        );

        double total = cantidadSolicitada * producto.obtenerPrecio();

        System.out.println("\n---------------------------------");
        System.out.println("           TICKET");
        System.out.println("---------------------------------");
        System.out.println("Cliente: " + nombre);
        System.out.println("Producto: " + producto.obtenerNombre());
        System.out.println("Cantidad: " + cantidadSolicitada);
        System.out.printf("Precio unitario: $%.2f\n", producto.obtenerPrecio());
        System.out.printf("TOTAL: $%.2f\n", total);
        System.out.println("---------------------------------");
        System.out.println("Ticket enviado al cliente: " + nombre);
        System.out.println("---------------------------------");
    }
}