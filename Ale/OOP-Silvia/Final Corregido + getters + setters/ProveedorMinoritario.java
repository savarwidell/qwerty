public class ProveedorMinoritario extends Proveedor {
    public ProveedorMinoritario(String nombre) {
        super(nombre);
    }

    public void darListaPrecios() {
        System.out.println("Lista de precios del proveedor minoritario " + getNombre() + ":");
        System.out.println("Cocacola: $10.00");
        System.out.println("Pepsi: $15.00");
        System.out.println("Fanta: $20.00");
    }
}