public class Proveedor {

    private String nombre;

    public Proveedor(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void informeStock(Producto producto) {

        System.out.println("ALERTA: Poco stock del producto "
                + producto.getNombre()
                + ". Aviso enviado al proveedor "
                + nombre);

    }

    public void revisarInventario(Inventario tienda) {

        System.out.println("\n========= REPORTE DEL PROVEEDOR =========");

        System.out.println("\nPRODUCTOS ORGANICOS");

        for (Producto p : tienda.getProductos()) {

            if (p.getEspecial().isOrganico()) {

                System.out.println("- " + p.getNombre());

            }

        }

        System.out.println("\nPRODUCTOS INORGANICOS");

        for (Producto p : tienda.getProductos()) {

            if (!p.getEspecial().isOrganico()) {

                System.out.println("- " + p.getNombre());

            }

        }

        System.out.println("=========================================\n");

    }
}