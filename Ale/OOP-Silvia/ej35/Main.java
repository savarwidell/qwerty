public class Main {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();

        Producto producto1 = new Producto("Coquita fria", 10.99, 5);
        Producto producto2 = new Producto("Pizza", 15.49, 3);
        Producto producto3 = new Producto("Nucita", 7.99, 8);

        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        inventario.agregarProducto(producto3);

        inventario.mostrarProductos();
    }
}