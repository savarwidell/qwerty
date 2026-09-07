public class Producto {

    private String nombre;
    private double precio;
    private int cantidad;
    private ProductoEspecializado especial;

    public Producto(String nombre, double precio, int cantidad, ProductoEspecializado especial) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.especial = especial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ProductoEspecializado getEspecial() {
        return especial;
    }

    public void setEspecial(ProductoEspecializado especial) {
        this.especial = especial;
    }
}