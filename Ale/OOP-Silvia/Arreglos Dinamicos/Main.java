import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> productos = new ArrayList<>();

        productos.add("Laptop");
        productos.add("Mouse");      
        productos.add("Teclado");
        
        System.out.println("Productos del inventario:");

        for (int i = 0; i < productos.size(); i++) {
            System.out.println((i + 1) + ". " + productos.get(i));
        }
    }

}