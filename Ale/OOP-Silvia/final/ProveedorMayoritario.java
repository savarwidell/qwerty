public class ProveedorMayoritario extends Proveedor {
    ProveedorMayoritario(String nombre) {
        super(nombre);
    }

    public void darListaPrecios() {
        System.out.println("Lista de precios del proveedor mayoritario " + obtenerNombre() + ":");
        System.out.println("Cocacola: $8.00");
        System.out.println("Pepsi: $12.00");
        System.out.println("Fanta: $18.00");
    }
    
}
