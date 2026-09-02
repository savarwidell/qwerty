public class ProveedorMinoritario extends Proveedor {
    ProveedorMinoritario(String nombre) {
        super(nombre);
    }

    public void darListaPrecios() {
        System.out.println("Lista de precios del proveedor minoritario " + obtenerNombre() + ":");
        System.out.println("Cocacola: $10.00");
        System.out.println("Pepsi: $15.00");
        System.out.println("Fanta: $20.00");
    }
}