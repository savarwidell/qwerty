import java.util.Scanner;

public class Cliente {

    private String nombre;

    public Cliente(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void comprar(Inventario tienda, Scanner scanner) {

        System.out.println("\nPRODUCTOS DISPONIBLES");
        tienda.mostrarProductos();

        scanner.nextLine();

        System.out.print("Nombre del producto: ");
        String nombreProducto = scanner.nextLine();

        Producto producto = tienda.buscarProducto(nombreProducto);

        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();

        if (cantidad > producto.getCantidad()) {
            System.out.println("No hay suficiente existencia.");
            return;
        }

        producto.setCantidad(producto.getCantidad() - cantidad);

        double total = cantidad * producto.getPrecio();

        String tipo = producto.getEspecial().isOrganico()
                ? "ORGANICO"
                : "INORGANICO";

        System.out.println("\n=========== TICKET ===========");
        System.out.println("Cliente : " + nombre);
        System.out.println("Producto: " + producto.getNombre());
        System.out.println("Tipo    : " + tipo);
        System.out.println("Origen  : " + producto.getEspecial().getPaisDeOrigen());
        System.out.println("Cantidad: " + cantidad);
        System.out.printf("Total   : $%.2f%n", total);
        System.out.println("=============================");
    }
}