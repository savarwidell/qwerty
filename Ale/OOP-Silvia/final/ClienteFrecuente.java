import java.util.ArrayList;
import java.util.Scanner;

public class ClienteFrecuente extends Cliente{
    private ArrayList<String> historialCompras;

    ClienteFrecuente(String nombre) {
        super(nombre);
        historialCompras = new ArrayList<>();
    }

    @Override
    public void solicitarMercancia(Inventario inventario, Scanner scanner) {
        super.solicitarMercancia(inventario, scanner);
        historialCompras.add("Compra realizada por: " + obtenerNombre());
    }

    public void mostrarHistorialCompras() {
        System.out.println("\nHistorial de compras del cliente: " + obtenerNombre());
        if (historialCompras.isEmpty()) {
            System.out.println("No hay compras registradas.");
        } else {
            for (String compra : historialCompras) {
                System.out.println(compra);
            }
        }
    }

    
}
