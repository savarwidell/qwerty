public class Producto {
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public double obtenerPrecio() {
        return precio;
    }

    public int obtenerCantidad() {
        return cantidad;
    }

    public void establecerNombre(String nombre) {
        this.nombre = nombre;
    }

    public void establecerPrecio(double precio) {
        this.precio = precio;
    }

    public void establecerCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
} 