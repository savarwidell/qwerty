import java.util.ArrayList;
import java.util.Scanner;

public class ClienteFrecuente extends Cliente {
    private ArrayList<String> historialCompras;

    public ClienteFrecuente(String nombre) {
        super(nombre);
        historialCompras = new ArrayList<>();
    }

    @Override
    public void comprar(Inventario inventario, Scanner scanner) {
        super.comprar(inventario, scanner);
        historialCompras.add("Compra realizada por: " + getNombre());
    }

    public void mostrarHistorialCompras() {
        System.out.println("\nHistorial de compras del cliente: " + getNombre());
        if (historialCompras.isEmpty()) {
            System.out.println("No hay compras registradas.");
        } else {
            for (String compra : historialCompras) {
                System.out.println(compra);
            }
        }
    }
}