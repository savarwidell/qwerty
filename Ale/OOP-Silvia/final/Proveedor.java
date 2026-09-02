public class Proveedor {
    private String nombre;

    Proveedor(String nombre) {
        this.nombre = nombre;
    }

    public void informeStack(Producto producto) {
        System.out.println("!! : El producto " + producto.obtenerNombre() + " tiene una cantidad menor a 5. Se envió un informe al proveedor " + nombre + ".");
    }

    public String obtenerNombre() {
        return nombre;
    }

    public void establecerNombre(String nombre) {
        this.nombre = nombre;
    }
}