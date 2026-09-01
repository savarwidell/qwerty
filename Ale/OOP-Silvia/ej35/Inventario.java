import java.util.ArrayList;

public class Inventario {
    private ArrayList<Producto> productos;

    Inventario() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void mostrarProductos() {
        for (Producto producto : productos) {
            System.out.println("Nombre: " + producto.obtenerNombre() + ", Precio: " + producto.obtenerPrecio() + ", Cantidad: " + producto.obtenerCantidad());
        }
    }
}
