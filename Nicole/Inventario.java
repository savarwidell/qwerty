import java.util.ArrayList;

public class Inventario {
    private ArrayList<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void mostrarProductos() {
        System.out.println("+----------+----------+----------+");
        System.out.printf("| %-8s | %-8s | %-8s |\n",
                "Nombre", "Precio", "Cantidad");
        System.out.println("+----------+----------+----------+");

        for (Producto producto : productos) {
            System.out.printf("| %-8s | %8.2f | %8d |\n",
                    producto.obtenerNombre(),
                    producto.obtenerPrecio(),
                    producto.obtenerCantidad());
        }

        System.out.println("+----------+----------+----------+");
    }

    public Producto buscarProducto(String nombre) {
        for (Producto producto : productos) {
            if (producto.obtenerNombre().equalsIgnoreCase(nombre)) {
                return producto;
            }
        }

        return null;
    }
}