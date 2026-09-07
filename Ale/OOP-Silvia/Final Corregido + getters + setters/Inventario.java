import java.util.ArrayList;

public class Inventario {

    private ArrayList<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public Producto buscarProducto(String nombre) {

        for (Producto p : productos) {

            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }

        }

        return null;
    }

    public void mostrarProductos() {

        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-12s %-8s %-8s %-12s%n",
                "Nombre", "Precio", "Cant.", "Tipo");
        System.out.println("---------------------------------------------------------------");

        for (Producto p : productos) {

            String tipo = p.getEspecial().isOrganico() ? "Organico" : "Inorganico";

            System.out.printf("%-12s $%-7.2f %-8d %-12s%n",
                    p.getNombre(),
                    p.getPrecio(),
                    p.getCantidad(),
                    tipo);

        }

        System.out.println("---------------------------------------------------------------");
    }
}